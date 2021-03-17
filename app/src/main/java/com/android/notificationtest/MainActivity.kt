package com.android.notificationtest

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.android.notificationtest.fcm.TriggerNotificationReceiver
import com.android.notificationtest.utils.dagger.ViewModelFactory
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

  @Inject
  lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
  @Inject
  lateinit var viewModelFactory: ViewModelFactory

  lateinit var mMainActivityViewModel: MainActivityViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    AndroidInjection.inject(this)
    mMainActivityViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)

    lifecycleScope.launch {
      val notificationIds = mMainActivityViewModel.getAllNotificationIds()

      if(!notificationIds.isNullOrEmpty()) {
        notificationIds.forEach { notificationId ->
          val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
          val myIntent = Intent(applicationContext, TriggerNotificationReceiver::class.java)
          val pendingIntent = PendingIntent.getBroadcast(applicationContext, notificationId, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)
          alarmManager.cancel(pendingIntent)
        }
        mMainActivityViewModel.clearAllNotifications()
      }
    }

  }
}