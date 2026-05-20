package com.rayaans.recipeai

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.rayaans.recipeai.data.db.RecipeDatabase
import com.rayaans.recipeai.notifications.NotificationHelper
import com.rayaans.recipeai.ui.Screens
import com.rayaans.recipeai.ui.ingredients.IngredientsScreen
import com.rayaans.recipeai.ui.ingredients.IngredientsViewModel
import com.rayaans.recipeai.ui.recipe.RecipeScreen
import com.rayaans.recipeai.ui.recipe.RecipeViewModel
import com.rayaans.recipeai.ui.saved.SavedScreen
import com.rayaans.recipeai.ui.theme.RecipeAITheme

class MainActivity : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = Room.databaseBuilder(applicationContext,RecipeDatabase::class.java,
            "recipe_database").fallbackToDestructiveMigration().build()
        database.recipeDao()
        val recipeViewModel = RecipeViewModel(database.recipeDao())

        val ingredientsViewModel = IngredientsViewModel()

        NotificationHelper.createNotificationChannel(this)

        setContent {
            val navController = rememberNavController()
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentScreen = backStackEntry?.destination?.route

            Scaffold( bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentScreen == Screens.Ingredients.name,
                        onClick = { navController.navigate(Screens.Ingredients.name) },
                        label = { Text("Ingredients") },
                        icon = {Icon(imageVector = Icons.AutoMirrored.Filled.List,
                            contentDescription = "Ingredients")}
                    )
                    NavigationBarItem(
                        selected = currentScreen == Screens.Recipe.name,
                        onClick = { navController.navigate(Screens.Recipe.name) },
                        label = { Text("Recipe") },
                        icon = {Icon(imageVector = Icons.Default.Home,
                            contentDescription = "Recipe")}
                    )
                    NavigationBarItem(
                        selected = currentScreen == Screens.Saved.name,
                        onClick = { navController.navigate(Screens.Saved.name) },
                        label = { Text("Saved") },
                        icon = {Icon(imageVector = Icons.Default.Star,
                            contentDescription = "Saved")}
                    )
                }
            } ) { innerPadding ->

                NavHost(
                    navController = navController,
                    startDestination = Screens.Ingredients.name,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(route = Screens.Ingredients.name) {
                        IngredientsScreen(ingredientsViewModel, recipeViewModel, navController)
                    }
                    composable(route = Screens.Recipe.name) {
                        RecipeScreen(recipeViewModel, ingredientsViewModel, navController)
                    }
                    composable(route = Screens.Saved.name) {
                        SavedScreen(recipeViewModel, navController)
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