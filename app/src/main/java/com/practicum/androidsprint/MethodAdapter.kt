package com.practicum.androidsprint

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class MethodAdapter(
    private val dataSet: List<String>,
) : RecyclerView.Adapter<MethodAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val methodNumber: TextView = view.findViewById(R.id.tvRecipeMethodDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient, parent, true)
        return ViewHolder(itemView)
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