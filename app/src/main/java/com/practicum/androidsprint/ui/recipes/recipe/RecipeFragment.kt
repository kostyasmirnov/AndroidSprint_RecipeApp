package com.practicum.androidsprint.ui.recipes.recipe

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practicum.androidsprint.R
import com.practicum.androidsprint.model.Recipe
import com.practicum.androidsprint.databinding.FragmentRecipeBinding
import com.practicum.androidsprint.data.Constants.Companion.ARG_RECIPE_ID
import java.io.InputStream

class RecipeFragment : Fragment(R.layout.fragment_recipe) {
    private val binding by lazy {
        FragmentRecipeBinding.inflate(layoutInflater)
    }
    private var ingredientsAdapter: IngredientsAdapter? = null
    private var sizeInDp: Int = 0
    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt(ARG_RECIPE_ID)?.let { viewModel.loadRecipe(it) }
        setupUI()
    }


    private fun setupUI() {
        val dividerItemDecoration = createCustomDivider()
        binding.rvIngredients.addItemDecoration(dividerItemDecoration)
        binding.rvMethod.addItemDecoration(dividerItemDecoration)
        viewModel.recipeState.observe(viewLifecycleOwner) { state: RecipeUiState? ->
            val recipe: Recipe? = state?.recipe

            binding.tvRecipeHeaderText.text = recipe?.title
            val inputStream: InputStream? = recipe?.imageUrl.let {
                recipe?.imageUrl?.let {
                    context?.assets?.open(it)
                }
            }
            val drawable = Drawable.createFromStream(inputStream, null)
            binding.ivRecipeHeaderImg.setImageDrawable(drawable)

            val isFavorite = state?.isFavorite

            val favoritesButton: ImageButton = binding.ibFavorites
            val favoritesIconRes = if (isFavorite == true) R.drawable.ic_favorites else R.drawable.ic_favorites_empty
            favoritesButton.setBackgroundResource(favoritesIconRes)

            favoritesButton.setOnClickListener {
                viewModel.onFavoritesClicked()
            }
            initRecycler(recipe)
        }
    }

    private fun initRecycler(recipe: Recipe?) {
        ingredientsAdapter = recipe?.ingredients?.let { IngredientsAdapter(it) }


        val seekBarListener = IngredientsCountChooseSeekbar(
            onProgressChanged = { progress ->
                binding.tvPortionsCount.text = progress.toString()
                ingredientsAdapter?.updateIngredients(progress)
            },
        )
        binding.sbPortionsCount.setPadding(sizeInDp, 0, sizeInDp, 0)
        binding.sbPortionsCount.setOnSeekBarChangeListener(seekBarListener)
        binding.rvIngredients.adapter = ingredientsAdapter

        binding.rvIngredients.apply {
            adapter = ingredientsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        recipe?.method?.let { method ->
            binding.rvMethod.apply {
                adapter = MethodAdapter(method)
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    private fun createCustomDivider(): DividerItemDecoration {
        val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
        dividerItemDecoration.setLastItemDecorated(false)
        return dividerItemDecoration
    }

}
