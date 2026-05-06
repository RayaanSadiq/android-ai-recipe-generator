package com.rayaans.recipeai.ui.saved

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SavedScreen() {
    val savedRecipes = listOf("Fried rice", "Omelette", "Pasta")
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Saved Recipes")
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(savedRecipes) { recipe ->
                Card(modifier=Modifier.padding(vertical=4.dp).clickable {}) {
                    Text(text = recipe, modifier = Modifier.padding(16.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))


        Button(onClick = {}) {Text("Share Recipe")}
        Button(onClick = {}) {Text("Set Reminder")}
    }
}