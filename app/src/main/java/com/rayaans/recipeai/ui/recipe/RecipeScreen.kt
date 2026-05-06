package com.rayaans.recipeai.ui.recipe

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RecipeScreen() {
    // 0 = Ingredients tab, 1 = instructions tab
    var currentTab by remember { mutableIntStateOf(0) }

    val ingredients = listOf("Chicken", "Rice", "Egg")
    val instructions = listOf("Cook the rice", "Sear the chicken", "Mix them together with egg")

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Chicken Fried Rice")
        Spacer(modifier = Modifier.height(16.dp))

        TabRow(currentTab) {
            Tab(
                selected = currentTab == 0,
                onClick = {currentTab = 0},
                text = { Text("Ingredients")}
            )
            Tab(
                selected = currentTab == 1,
                onClick = {currentTab = 1},
                text = { Text("Instructions")}
            )
        }

        if (currentTab == 0) {
            ingredients.forEach { ingredient ->
                Text(ingredient)
            }
        } else {
            instructions.forEach { instruction ->
                Text(instruction)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {}) {Text("Adjust Recipe")}
        Button(onClick = {}) {Text("Save Recipe")}
    }
}