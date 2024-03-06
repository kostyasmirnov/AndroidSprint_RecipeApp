package com.practicum.androidsprint.ui.recipes.recipe

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.androidsprint.model.Recipe

data class RecipeUiState(
    val recipe: Recipe? = null,
    val recipeDrawable: Drawable? = null,
    val portionsCount: Int? = 1,
    var isFavorite: Boolean = false,
)

class RecipeViewModel: ViewModel() {
    private val _recipeState = MutableLiveData<RecipeUiState>()
    val recipeState: LiveData<RecipeUiState>  = _recipeState

    init {
        _recipeState.value = RecipeUiState(isFavorite = false)
        Log.i("!!!", _recipeState.value.toString())
    }
}