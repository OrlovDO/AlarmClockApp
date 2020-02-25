package tech.orlov.alarmclockapp.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import tech.orlov.alarmclockapp.MainActivity
import tech.orlov.alarmclockapp.alarm.AlarmBootReceiver
import tech.orlov.alarmclockapp.alarm.AlarmService
import tech.orlov.alarmclockapp.ui.main.MainFragment

@Module
abstract class ApplicationModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeAlarmService(): AlarmService

    @ContributesAndroidInjector
    abstract fun contributeAlarmBootReceiver(): AlarmBootReceiver

}