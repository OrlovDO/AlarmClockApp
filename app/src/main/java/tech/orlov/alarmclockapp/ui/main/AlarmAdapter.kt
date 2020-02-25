package tech.orlov.alarmclockapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_alarm.view.*
import tech.orlov.alarmclockapp.R

class AlarmAdapter(
    private var alarms: MutableList<AlarmVo>,
    private var alarmListener: AlarmListener
) : RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    fun addAlarm(alarm: AlarmVo) {
        alarms.add(alarm)
        notifyItemInserted(alarms.size - 1);
    }

    fun updateAlarm(alarm: AlarmVo) {
        val index = alarms.indexOfFirst { it.id == alarm.id }
        alarms[index] = alarm
        notifyItemChanged(index)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): AlarmViewHolder {
        return AlarmViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_alarm, parent, false)
        )
    }

    override fun getItemCount() = alarms.size

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.bind(alarms[position])
    }

    private fun deleteItem(alarm: AlarmVo) {
        val index = alarms.indexOf(alarm)
        alarms.removeAt(index)
        notifyItemRemoved(index)
    }

    inner class AlarmViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer, CompoundButton.OnCheckedChangeListener {

        private lateinit var alarm: AlarmVo

        fun bind(alarm: AlarmVo) {
            this.alarm = alarm
            containerView.timeTextView.text = alarm.formattedTime
            containerView.timeTextView.setOnClickListener {
                alarmListener.onClickTime(alarm.id, alarm.hours, alarm.minutes)
            }
            containerView.deleteButton.setOnClickListener {
                alarmListener.onDeleteClick(alarm.id)
                deleteItem(alarm)
            }
            containerView.alarmSwitch.setOnCheckedChangeListener(null)
            containerView.alarmSwitch.isChecked = alarm.isEnabled
            containerView.alarmSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                alarmListener.onSwitchEnabled(alarm.id, isChecked)
            }
            removeChipsListeners()
            setEnabledDaysOfWeekList(alarm.daysOfWeek)
            setChipsListeners()

        }

        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            alarmListener.onDaysOfWeekChanged(alarm.id, getEnabledDaysOfWeekList())
        }

        private fun setChipsListeners() {
            containerView.mondayChip.setOnCheckedChangeListener(this)
            containerView.tuesdayChip.setOnCheckedChangeListener(this)
            containerView.wednesdayChip.setOnCheckedChangeListener(this)
            containerView.thursdayChip.setOnCheckedChangeListener(this)
            containerView.fridayChip.setOnCheckedChangeListener(this)
            containerView.saturdayChip.setOnCheckedChangeListener(this)
            containerView.sundayChip.setOnCheckedChangeListener(this)
        }

        private fun removeChipsListeners() {
            containerView.mondayChip.setOnCheckedChangeListener(null)
            containerView.tuesdayChip.setOnCheckedChangeListener(null)
            containerView.wednesdayChip.setOnCheckedChangeListener(null)
            containerView.thursdayChip.setOnCheckedChangeListener(null)
            containerView.fridayChip.setOnCheckedChangeListener(null)
            containerView.saturdayChip.setOnCheckedChangeListener(null)
            containerView.sundayChip.setOnCheckedChangeListener(null)
        }

        private fun setEnabledDaysOfWeekList(daysOfWeek: List<AlarmDayOfWeekVo>) {
            containerView.mondayChip.isChecked = daysOfWeek.contains(AlarmDayOfWeekVo.MONDAY)
            containerView.tuesdayChip.isChecked = daysOfWeek.contains(AlarmDayOfWeekVo.TUESDAY)
            containerView.wednesdayChip.isChecked = daysOfWeek.contains(AlarmDayOfWeekVo.WEDNESDAY)
            containerView.thursdayChip.isChecked = daysOfWeek.contains(AlarmDayOfWeekVo.THURSDAY)
            containerView.fridayChip.isChecked = daysOfWeek.contains(AlarmDayOfWeekVo.FRIDAY)
            containerView.saturdayChip.isChecked = daysOfWeek.contains(AlarmDayOfWeekVo.SATURDAY)
            containerView.sundayChip.isChecked = daysOfWeek.contains(AlarmDayOfWeekVo.SUNDAY)
        }

        private fun getEnabledDaysOfWeekList(): List<AlarmDayOfWeekVo> {
            val resultList = mutableListOf<AlarmDayOfWeekVo>()
            if (containerView.mondayChip.isChecked) resultList.add(AlarmDayOfWeekVo.MONDAY)
            if (containerView.tuesdayChip.isChecked) resultList.add(AlarmDayOfWeekVo.TUESDAY)
            if (containerView.wednesdayChip.isChecked) resultList.add(AlarmDayOfWeekVo.WEDNESDAY)
            if (containerView.thursdayChip.isChecked) resultList.add(AlarmDayOfWeekVo.THURSDAY)
            if (containerView.fridayChip.isChecked) resultList.add(AlarmDayOfWeekVo.FRIDAY)
            if (containerView.saturdayChip.isChecked) resultList.add(AlarmDayOfWeekVo.SATURDAY)
            if (containerView.sundayChip.isChecked) resultList.add(AlarmDayOfWeekVo.SUNDAY)
            return resultList
        }


    }

    interface AlarmListener {
        fun onClickTime(id: Long, hours: Int, minutes: Int)
        fun onSwitchEnabled(id: Long, isEnabled: Boolean)
        fun onDaysOfWeekChanged(id: Long, daysOfWeek: List<AlarmDayOfWeekVo>)
        fun onDeleteClick(id: Long)
    }

}