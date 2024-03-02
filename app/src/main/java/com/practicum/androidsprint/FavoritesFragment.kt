package com.practicum.androidsprint

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.LinearLayoutManager
import com.practicum.androidsprint.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val binding by lazy {
        FragmentFavoritesBinding.inflate(layoutInflater)
    }

    private var recipeTitle: String? = null
    private var recipeImageUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        val favoriteRecipeIdsStringSet = getFavoriteRecipeIds()
        if (favoriteRecipeIdsStringSet.isEmpty()) {
            binding.tvNoData.visibility = View.VISIBLE
            binding.rvFavoriteRecipes.visibility = View.GONE
        } else {
            binding.tvNoData.visibility = View.GONE
            binding.rvFavoriteRecipes.visibility = View.VISIBLE
        }

        val favoriteRecipeIds = favoriteRecipeIdsStringSet.mapNotNull { idString ->
            idString.toIntOrNull()
        }.toSet()
        val favoriteRecipes = STUB.getRecipesByIds(favoriteRecipeIds)
        val favoritesListAdapter = RecipesListAdapter(favoriteRecipes).apply {
            setOnItemClickListener(object : RecipesListAdapter.OnItemClickListener {
                override fun onItemClick(recipeId: Int) {
                    openRecipeByRecipeId(recipeId)
                }
            })
        }
        binding.rvFavoriteRecipes.apply {
            adapter = favoritesListAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    fun openRecipeByRecipeId(recipeId: Int) {
        val recipe = STUB.getRecipeById(recipeId) //burgerRecipes[recipeId]
        val bundle = bundleOf(
            Constants.ARG_RECIPE_ID to recipeId,
            Constants.ARG_RECIPE_NAME to recipeTitle,
            Constants.ARG_RECIPE_IMAGE_URL to recipeImageUrl
        )

        bundle.putParcelable(Constants.ARG_RECIPE, recipe)

        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            addToBackStack(null)
        }
    }

    private fun getFavoriteRecipeIds(): Set<String> {
        val sharedPrefs =
            activity?.getSharedPreferences(Constants.SHARED_PREFS_RECIPES, Context.MODE_PRIVATE)
        return sharedPrefs?.getStringSet(Constants.SHARED_PREFS_RECIPES_DATA, setOf()) ?: setOf()
    }
}