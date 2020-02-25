package tech.orlov.alarmclockapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(AlarmDbo::class), version = 1)
abstract class AlarmRoomDatabase : RoomDatabase() {

    abstract fun getAlarmDao(): AlarmDao

}