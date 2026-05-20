package com.rayaans.recipeai.ui.saved


import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
// Coil library used for loading images from saved URIs asynchronously
import coil.compose.AsyncImage
import com.rayaans.recipeai.data.db.Recipe
import com.rayaans.recipeai.notifications.NotificationHelper
import com.rayaans.recipeai.ui.Screens
import com.rayaans.recipeai.ui.recipe.RecipeViewModel
import java.util.Calendar

@Composable
fun SavedScreen(viewModel: RecipeViewModel, navController: NavController) {
    val savedRecipes by viewModel.savedRecipes.collectAsState(initial = emptyList())
    var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }
    val context = LocalContext.current
    var imageRecipe by remember {mutableStateOf<Recipe?>(null)}

    val imagePickerLauncher = rememberLauncherForActivityResult(
        //OpenDocument used as it allows images to persist after restarts while GetContent doesn't
        contract = ActivityResultContracts.OpenDocument()
        ) { uri: Uri? ->
            uri?.let {
                context.contentResolver.takePersistableUriPermission(it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION)

                imageRecipe?.let { recipe -> viewModel.updateRecipe(
                    recipe.copy(imageUri = it.toString()))
                }
            }
        }

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
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween) {
                            Row(modifier = Modifier.weight(1f).padding(8.dp)) {
                                recipe.imageUri?.let {
                                    AsyncImage(model = it, contentDescription = "Recipe Image",
                                        modifier = Modifier.size(72.dp),
                                        contentScale = ContentScale.Crop)
                                    Spacer(modifier = Modifier.width(12.dp))
                                }

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = recipe.title,
                                        modifier = Modifier.padding(horizontal = 8.dp),
                                        fontWeight = FontWeight.Bold, fontSize = 22.sp)

                                    Text(text = recipe.ingredients.take(3).joinToString(),
                                        modifier = Modifier.padding(horizontal = 8.dp),
                                        maxLines = 1, overflow = TextOverflow.Ellipsis)
                                }
                            }
                            IconButton(onClick = {
                                viewModel.selectRecipe(recipe)
                                navController.navigate(Screens.Recipe.name)
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
            Button(onClick = {selectedRecipe?.let { recipe ->
                val shareText = buildString {
                    append(recipe.title)

                    append("\n\nIngredients:\n")
                    recipe.ingredients.forEach {append("- $it\n")}

                    append("\nInstructions:\n")
                    recipe.instructions.forEach {append("$it\n")}
                }

                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, shareText)
                }

                context.startActivity(Intent.createChooser(shareIntent,"Share Recipe"))
            }}, enabled = selectedRecipe != null) {Text("Share Recipe")}
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {selectedRecipe?.let { recipe ->
                val calendar = Calendar.getInstance()

                TimePickerDialog(context, { _, hour, minute ->
                        val reminderTime = Calendar.getInstance().apply {
                                set(Calendar.HOUR_OF_DAY, hour)
                                set(Calendar.MINUTE, minute)
                                set(Calendar.SECOND, 0)

                                if (before(Calendar.getInstance())) {
                                    add(Calendar.DAY_OF_MONTH, 1)
                                }
                            }

                        NotificationHelper.scheduleNotification(context,
                            reminderTime.timeInMillis, recipe.title)
                    }, calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), true).show()
            }}, enabled = selectedRecipe != null) {Text("Set Reminder")}
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {selectedRecipe?.let{
                imageRecipe = it
                imagePickerLauncher.launch(arrayOf("image/*"))
            }}, enabled = selectedRecipe != null) {Text("Add Image")}
        }
    }
}