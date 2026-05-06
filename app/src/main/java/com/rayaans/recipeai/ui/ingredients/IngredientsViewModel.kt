package com.rayaans.recipeai.ui.ingredients

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class IngredientsViewModel {
    private val _ingredientText = MutableStateFlow("")
    val ingredientText = _ingredientText.asStateFlow()

    private val _ingredients = MutableStateFlow<List<String>>(emptyList())
    val ingredients = _ingredients.asStateFlow()

    fun updateIngredientText(newText: String) {
        _ingredientText.value = newText
    }

    fun addIngredient() {
        if ((_ingredientText.value.isNotBlank()) && (_ingredientText.value !in _ingredients.value)) {
            _ingredients.value += _ingredientText.value
            _ingredientText.value = ""
        }
    }

    fun removeIngredient(ingredient: String) {_ingredients.value -= ingredient}

    fun clearIngredients() {_ingredients.value = emptyList()}
}