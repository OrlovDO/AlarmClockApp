package tech.orlov.alarmclockapp.alarm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.*
import androidx.core.app.NotificationCompat
import dagger.android.DaggerService
import tech.orlov.alarmclockapp.R
import javax.inject.Inject

class AlarmService : DaggerService() {

    @Inject
    lateinit var alarmController: AlarmServiceController

    @Inject
    lateinit var alarmVibrator: AlarmVibrator

    private val handler = Handler()
    private val stopServiceByTimer = Runnable {
        alarmController.snoozeAlarm()
        finish()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handleIntentAction(intent?.action ?: ACTION_START)
        return super.onStartCommand(intent, flags, startId)

    }

    private fun handleIntentAction(action: String) {
        when (action) {
            ACTION_START -> {
                startForeground(1, createNotification())
                alarmVibrator.startVibration()
                handler.postDelayed(stopServiceByTimer, ALARM_TIME)
            }
            ACTION_STOP -> {
                finish()
            }
            ACTION_SNOOZE -> {
                alarmController.snoozeAlarm()
                finish()
            }
        }
    }


    private fun createNotification(): Notification {
        val channelId = "My_Channel_ID"
        createNotificationChannel(channelId)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("AlarmClock")
            .setContentText("Будильник")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(R.drawable.ic_launcher_foreground, "Dismiss", createStopPendingIntent())
            .addAction(R.drawable.ic_launcher_foreground, "Snooze", createSnoozePendingIntent())
        return notificationBuilder.build()
    }

    private fun createNotificationChannel(channelId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "My Channel"
            val channelDescription = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(channelId, name, importance)
            channel.apply {
                description = channelDescription
            }
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createStopPendingIntent(): PendingIntent {
        return createPendingIntent(ACTION_STOP)
    }

    private fun createSnoozePendingIntent(): PendingIntent {
        return createPendingIntent(ACTION_SNOOZE)
    }

    private fun createPendingIntent(action: String): PendingIntent {
        val intent = Intent(this, AlarmService::class.java)
        intent.setAction(action)
        return PendingIntent.getService(this, 1, intent, 0)
    }

    private fun finish(){
        alarmVibrator.stopVibration()
        handler.removeCallbacksAndMessages(null)
        stopSelf()
    }

    companion object{
        private const val ACTION_START = "START"
        private const val ACTION_STOP = "STOP"
        private const val ACTION_SNOOZE = "SNOOZE"
        private const val ALARM_TIME: Long = 1000*60
    }
}