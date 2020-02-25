package tech.orlov.alarmclockapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import tech.orlov.alarmclockapp.domain.*
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getAllAlarmsCommand: GetAllAlarmsCommand,
    private val addNewAlarmCommand: AddNewAlarmCommand,
    private val updateAlarmCommand: UpdateAlarmCommand,
    private val deleteAlarmCommand: DeleteAlarmCommand,
    private val alarmVoMapper: AlarmVoMapper,
    private val nextAlarmMessageFormatter: NextAlarmMessageFormatter
) : ViewModel() {

    private val disposables = CompositeDisposable()

    val alarms = MutableLiveData<List<AlarmVo>>()

    val updatedAlarm = MutableLiveData<AlarmVo>()

    val newAlam = MutableLiveData<AlarmVo>()

    val nextAlarmMessage = MutableLiveData<String>()

    fun start() {
        getAlarms()
    }

    private fun getAlarms(){
        disposables.add(
            getAllAlarmsCommand.getAllAlarms()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ alarmList ->
                    alarms.value = alarmList.map { alarmVoMapper.map(it) }
                }, {
                    it.printStackTrace()
                })
        )
    }

    fun updateTime(id: Long, hourOfDay: Int, minute: Int) {
        disposables.add(
            updateAlarmCommand.updateAlarm(id, hourOfDay, minute)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ alarmNextAlarmPair ->
                updatedAlarm.value = alarmVoMapper.map(alarmNextAlarmPair.first)
                alarmNextAlarmPair.second?.let {
                    nextAlarmMessage.value = nextAlarmMessageFormatter.format(it)
                }
            }, {
                it.printStackTrace()
            })
        )
    }

    fun addNewAlarm(hourOfDay: Int, minute: Int) {
        disposables.add(
            addNewAlarmCommand.addNewAlarm(hourOfDay, minute)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ alarmNextAlarmPair ->
                    newAlam.value = alarmVoMapper.map(alarmNextAlarmPair.first)
                    alarmNextAlarmPair.second?.let {
                        nextAlarmMessage.value = nextAlarmMessageFormatter.format(it)
                    }
                }, {
                    it.printStackTrace()
                })
        )
    }

    fun updateEnabledStatus(id: Long, enabled: Boolean) {
        disposables.add(
            updateAlarmCommand.updateAlarm(id, isEnabled = enabled)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ alarmNextAlarmPair ->
                    updatedAlarm.value = alarmVoMapper.map(alarmNextAlarmPair.first)
                    alarmNextAlarmPair.second?.let {
                        nextAlarmMessage.value = nextAlarmMessageFormatter.format(it)
                    }
                }, {
                    it.printStackTrace()
                })
        )
    }

    fun updateDaysOfWeekList(id: Long, daysOfWeek: List<AlarmDayOfWeekVo>) {
        disposables.add(
            updateAlarmCommand.updateAlarm(id, daysOfWeek = daysOfWeek.map {alarmVoMapper.map(it)})
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ alarmNextAlarmPair ->
                    updatedAlarm.value = alarmVoMapper.map(alarmNextAlarmPair.first)
                    alarmNextAlarmPair.second?.let {
                        nextAlarmMessage.value = nextAlarmMessageFormatter.format(it)
                    }
                }, {
                    it.printStackTrace()
                })
        )
    }

    fun deleteAlarm(id: Long) {
        disposables.add(
            deleteAlarmCommand.deleteAlarm(id)
                .subscribe({
                    //no-op
                }, {
                    it.printStackTrace()
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
