package com.android.notificationtest.notification.repo

import com.android.notificationtest.notification.data.NotificationData

interface INotificationsDao {
    suspend fun insert(notificationData: NotificationData)
    suspend fun find(id: Int): NotificationData
    suspend fun findAllIds(): List<Int>?
    suspend fun deleteAll()
}

