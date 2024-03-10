package com.practicum.androidsprint.ui.recipes.recipe

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.practicum.androidsprint.data.Constants
import com.practicum.androidsprint.data.STUB
import com.practicum.androidsprint.model.Recipe

data class RecipeUiState(
    val recipe: Recipe? = null,
    val recipeDrawable: Drawable? = null,
    val portionsCount: Int? = 1,
    var isFavorite: Boolean = false,
)

class RecipeViewModel(private val application: Application) : AndroidViewModel(application) {
    private val _recipeState = MutableLiveData<RecipeUiState>()
    val recipeState: LiveData<RecipeUiState>  = _recipeState

    fun loadRecipe(recipeId: Int) {
        // TODO: Load recipe from network
        val recipe = STUB.getRecipeById(recipeId)
        _recipeState.value = RecipeUiState().copy(
            recipe = recipe,
            portionsCount = recipeState.value?.portionsCount ?: 1,
            isFavorite = getFavorites().contains(recipeId.toString())
        )
    }

    private fun getFavorites(): MutableSet<String> {
        val sharedPrefs =
            application.getSharedPreferences(Constants.SHARED_PREFS_RECIPES, Context.MODE_PRIVATE)
        val favoritesRecipe = sharedPrefs?.getStringSet(Constants.SHARED_PREFS_RECIPES_DATA, null)
        return favoritesRecipe?.let { HashSet(it) } ?: hashSetOf()
    }

    fun onFavoritesClicked() {
        val isFavorite = getFavorites().contains(recipeState.value?.recipe?.id.toString())
        val recipeId = recipeState.value?.recipe?.id.toString()
        val favoritesSet = getFavorites()

        if (isFavorite) {
            saveFavorites(favoritesSet.apply { remove(recipeId) })
            _recipeState.value = _recipeState.value?.copy(
                isFavorite = false
            )
        } else {
            saveFavorites(favoritesSet.apply { add(recipeId) })
            _recipeState.value = _recipeState.value?.copy(
                isFavorite = true
            )
        }
    }

    private fun saveFavorites(recipesIds: Set<String>) {
        val sharedPrefs =
            application.getSharedPreferences(Constants.SHARED_PREFS_RECIPES, Context.MODE_PRIVATE)
        with(sharedPrefs.edit()) {
            putStringSet(Constants.SHARED_PREFS_RECIPES_DATA, recipesIds)
            apply()
        }
    }
}