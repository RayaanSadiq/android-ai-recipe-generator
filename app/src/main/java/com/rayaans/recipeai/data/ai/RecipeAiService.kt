package com.rayaans.recipeai.data.ai

// Used Firebase Gemini AI for recipe generation

import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend

class RecipeAiService {
    private val generativeModel = Firebase.ai(backend =
        GenerativeBackend.googleAI()).generativeModel(modelName = "gemini-2.5-flash")

    suspend fun generateRecipe(prompt: String): String? {
        return try {
            generativeModel.generateContent(prompt).text
        } catch (e: Exception){
            "Error generating recipe. Please try again."
        }
    }
}