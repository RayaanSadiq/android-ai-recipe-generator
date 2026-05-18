package com.rayaans.recipeai.ui

import androidx.lifecycle.ViewModel
import com.rayaans.recipeai.data.Recipe
import com.rayaans.recipeai.data.ai.RecipeAiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {
    private val recipeAiService = RecipeAiService()

    private var _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes = _recipes.asStateFlow()

    private var _savedRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val savedRecipes = _savedRecipes.asStateFlow()

    private var _currentRecipe = MutableStateFlow<Recipe?>(null)
    val currentRecipe = _currentRecipe.asStateFlow()

    private var _generatedRecipe = MutableStateFlow("")
    val generatedRecipe = _generatedRecipe.asStateFlow()

    fun saveRecipe(recipe: Recipe) {
        if (recipe !in _savedRecipes.value) {
            _savedRecipes.value += recipe
        }
    }

    fun selectRecipe(recipe: Recipe) {
        _currentRecipe.value = recipe
    }

    fun generateRecipe(ingredients: List<String>, extraReqs: String) {
        viewModelScope.launch {
            val response = recipeAiService.generateRecipce(
                buildString {
                    append("Create a recipe using these ingredients: ")
                    append(ingredients.joinToString())
                    append(". ")
                    append("Additional requests: ")
                    append(extraReqs)
                    append(". ")
                    append("Return a recipe title, ingredients list, and numbered instructions. " +
                            "Use plain text only. Do not use markdown.")
                }
            )

            if (response != null) {
                val recipe = parseRecipe(response)
                _currentRecipe.value = recipe
            }
        }
    }

    fun parseRecipe(response: String): Recipe {
        val recipeLines = response.lines()

        val title = recipeLines.firstOrNull() {it.isNotBlank()} ?: "Generated Recipe"

        val ingredients = mutableListOf<String>()
        val instructions = mutableListOf<String>()

        var recipeSection = ""

        for (rawLine in recipeLines) {
            if (rawLine.isBlank()) { continue }
            val line = rawLine.trim()

            if (line.contains("ingredient", true)) {
                recipeSection = "ingredients"

            } else if (line.contains("instruction", true)) {
                recipeSection = "instructions"

            } else if (recipeSection == "ingredients") {
                ingredients.add(line)

            } else if (recipeSection == "instructions") {
                instructions.add(line)
            }
        }

        return Recipe(title, ingredients, instructions)
    }
}