package com.rayaans.recipeai.data

data class Recipe (
    val title: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val extraRequests: String = ""
)