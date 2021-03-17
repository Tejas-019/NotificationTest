package com.android.notificationtest.notification.repo

import com.android.notificationtest.notification.data.NotificationData

interface INotificationsRepository {
    suspend fun insert(notificationData: NotificationData)
    suspend fun getNotificationData(notificationId: Int): NotificationData
    suspend fun findAllIds(): List<Int>?
    suspend fun deleteAll()
}