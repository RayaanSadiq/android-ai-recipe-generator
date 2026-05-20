package com.rayaans.recipeai.ui.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rayaans.recipeai.data.ai.RecipeAiService
import com.rayaans.recipeai.data.db.Recipe
import com.rayaans.recipeai.data.db.RecipeDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecipeViewModel(private val recipeDao: RecipeDao) : ViewModel() {
    private val recipeAiService = RecipeAiService()

    private var _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes = _recipes.asStateFlow()

    val savedRecipes = recipeDao.getAllRecipes()

    private var _currentRecipe = MutableStateFlow<Recipe?>(null)
    val currentRecipe = _currentRecipe.asStateFlow()

    private val _aiGenerating = MutableStateFlow(false)
    val aiGenerating = _aiGenerating.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    fun saveRecipe(recipe: Recipe) {
        viewModelScope.launch { recipeDao.addRecipe(recipe) }
    }

    fun selectRecipe(recipe: Recipe) {
        _currentRecipe.value = recipe
    }

    fun generateRecipe(ingredients: List<String>, extraReqs: String) {
        _aiGenerating.value = true
        _errorMessage.value = null
        viewModelScope.launch {
            val response = recipeAiService.generateRecipe(
                buildString {
                    append("Create a recipe using these ingredients: ")
                    append(ingredients.joinToString())
                    append(". ")
                    append("Additional requests: ")
                    append(extraReqs)
                    append(". ")
                    append("Return ONLY:\n" +
                            "1. Recipe title on first line\n" +
                            "2. A line that says Ingredients:\n" +
                            "3. Ingredient list\n" +
                            "4. A line that says Instructions:\n" +
                            "5. Numbered instructions\n" +
                            "Keep each instruction brief. Use plain text only. Do not use markdown.")
                }
            )

            if (response != null && !response.contains("Error generating")) {
                val recipe = parseRecipe(response, extraReqs)
                _currentRecipe.value = recipe
            }else {
                _errorMessage.value = "Failed to generate recipe."
            }
            _aiGenerating.value = false
        }
    }

    fun parseRecipe(response: String, extraReqs: String): Recipe {
        val recipeLines = response.lines()

        val title = recipeLines.firstOrNull {it.isNotBlank()} ?: "Generated Recipe"

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

        return Recipe(
            title = title, ingredients = ingredients,
            instructions = instructions, extraRequests = extraReqs
        )
    }

    fun deleteRecipe(recipe: Recipe) {
        viewModelScope.launch {recipeDao.deleteRecipe(recipe.id)}
    }

    fun updateRecipe(recipe: Recipe) {
        viewModelScope.launch {recipeDao.updateRecipe(recipe)}
    }
}