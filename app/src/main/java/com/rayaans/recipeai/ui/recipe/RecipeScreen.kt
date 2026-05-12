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
import com.rayaans.recipeai.ui.RecipeViewModel

@Composable
fun RecipeScreen(viewModel: RecipeViewModel) {
    // 0 = Ingredients tab, 1 = instructions tab
    var currentTab by remember { mutableIntStateOf(0) }
    var adjustments by remember { mutableStateOf("") }


    val recipe by viewModel.currentRecipe.collectAsState()
    val ingredients = recipe?.ingredients ?: emptyList()
    val instructions = recipe?.instructions ?: emptyList()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = recipe?.title ?: "Recipe", modifier = Modifier.fillMaxWidth(),
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
            if (ingredients.isEmpty() or instructions.isEmpty()) {
                item {Text("No recipe generated yet",
                    modifier = Modifier.padding(vertical = 128.dp).fillMaxSize(), textAlign = TextAlign.Center)}
            } else {
                if (currentTab == 0) {
                    items(ingredients) { ingredient ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                        ) {
                            Text(
                                text = ingredient,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                } else {
                    items(instructions) { instruction ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Text(
                                text = instruction,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = adjustments,
            onValueChange = {adjustments = it},
            label = { Text ("Add any adjustments you want to make",
                overflow = TextOverflow.Ellipsis) }, modifier = Modifier.fillMaxWidth(),
            minLines = 3, maxLines = 5)
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = {}) { Text("Adjust Recipe") }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                recipe?.let { viewModel.saveRecipe(it) }
            }) { Text("Save Recipe") }
        }
    }
}