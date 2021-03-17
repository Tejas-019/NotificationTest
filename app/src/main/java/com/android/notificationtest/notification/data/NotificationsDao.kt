package com.android.notificationtest.notification.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.notificationtest.notification.repo.INotificationsDao

@Dao
interface NotificationsDao : INotificationsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(notificationData: NotificationData)

    @Query("SELECT * FROM NotificationData where id = :id")
    override suspend fun find(id: Int): NotificationData

    @Query("SELECT id FROM NotificationData")
    override suspend fun findAllIds(): List<Int>?

    @Query("DELETE FROM NotificationData")
    override suspend fun deleteAll()
}