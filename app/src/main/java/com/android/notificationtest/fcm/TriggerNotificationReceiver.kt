package com.android.notificationtest.fcm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.android.notificationtest.constants.Constants
import com.android.notificationtest.notification.repo.INotificationsRepository
import com.android.notificationtest.utils.NotificationsHelper
import dagger.android.AndroidInjection
import kotlinx.coroutines.*
import javax.inject.Inject

class TriggerNotificationReceiver : BroadcastReceiver() {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    @Inject
    lateinit var mNotificationsRepository: INotificationsRepository

    @Inject
    lateinit var mNotificationsHelper: NotificationsHelper

    override fun onReceive(context: Context, intent: Intent) {
        AndroidInjection.inject(this, context)
        val notificationId = intent.getIntExtra(Constants.NOTIFICATION_ID, 0)
        if(notificationId != 0) {
            scope.launch {
                val notificationData = mNotificationsRepository.getNotificationData(notificationId)
                mNotificationsHelper.showNotification(notificationData)
            }
        }

    }
}