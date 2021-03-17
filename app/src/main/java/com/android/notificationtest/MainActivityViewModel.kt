package com.android.notificationtest

import androidx.lifecycle.ViewModel
import com.android.notificationtest.notification.repo.INotificationsRepository
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val mNotificationsRepository: INotificationsRepository) : ViewModel() {

    suspend fun getAllNotificationIds(): List<Int>? {
        return mNotificationsRepository.findAllIds()
    }

    suspend fun clearAllNotifications() {
        mNotificationsRepository.deleteAll()
    }
}