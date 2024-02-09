package com.practicum.androidsprint

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class IngredientsAdapter(
    private val dataSet: List<Ingredient>,
) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    private var quantity = 1

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ingredientName: TextView = view.findViewById(R.id.tvRecipeIngredientName)
        val ingredientCount: TextView = view.findViewById(R.id.tvRecipeIngredientCount)
        val ingredientMeasure: TextView = view.findViewById(R.id.tvRecipeIngredientMeasure)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_ingredient, viewGroup, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            val ingredientCurrentCount = dataSet[position].quantity
            val totalQuantity = ingredientCurrentCount.toFloat() * quantity
            val displayQuantity = if ((totalQuantity % 1) == 0f) {
                totalQuantity.toInt().toString()
            } else {
                String.format("%.1f", totalQuantity)
            }

            try {
                ingredientName.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.description_categories_color
                    )
                )
                ingredientCount.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.description_categories_color
                    )
                )
                ingredientMeasure.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.description_categories_color
                    )
                )
                ingredientName.text = dataSet[position].description
                ingredientCount.text = "$displayQuantity "
                ingredientMeasure.text = dataSet[position].unitOfMeasure
            } catch (e: Exception) {
                Log.e(
                    "err", "onBindViewHolder : asset error ${e.printStackTrace()}"
                )
            }
        }
    }


    override fun getItemCount() = dataSet.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateIngredients(progress: Int) {
        Log.d("IngredientsAdapter", "Updating ingredients with progress: $progress")
        quantity = progress
        notifyDataSetChanged()
    }

}