package com.practicum.androidsprint

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.io.InputStream

class CategoriesListAdapter(
    private val dataSet: List<Category>,
    private val context: CategoriesListFragment,
) : RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvCategoryItem: CardView = itemView.findViewById(R.id.cvCategoryItem)
        val tvCategoryName: TextView = itemView.findViewById(R.id.tvCategoryName)
        val tvCategoryDescription: TextView = itemView.findViewById(R.id.tvCategoryDescription)
        val ivCategoryImage: ImageView = itemView.findViewById(R.id.ivCategoryImage)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_category, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val categoryTitle = viewHolder.tvCategoryName
        val categoryDescription = viewHolder.tvCategoryDescription
        val fragment = context
        val inputStream: InputStream? = fragment.context?.assets?.open(dataSet[position].imageUrl)
        val drawable = Drawable.createFromStream(inputStream, null)

        categoryTitle.text = dataSet[position].title
        categoryDescription.text = dataSet[position].description

        try {

            viewHolder.ivCategoryImage.setImageDrawable(drawable)
            viewHolder.tvCategoryName.text = categoryTitle.text
            viewHolder.tvCategoryDescription.text = categoryDescription.text

        } catch (e: Exception) {
            Log.e(
                "onBindViewHolder", "err access on assets",
                Throwable(Log.getStackTraceString(e))
            )
        }
    }

    override fun getItemCount() = dataSet.size


}