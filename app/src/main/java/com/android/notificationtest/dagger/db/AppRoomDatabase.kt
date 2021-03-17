package com.android.notificationtest.dagger.db

import android.content.Context
import androidx.room.*
import com.android.notificationtest.notification.data.NotificationData
import com.android.notificationtest.notification.data.NotificationsDao

@Database(entities = [(NotificationData::class)], version = 1, exportSchema = false)
@TypeConverters(ImagesConverter::class)
abstract class AppRoomDatabase : RoomDatabase() {

    companion object {
        fun getDatabase(context: Context): AppRoomDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppRoomDatabase::class.java,
                "app_db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun notificationsDao(): NotificationsDao
}