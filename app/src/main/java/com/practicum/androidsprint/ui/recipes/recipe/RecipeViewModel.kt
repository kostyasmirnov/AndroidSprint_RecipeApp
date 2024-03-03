package com.practicum.androidsprint.ui.recipes.recipe

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import com.practicum.androidsprint.model.Recipe

data class RecipeUiState(
    val favoriteRecipe: Boolean,
    val portionsCount: Int,
)

class RecipeViewModel(
    val recipe: Recipe? = null,
    val recipeDrawable: Drawable? = null,
    val favoriteRecipe: Boolean = false,
    val portionsCount: Int? = null,
    val ingredientsCount: Int? = null,
) : ViewModel()