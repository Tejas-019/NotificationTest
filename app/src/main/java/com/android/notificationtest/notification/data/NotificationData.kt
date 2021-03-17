package com.android.notificationtest.notification.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.android.notificationtest.dagger.db.ImagesConverter

@Entity
data class NotificationData(
    @PrimaryKey
    var id: Int,
    var title: String?,
    var info: String?,
    var flag: String?,
    @TypeConverters(ImagesConverter::class)
    var deals_images: List<String>?,
    val isRepeating: Boolean?,
    val interval: Long?
)