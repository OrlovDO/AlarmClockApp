package tech.orlov.alarmclockapp

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.BroadcastReceiver
import dagger.android.*
import tech.orlov.alarmclockapp.di.DaggerApplicationComponent
import javax.inject.Inject

class App : Application(), HasActivityInjector, HasServiceInjector, HasBroadcastReceiverInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var dispatchingAndroidServiceInjector: DispatchingAndroidInjector<Service>
    @Inject
    lateinit var dispatchingAndroidReceiverInjector: DispatchingAndroidInjector<BroadcastReceiver>

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder()
            .applicationBind(this)
            .build()
            .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector
    override fun serviceInjector(): AndroidInjector<Service> = dispatchingAndroidServiceInjector
    override fun broadcastReceiverInjector(): AndroidInjector<BroadcastReceiver> =
        dispatchingAndroidReceiverInjector

}