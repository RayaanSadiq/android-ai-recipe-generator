package com.rayaans.recipeai.ui.recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rayaans.recipeai.ui.Screens
import com.rayaans.recipeai.ui.ingredients.IngredientsViewModel

@Composable
fun RecipeScreen(recipeViewModel: RecipeViewModel, ingredientsViewModel: IngredientsViewModel,
                 navController: NavController) {
    // 0 = Ingredients tab, 1 = instructions tab
    var currentTab by remember { mutableIntStateOf(0) }
    var adjustments by remember { mutableStateOf("") }


    val recipe by recipeViewModel.currentRecipe.collectAsState()
    val ingredients = recipe?.ingredients ?: emptyList()
    val instructions = recipe?.instructions ?: emptyList()
    val aiGenerating by recipeViewModel.aiGenerating.collectAsState()
    val errorMessage by recipeViewModel.errorMessage.collectAsState()

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

        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error, textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth())
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            if (recipe == null) {
                if (aiGenerating) {
                    item {
                        Box(modifier = Modifier.fillParentMaxSize(),
                        contentAlignment = Alignment.Center) {CircularProgressIndicator()}
                    }
                } else {
                    item {
                        Text(
                            "No recipe generated yet",
                            modifier = Modifier.padding(vertical = 128.dp).fillMaxSize(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
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
            Button(onClick = {recipe?.let {
                    ingredientsViewModel.loadRecipeIngredients(it.ingredients)
                    navController.navigate(Screens.Ingredients.name)
                }},
                enabled = recipe != null) {Text("Adjust Recipe")}

            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                recipe?.let {
                    recipeViewModel.saveRecipe(it)
                    navController.navigate(Screens.Saved.name)
                }
            }, enabled = recipe != null) { Text("Save Recipe") }
        }
    }
}