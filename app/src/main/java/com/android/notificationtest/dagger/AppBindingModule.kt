package com.android.notificationtest.dagger

import com.android.notificationtest.MainActivity
import com.android.notificationtest.fcm.TriggerNotificationReceiver
import com.android.notificationtest.fcm.TestMessagingService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface AppBindingModule {
    @ContributesAndroidInjector
    fun bindTestMessagingService(): TestMessagingService

    @ContributesAndroidInjector
    fun bindAlarmReceiver(): TriggerNotificationReceiver

    @ContributesAndroidInjector
    fun bindMainActivity(): MainActivity
}