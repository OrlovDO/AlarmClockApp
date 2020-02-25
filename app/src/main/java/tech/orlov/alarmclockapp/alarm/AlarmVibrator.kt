package tech.orlov.alarmclockapp.alarm

import android.content.Context
import android.os.Vibrator
import javax.inject.Inject

class AlarmVibrator @Inject constructor(context: Context) {

    private val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    fun startVibration() {
        vibrator.vibrate(VIBRATION_PATTERN, 0)
    }

    fun stopVibration() {
        vibrator.cancel()
    }

    companion object{
        private val VIBRATION_PATTERN = longArrayOf(500, 300)
    }
}