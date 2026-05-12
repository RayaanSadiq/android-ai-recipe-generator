package com.rayaans.recipeai.ui.saved

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rayaans.recipeai.data.Recipe
import com.rayaans.recipeai.ui.RecipeViewModel
import com.rayaans.recipeai.ui.Screens

@Composable
fun SavedScreen(viewModel: RecipeViewModel, navController: NavController) {
    val savedRecipes by viewModel.savedRecipes.collectAsState()
    var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Saved Recipes", modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            if (savedRecipes.isEmpty()) {
                item {Text("No recipes saved yet",
                    modifier = Modifier.padding(vertical = 128.dp).fillMaxSize(),
                    textAlign = TextAlign.Center)}
            } else {
                items(savedRecipes) { recipe ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor =
                                if (selectedRecipe == recipe)
                                    MaterialTheme.colorScheme.secondary
                                else
                                    MaterialTheme.colorScheme.surfaceVariant
                        ), modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                            .clickable { selectedRecipe = recipe }) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f).padding(8.dp)) {
                                Text(
                                    text = recipe.title, modifier = Modifier.padding(horizontal = 8.dp),
                                    fontWeight = FontWeight.Bold, fontSize = 22.sp
                                )
                                Text(
                                    text = recipe.ingredients.joinToString(),
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                )
                            }
                            IconButton(onClick = {
                                viewModel.selectRecipe(recipe)
                                navController.navigate(Screens.Recipe)
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = "Open Recipe"
                                )
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = {}) {Text("Share Recipe")}
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {}) {Text("Set Reminder")}
        }
    }
}