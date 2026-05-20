package com.rayaans.recipeai.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.room.Room
import com.rayaans.recipeai.data.db.RecipeDatabase

// Custom content provider so recipes are exposed externally
class RecipeProvider : ContentProvider() {
    private lateinit var database: RecipeDatabase

    companion object {
        const val AUTHORITY = "com.rayaans.recipeai"
        const val RECIPES = 1

        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "recipes", RECIPES)
        }
    }

    override fun onCreate(): Boolean {
        context?.let {
            database = Room.databaseBuilder(it, RecipeDatabase::class.java,
                "recipe_database").build()
        }
        return true
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?,
                       selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        return when (uriMatcher.match(uri)) {
            RECIPES -> {database.openHelper.readableDatabase.query("SELECT * FROM recipes")}
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {return null}

    override fun insert(uri: Uri, values: ContentValues?): Uri? {return null}

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?
    ): Int {return 0}

    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<out String>?
    ): Int {return 0}
}