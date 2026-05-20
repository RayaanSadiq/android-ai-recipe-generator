package com.rayaans.recipeai

import android.net.Uri
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertNotNull
import org.junit.Test


class RecipeProviderTest {
    @Test
    fun queryRecipes_returnsCursor() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        val cursor = context.contentResolver.query(
            Uri.parse("content://com.rayaans.recipeai/recipes"),
            null, null, null, null)

        assertNotNull(cursor)
    }
}