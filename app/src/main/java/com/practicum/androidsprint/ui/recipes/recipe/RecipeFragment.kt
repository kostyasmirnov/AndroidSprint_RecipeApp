package com.practicum.androidsprint.ui.recipes.recipe

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practicum.androidsprint.R
import com.practicum.androidsprint.model.Recipe
import com.practicum.androidsprint.data.Constants.Companion.ARG_RECIPE
import com.practicum.androidsprint.databinding.FragmentRecipeBinding
import com.practicum.androidsprint.data.Constants
import java.io.InputStream

class RecipeFragment : Fragment(R.layout.fragment_recipe) {
    private val binding by lazy {
        FragmentRecipeBinding.inflate(layoutInflater)
    }
    private lateinit var seekBar: SeekBar
    private var ingredientsAdapter: IngredientsAdapter? = null
    private var sizeInDp: Int = 0
    private val recipe: RecipeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipeParcelable = getRecipeFromArguments()
        setupUI(recipeParcelable)
        initRecycler(recipeParcelable)
        sizeInDp = resources.getDimensionPixelSize(R.dimen.main_padding)

        recipe.recipeState.observe(viewLifecycleOwner) {
            Log.i("!!!", recipe.recipeState.toString())
        }
    }

    private fun getRecipeFromArguments(): Recipe? {
        return if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_RECIPE, Recipe::class.java)
        } else {
            arguments?.getParcelable(ARG_RECIPE)
        }
    }

    private fun setupUI(recipe: Recipe?) {
        recipe?.let {
            binding.tvRecipeHeaderText.text = it.title
            val inputStream: InputStream =
                it.imageUrl.let { imgUrl -> binding.root.context.assets.open(imgUrl) }
            val drawable = Drawable.createFromStream(inputStream, null)
            binding.ivRecipeHeaderImg.setImageDrawable(drawable)

            val favoritesSet = getFavorites()
            var isFavorite = favoritesSet.contains(recipe.id.toString())

            val favoriteIconRes =
                if (isFavorite) R.drawable.ic_favorites else R.drawable.ic_favorites_empty

            val favoritesButton: ImageButton = binding.ibFavorites
            favoritesButton.setBackgroundResource(favoriteIconRes)
            favoritesButton.setOnClickListener {
                isFavorite = if (isFavorite) {
                    saveFavorites(getFavorites().apply { remove(recipe.id.toString()) })
                    favoritesButton.setBackgroundResource(R.drawable.ic_favorites_empty)
                    false
                } else {
                    saveFavorites(getFavorites().apply { add(recipe.id.toString()) })
                    favoritesButton.setBackgroundResource(R.drawable.ic_favorites)
                    true
                }
            }
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
        seekBar = binding.sbPortionsCount
        seekBar.setPadding(sizeInDp, 0, sizeInDp, 0)
        seekBar.setOnSeekBarChangeListener(seekBarListener)
        binding.rvIngredients.adapter = ingredientsAdapter

        binding.rvIngredients.apply {
            adapter = ingredientsAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(createCustomDivider())
        }

        recipe?.method?.let { method ->
            binding.rvMethod.apply {
                adapter = MethodAdapter(method)
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(createCustomDivider())
            }
        }
    }

    private fun createCustomDivider(): DividerItemDecoration {
        val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
        dividerItemDecoration.setLastItemDecorated(false)
        return dividerItemDecoration
    }

    private fun saveFavorites(recipesIds: Set<String>) {
        val sharedPrefs =
            requireContext().getSharedPreferences(
                Constants.SHARED_PREFS_RECIPES,
                Context.MODE_PRIVATE
            )
        with(sharedPrefs.edit()) {
            putStringSet(Constants.SHARED_PREFS_RECIPES_DATA, recipesIds)
            apply()
        }
    }

    private fun getFavorites(): MutableSet<String> {
        val sharedPrefs =
            activity?.getSharedPreferences(Constants.SHARED_PREFS_RECIPES, Context.MODE_PRIVATE)
        val favoritesRecipe = sharedPrefs?.getStringSet(Constants.SHARED_PREFS_RECIPES_DATA, null)
        return favoritesRecipe?.let { HashSet(it) } ?: hashSetOf()
    }
}