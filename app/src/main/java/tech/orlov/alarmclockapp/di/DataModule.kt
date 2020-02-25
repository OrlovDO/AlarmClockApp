package tech.orlov.alarmclockapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import tech.orlov.alarmclockapp.data.AlarmDao
import tech.orlov.alarmclockapp.data.AlarmRoomDatabase
import javax.inject.Singleton

@Module
class DataModule {
    @Singleton
    @Provides
    fun provideAlarmRoomDatabase(context: Context): AlarmRoomDatabase {
        return Room.databaseBuilder(
            context,
            AlarmRoomDatabase::class.java,
            "alarm-database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideAlarmDao(alarmRoomDatabase: AlarmRoomDatabase): AlarmDao {
        return alarmRoomDatabase.getAlarmDao()
    }
}