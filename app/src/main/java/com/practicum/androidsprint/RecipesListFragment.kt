package com.practicum.androidsprint

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.practicum.androidsprint.databinding.FragmentRecipesListBinding
import java.io.InputStream

class RecipesListFragment: Fragment(R.layout.fragment_recipes_list) {

    private val binding by lazy {
        FragmentRecipesListBinding.inflate(layoutInflater)
    }

    private var categoryId: String? = null
    private var categoryName: String? = null
    private var categoryImageUrl: String? = null

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
        categoryId = arguments?.getString(Constants.ARG_CATEGORY_ID)
        categoryName = arguments?.getString(Constants.ARG_CATEGORY_NAME)
        categoryImageUrl = arguments?.getString(Constants.ARG_CATEGORY_IMAGE_URL)

        val fragment = context
        val inputStream: InputStream? = categoryImageUrl?.let { fragment?.assets?.open(it) }
        val drawable = Drawable.createFromStream(inputStream, null)
        binding.ivRecipesListHeaderImg.setImageDrawable(drawable)
        binding.tvRecipesListHeaderText.text = categoryName

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
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipeFragment>(R.id.mainContainer)
            addToBackStack(null)
        }

    }
}