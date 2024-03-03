package com.practicum.androidsprint.ui.categories

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.androidsprint.R
import com.practicum.androidsprint.databinding.ItemCategoryBinding
import com.practicum.androidsprint.model.Category
import java.io.InputStream

class CategoriesListAdapter(
    private val dataSet: List<Category>,
) : RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {
    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(categoryId: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    class ViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            val categoryTitle = binding.tvRecipeName
            val categoryDescription = binding.tvCategoryDescription
            val inputStream: InputStream = itemView.context.assets.open(dataSet[position].imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)

            categoryTitle.text = dataSet[position].title
            categoryDescription.text = dataSet[position].description

            try {
                val imageDescription = itemView.resources.getString(R.string.image_category_description) + " ${categoryTitle.text}"
                binding.tvCategoryDescription.contentDescription = imageDescription
                binding.ivRecipeImage.setImageDrawable(drawable)
            } catch (e: Exception) {
                Log.e("onBindViewHolder", "err access on assets", Throwable(Log.getStackTraceString(e)))
            }

            binding.cvCategoryItem.setOnClickListener {
                itemClickListener?.onItemClick(dataSet[position].id)
            }
        }
    }

    override fun getItemCount() = dataSet.size
}