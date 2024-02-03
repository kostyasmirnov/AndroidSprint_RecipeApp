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
import com.practicum.androidsprint.Constants.Companion.ARG_RECIPE
import com.practicum.androidsprint.Constants.Companion.ARG_RECIPE_ID
import com.practicum.androidsprint.Constants.Companion.ARG_RECIPE_IMAGE_URL
import com.practicum.androidsprint.Constants.Companion.ARG_RECIPE_NAME

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
        initArgs()
        initUI()

        val fragment = context
        val inputStream: InputStream? = recipeImageUrl?.let { fragment?.assets?.open(it) }
        val drawable = Drawable.createFromStream(inputStream, null)
        binding.ivRecipesListHeaderImg.setImageDrawable(drawable)
        binding.tvRecipesListHeaderText.text = recipeName
    }

    private fun initArgs() {
        arguments?.let { args ->
            recipeId = args.getString(ARG_RECIPE_ID)
            recipeName = args.getString(ARG_RECIPE_NAME)
            recipeImageUrl = args.getString(ARG_RECIPE_IMAGE_URL)
        }
    }

    private fun initUI() {
        val inputStream: InputStream? = recipeImageUrl?.let { this.context?.assets?.open(it) }
        val drawable = Drawable.createFromStream(inputStream, null)
        binding.ivRecipesListHeaderImg.setImageDrawable(drawable)
        binding.tvRecipesListHeaderText.text = recipeName
    }

    private fun initRecycler() {
        val recipesListAdapter = RecipesListAdapter(STUB.getBurgerRecipes(), fragment = this)
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
        val recipe = STUB.getRecipeById(recipeId)
        val bundle = bundleOf(
            ARG_RECIPE_ID to recipeId,
            ARG_RECIPE_NAME to recipeName,
            ARG_RECIPE_IMAGE_URL to recipeImageUrl
        )

        bundle.putParcelable(ARG_RECIPE, recipe)

        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            addToBackStack(null)
        }
    }
}