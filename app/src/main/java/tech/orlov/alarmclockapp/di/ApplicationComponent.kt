package tech.orlov.alarmclockapp.di

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import tech.orlov.alarmclockapp.App
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        DataModule::class
    ]
)
interface ApplicationComponent {

    fun inject(application: App)

    @Component.Builder
    interface Builder {

        fun build(): ApplicationComponent

        @BindsInstance
        fun applicationBind(context: Context): Builder

    }
}