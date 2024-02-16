package com.practicum.androidsprint

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.math.BigDecimal
import java.math.RoundingMode

class IngredientsAdapter(
    private val dataSet: List<Ingredient>,
) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    private var quantity = 1

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ingredientName: TextView = view.findViewById(R.id.tvRecipeIngredientName)
        val ingredientCount: TextView = view.findViewById(R.id.tvRecipeIngredientCount)
        val ingredientMeasure: TextView = view.findViewById(R.id.tvRecipeIngredientMeasure)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient, parent, false)
        return ViewHolder(itemView)
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