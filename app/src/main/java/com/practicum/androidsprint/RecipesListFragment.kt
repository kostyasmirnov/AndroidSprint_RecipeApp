package com.practicum.androidsprint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.practicum.androidsprint.databinding.FragmentRecipesListBinding

class RecipesListFragment: Fragment(R.layout.fragment_recipes_list) {

    private lateinit var binding: FragmentRecipesListBinding

    private var recipeId: String? = null
    private var recipeName: String? = null
    private var recipeImageUrl: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRecipesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = binding.tvCategoriesList
        recipeId = arguments?.let { Constants.ARG_CATEGORY_ID }
        recipeName = arguments?.let { Constants.ARG_CATEGORY_NAME }
        recipeImageUrl = arguments?.let { Constants.ARG_CATEGORY_IMAGE_URL }
        textView.text
    }
}