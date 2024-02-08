package com.practicum.androidsprint

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practicum.androidsprint.Constants.Companion.ARG_RECIPE
import com.practicum.androidsprint.databinding.FragmentRecipeBinding
import java.io.InputStream

class RecipeFragment : Fragment(R.layout.fragment_recipe) {
    private val binding by lazy {
        FragmentRecipeBinding.inflate(layoutInflater)
    }
    private lateinit var seekBar: SeekBar
    private var ingredientsAdapter: IngredientsAdapter? = null

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
            val inputStream: InputStream? =
                it.imageUrl.let { imgUrl -> context?.assets?.open(imgUrl) }
            val drawable = Drawable.createFromStream(inputStream, null)
            binding.ivRecipeHeaderImg.setImageDrawable(drawable)
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
        seekBar.setPadding(16, 0, 16, 0)
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
        val dividerItemDecoration = context?.let {
            DividerItemDecoration(it, RecyclerView.VERTICAL)
        }
        dividerItemDecoration?.setLastItemDecorated(false)
        return dividerItemDecoration!!
    }

}