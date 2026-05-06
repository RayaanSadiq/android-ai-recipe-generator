package com.rayaans.recipeai.ui.ingredients

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun IngredientsScreen(viewModel: IngredientsViewModel) {
    val ingredientText by viewModel.ingredientText.collectAsState()
    val ingredients by viewModel.ingredients.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Your Ingredients")
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = ingredientText,
            onValueChange = {viewModel.updateIngredientText(it)},
            label = {Text("Enter the ingredients you have")})
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {viewModel.addIngredient()}) {
            Text("Add")
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(ingredients) {
                ingredient -> Card(modifier = Modifier.padding(vertical = 4.dp)) {
                    Row {
                        Text(text = ingredient, modifier = Modifier.padding(16.dp))
                        Button(
                            onClick = { viewModel.removeIngredient(ingredient) }) {
                            Text("X")
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {viewModel.clearIngredients()}) {Text("Clear Ingredients")}
        Button(onClick = {}) {Text("Generate Recipe")}
    }
}