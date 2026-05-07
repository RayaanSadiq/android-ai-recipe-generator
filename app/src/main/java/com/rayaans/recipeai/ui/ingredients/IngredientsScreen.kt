package com.rayaans.recipeai.ui.ingredients

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun IngredientsScreen(viewModel: IngredientsViewModel) {
    val ingredientText by viewModel.ingredientText.collectAsState()
    val ingredients by viewModel.ingredients.collectAsState()
    val additionalReqs by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Your Ingredients", modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextField(
                value = ingredientText,
                onValueChange = { viewModel.updateIngredientText(it) },
                label = { Text("Enter the ingredients you have") },
                modifier = Modifier.weight(1f))

            Button(
                onClick = { viewModel.addIngredient() }) {
                Text("Add")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            if (ingredients.isEmpty()) {
                item {Text("No ingredients added yet",
                    modifier = Modifier.padding(vertical = 128.dp).fillMaxSize(), textAlign = TextAlign.Center)}
            } else {
                items(ingredients) { ingredient ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = ingredient, modifier = Modifier.padding(16.dp)
                                    .weight(1f), maxLines = 1, overflow = TextOverflow.Ellipsis
                            )
                            Button(
                                onClick = { viewModel.removeIngredient(ingredient) }) {
                                Text("Remove")
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))


        TextField(
            value = additionalReqs, onValueChange = {},
            label = { Text ("Add any extra requests you have",
                overflow = TextOverflow.Ellipsis) }, modifier = Modifier.fillMaxWidth(),
                minLines = 3, maxLines = 5)
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = {viewModel.clearIngredients()}) {Text("Clear Ingredients")}
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {}) {Text("Generate Recipe")}
        }
    }
}