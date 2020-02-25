package tech.orlov.alarmclockapp.ui.main

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.main_fragment.*
import tech.orlov.alarmclockapp.R
import java.time.DayOfWeek
import java.util.*
import javax.inject.Inject


class MainFragment : Fragment(), AlarmAdapter.AlarmListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var viewModel: MainViewModel

    private val calendar = Calendar.getInstance()

    private var alarmAdapter: AlarmAdapter =
        AlarmAdapter(mutableListOf(), this)


    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addAlarmButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            showTimePicker(null, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))
        }

        alarmRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = alarmAdapter
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
        }

        viewModel.alarms.observe(viewLifecycleOwner, Observer { alarms ->
            alarmAdapter = AlarmAdapter(alarms.toMutableList(), this)
            alarmRecycler.adapter = alarmAdapter
        })

        viewModel.updatedAlarm.observe(viewLifecycleOwner, Observer { alarm ->
            alarmAdapter.updateAlarm(alarm)
        })

        viewModel.newAlam.observe(viewLifecycleOwner, Observer { alarm ->
            alarmAdapter.addAlarm(alarm)
        })

        viewModel.nextAlarmMessage.observe(viewLifecycleOwner, Observer { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        })
        viewModel.start()
    }

    override fun onClickTime(id: Long, hours: Int, minutes: Int) {
        showTimePicker(id, hours, minutes)
    }

    override fun onSwitchEnabled(id: Long, isEnabled: Boolean) {
        viewModel.updateEnabledStatus(id, isEnabled)
    }

    override fun onDaysOfWeekChanged(id: Long, daysOfWeek: List<AlarmDayOfWeekVo>) {
        viewModel.updateDaysOfWeekList(id, daysOfWeek)
    }

    override fun onDeleteClick(id: Long) {
        viewModel.deleteAlarm(id)
    }


    private fun showTimePicker(id: Long?, currentHours: Int, currentMinutes: Int) {
        TimePickerDialog(
            context,
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                if (id != null) {
                    viewModel.updateTime(id, hourOfDay, minute)
                } else {
                    viewModel.addNewAlarm(hourOfDay, minute)
                }
            },
            currentHours,
            currentMinutes,
            true
        ).show()
    }

}
