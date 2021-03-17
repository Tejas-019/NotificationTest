package com.android.notificationtest.fcm

import android.util.Log
import com.android.notificationtest.notification.data.NotificationData
import com.android.notificationtest.notification.repo.INotificationsRepository
import com.android.notificationtest.utils.NotificationsHelper
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import dagger.android.AndroidInjection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject


class TestMessagingService: FirebaseMessagingService() {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    private val TAG = TestMessagingService::class.simpleName

    @Inject
    lateinit var mNotificationsHelper: NotificationsHelper

    @Inject
    lateinit var mNotificationsRepository: INotificationsRepository

    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val notificationsData: NotificationData = Gson().fromJson(remoteMessage.data.toString(), NotificationData::class.java)

        notificationsData.isRepeating?.let {
            if(it) {
                scope.launch {
                    mNotificationsRepository.insert(notificationsData)
                    mNotificationsHelper.scheduleNotification(notificationsData.id, notificationsData.interval)
                }
            }
        }

        mNotificationsHelper.showNotification(notificationsData)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "onNewToken: $token")
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}