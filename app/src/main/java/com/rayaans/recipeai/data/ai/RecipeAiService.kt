package com.rayaans.recipeai.data.ai

import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend

class RecipeAiService {
    private val generativeModel = Firebase.ai(backend =
        GenerativeBackend.googleAI()).generativeModel(modelName = "gemini-2.5-flash")

    suspend fun generateRecipce(prompt: String): String? {
        return generativeModel.generateContent(prompt).text
    }
}