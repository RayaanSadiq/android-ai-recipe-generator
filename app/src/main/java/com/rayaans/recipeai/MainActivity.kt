package com.rayaans.recipeai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rayaans.recipeai.ui.theme.RecipeAITheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rayaans.recipeai.ui.ingredients.IngredientsScreen
import com.rayaans.recipeai.ui.recipe.RecipeScreen
import com.rayaans.recipeai.ui.saved.SavedScreen
import com.rayaans.recipeai.ui.Screens
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import com.rayaans.recipeai.ui.ingredients.IngredientsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val ingredientsViewModel = IngredientsViewModel()

            Scaffold( bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        selected = false,
                        onClick = { navController.navigate(Screens.Ingredients.name) },
                        label = { Text("Ingredients") },
                        icon = {}
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = { navController.navigate(Screens.Recipe.name) },
                        label = { Text("Recipe") },
                        icon = {}
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = { navController.navigate(Screens.Saved.name) },
                        label = { Text("Saved") },
                        icon = {}
                    )
                }
            } ) { innerPadding ->

                NavHost(
                    navController = navController,
                    startDestination = Screens.Ingredients.name,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(route = Screens.Ingredients.name) {
                        IngredientsScreen(ingredientsViewModel)
                    }
                    composable(route = Screens.Recipe.name) {
                        RecipeScreen()
                    }
                    composable(route = Screens.Saved.name) {
                        SavedScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RecipeAITheme {
        Greeting("Android")
    }
}