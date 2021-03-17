package com.android.notificationtest.dagger.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ImagesConverter {
    @TypeConverter
    fun toImages(json: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(images: List<String>) = Gson().toJson(images)
}