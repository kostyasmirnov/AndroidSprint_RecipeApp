package com.practicum.androidsprint

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.practicum.androidsprint.databinding.ItemMethodBinding

class MethodAdapter(
    private val dataSet: List<String>,
) : RecyclerView.Adapter<MethodAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemMethodBinding) : RecyclerView.ViewHolder(binding.root) {
        val methodNumber: TextView = binding.tvRecipeMethodDescription
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMethodBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val index = position + 1

        viewHolder.methodNumber.setTextColor(
            ContextCompat.getColor(
                viewHolder.itemView.context,
                R.color.description_categories_color
            )
        )
        viewHolder.methodNumber.text = "$index. ${dataSet[position]}"
    }

    override fun getItemCount() = dataSet.size
}