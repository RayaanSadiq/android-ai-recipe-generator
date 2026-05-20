package com.rayaans.recipeai.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val extraRequests: String = "",
    val imageUri: String? = null
)