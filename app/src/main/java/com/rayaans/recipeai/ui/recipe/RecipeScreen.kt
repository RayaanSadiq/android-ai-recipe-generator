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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun RecipeScreen() {
    // 0 = Ingredients tab, 1 = instructions tab
    var currentTab by remember { mutableIntStateOf(0) }
    var adjustments by remember { mutableStateOf("") }

    val ingredients = listOf("•Chicken", "•Rice", "•Egg")
    val instructions = listOf("1. Cook the rice", "2. Sear the chicken", "3. Mix them together with egg")

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Chicken Fried Rice", modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center)
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

        LazyColumn(modifier = Modifier.weight(1f)) {
            if (currentTab == 0) {
                items(ingredients) { ingredient ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp)
                    ) {
                        Text(text = ingredient,
                        modifier = Modifier.padding(12.dp))
                    }
                }
            } else {
                items(instructions) { instruction ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(text = instruction,
                            modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = adjustments,
            onValueChange = {},
            label = { Text ("Add any adjustments you want to make",
                overflow = TextOverflow.Ellipsis) }, modifier = Modifier.fillMaxWidth(),
            minLines = 3, maxLines = 5)
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = {}) { Text("Adjust Recipe") }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {}) { Text("Save Recipe") }
        }
    }
}