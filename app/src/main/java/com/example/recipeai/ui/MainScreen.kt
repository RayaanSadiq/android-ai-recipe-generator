package com.example.recipeai.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.material3.Text

@Composable
fun MainScreen(viewModel: RecipeViewModel) {
    val text by viewModel.uiState.collectAsState()

    Column {
        Text(text = text)

        TextField(value = text, onValueChange = {viewModel.updateText(it)})
    }
}