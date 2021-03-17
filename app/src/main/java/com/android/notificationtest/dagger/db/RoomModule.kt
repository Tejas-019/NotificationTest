package com.android.notificationtest.dagger.db

import android.content.Context
import com.android.notificationtest.notification.repo.INotificationsDao
import dagger.Module
import dagger.Provides

@Module
class RoomModule() {
    @Provides
    fun providesRoomDb(context: Context): AppRoomDatabase = AppRoomDatabase.getDatabase(context)

    @Provides
    fun providesNotificationsDao(db: AppRoomDatabase): INotificationsDao = db.notificationsDao()
}