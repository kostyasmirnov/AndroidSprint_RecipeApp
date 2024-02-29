package com.practicum.androidsprint

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.androidsprint.databinding.ItemRecipeBinding
import java.io.InputStream
class RecipesListAdapter(
    private val dataSet: List<Recipe>,
) : RecyclerView.Adapter<RecipesListAdapter.ViewHolder>() {
    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(recipeId: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    class ViewHolder(val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            binding.tvRecipeName.text = dataSet[position].title
            try {
                val inputStream: InputStream = itemView.context.assets.open(dataSet[position].imageUrl)
                val drawable = Drawable.createFromStream(inputStream, null)
                binding.ivRecipeImage.setImageDrawable(drawable)
            } catch (e: Exception) {
                Log.e("err", "onBindViewHolder: ${e.printStackTrace()}")
            }
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(dataSet[position].id)
            }
        }
    }

    override fun getItemCount() = dataSet.size
}