package com.practicum.androidsprint

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.practicum.androidsprint.databinding.FragmentRecipesListBinding
import java.io.InputStream

class RecipesListFragment : Fragment(R.layout.fragment_recipes_list) {

    private val binding by lazy {
        FragmentRecipesListBinding.inflate(layoutInflater)
    }

    private var recipeId: String? = null
    private var recipeName: String? = null
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
        initRecycler()
        recipeId = arguments?.getString(Constants.ARG_RECIPE_ID)
        recipeName = arguments?.getString(Constants.ARG_RECIPE_NAME)
        recipeImageUrl = arguments?.getString(Constants.ARG_RECIPE_IMAGE_URL)

        val fragment = context
        val inputStream: InputStream? = recipeImageUrl?.let { fragment?.assets?.open(it) }
        val drawable = Drawable.createFromStream(inputStream, null)
        binding.ivRecipesListHeaderImg.setImageDrawable(drawable)
        binding.tvRecipesListHeaderText.text = recipeName

    }

    private fun initRecycler() {
        val recipesListAdapter = RecipesListAdapter(STUB.burgerRecipes, fragment = this)
        val recyclerView = binding.rvRecipes
        recyclerView.adapter = recipesListAdapter
        recipesListAdapter.setOnItemClickListener(object : RecipesListAdapter.OnItemClickListener {
            override fun onItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)
            }
        }
        )
    }

    fun openRecipeByRecipeId(recipeId: Int) {
        val recipe = STUB.burgerRecipes[recipeId]
        val bundle = bundleOf(
            Constants.ARG_RECIPE_ID to recipeId,
            Constants.ARG_RECIPE_NAME to recipeName,
            Constants.ARG_RECIPE_IMAGE_URL to recipeImageUrl
        )

        bundle.putParcelable(Constants.ARG_RECIPE, recipe)

        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            addToBackStack(null)
        }

    }
}