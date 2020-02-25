package tech.orlov.alarmclockapp.alarm

import android.content.Context
import android.content.Intent
import dagger.android.DaggerBroadcastReceiver
import javax.inject.Inject

class AlarmBootReceiver: DaggerBroadcastReceiver() {
    @Inject
    lateinit var alarmBootController: AlarmBootController

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        alarmBootController.restartAlarms()
    }
}