package com.practicum.androidsprint.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.practicum.androidsprint.R
import com.practicum.androidsprint.data.STUB
import com.practicum.androidsprint.databinding.FragmentListCategoriesBinding
import com.practicum.androidsprint.model.Category
import com.practicum.androidsprint.data.Constants
import com.practicum.androidsprint.ui.recipes.RecipesListFragment

class CategoriesListFragment : Fragment() {

    private val binding: FragmentListCategoriesBinding by lazy {
        FragmentListCategoriesBinding.inflate(layoutInflater)
    }

    private val categories: List<Category>
        get() = STUB.getCategories()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        val categoriesListAdapter = CategoriesListAdapter(STUB.getCategories())
        val recyclerView = binding.rvCategories
        recyclerView.adapter = categoriesListAdapter
        categoriesListAdapter.setOnItemClickListener(object :
            CategoriesListAdapter.OnItemClickListener {
            override fun onItemClick(categoryId: Int) {
                openRecipesByCategoryId(categoryId)
            }
        })
    }

    fun openRecipesByCategoryId(categoryId: Int) {
        val category = categories.find { it.id == categoryId }
        if (category != null) {
            val categoryName: String = category.title
            val categoryImageUrl: String = category.imageUrl
            val bundle = bundleOf(
                Constants.ARG_RECIPE_ID to categoryId,
                Constants.ARG_RECIPE_NAME to categoryName,
                Constants.ARG_RECIPE_IMAGE_URL to categoryImageUrl
            )
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                replace<RecipesListFragment>(R.id.mainContainer, args = bundle)
                addToBackStack(null)
            }
        }
    }
}