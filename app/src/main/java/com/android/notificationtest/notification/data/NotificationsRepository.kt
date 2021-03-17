package com.android.notificationtest.notification.data

import com.android.notificationtest.notification.repo.INotificationsDao
import com.android.notificationtest.notification.repo.INotificationsRepository
import javax.inject.Inject

class NotificationsRepository @Inject constructor(private val mNotificationsDao: INotificationsDao) : INotificationsRepository {
    override suspend fun insert(notificationData: NotificationData) {
        mNotificationsDao.insert(notificationData)
    }

    override suspend fun getNotificationData(notificationId: Int): NotificationData {
        return mNotificationsDao.find(notificationId)
    }

    override suspend fun findAllIds(): List<Int>? {
        return mNotificationsDao.findAllIds()
    }

    override suspend fun deleteAll() {
        return mNotificationsDao.deleteAll()
    }
}