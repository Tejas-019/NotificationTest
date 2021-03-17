package com.android.notificationtest.dagger

import com.android.notificationtest.notification.data.NotificationsRepository
import com.android.notificationtest.notification.repo.INotificationsRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoriesModule {

    @Provides
    fun provideNotificationsRepository(notificationsRepository: NotificationsRepository): INotificationsRepository = notificationsRepository
}