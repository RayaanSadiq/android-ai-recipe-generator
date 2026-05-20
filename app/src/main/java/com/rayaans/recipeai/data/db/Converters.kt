package com.rayaans.recipeai.data.db

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromList(items: List<String>): String {
        return items.joinToString("|")
    }

    @TypeConverter
    fun toList(data: String): List<String> {
        if (data.isBlank()) {
            return emptyList()
        }
        return data.split("|")
    }
}