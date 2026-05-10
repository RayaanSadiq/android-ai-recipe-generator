package com.rayaans.recipeai.ui

import com.rayaans.recipeai.data.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RecipeViewModel {
    private var _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes = _recipes.asStateFlow()

    private var _savedRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val savedRecipes = _savedRecipes.asStateFlow()

    private var _currentRecipe = MutableStateFlow<Recipe?>(null)
    val currentRecipe = _currentRecipe.asStateFlow()

    fun saveRecipe(recipe: Recipe) {
        if (recipe !in _savedRecipes.value) {
            _savedRecipes.value += recipe
        }
    }

    fun selectRecipe(recipe: Recipe) {
        _currentRecipe.value = recipe
    }
}