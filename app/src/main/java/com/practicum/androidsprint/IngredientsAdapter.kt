package com.practicum.androidsprint

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.practicum.androidsprint.databinding.ItemIngredientBinding
import java.math.BigDecimal
import java.math.RoundingMode

class IngredientsAdapter(
    private val dataSet: List<Ingredient>,
) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    private var quantity = 1

    class ViewHolder(val binding: ItemIngredientBinding) : RecyclerView.ViewHolder(binding.root) {
        val ingredientName: TextView = binding.tvRecipeIngredientName
        val ingredientCount: TextView = binding.tvRecipeIngredientCount
        val ingredientMeasure: TextView = binding.tvRecipeIngredientMeasure
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            val ingredientCurrentCount = dataSet[position].quantity
            val totalQuantity = BigDecimal(ingredientCurrentCount) * BigDecimal(quantity)
            val displayQuantity =
                totalQuantity.setScale(1, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString()

            ingredientName.text = dataSet[position].description
            ingredientCount.text = "$displayQuantity "
            ingredientMeasure.text = dataSet[position].unitOfMeasure
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
