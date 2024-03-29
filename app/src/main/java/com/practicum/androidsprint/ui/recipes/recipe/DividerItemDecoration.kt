package com.practicum.androidsprint.ui.recipes.recipe

import android.content.Context
import android.graphics.Canvas
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.practicum.androidsprint.R

class DividerItemDecoration(
    context: Context?,
    private val orientation: Int
) : DividerItemDecoration(context, orientation) {
    private var lastItemDecorated: Boolean = true
    private val spaceSize: Int


    init {
        spaceSize = context?.resources?.getDimensionPixelSize(R.dimen.main_padding) ?: 0
    }
    fun setLastItemDecorated(decorated: Boolean) {
        lastItemDecorated = decorated
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val divider = drawable
        if (divider == null || parent.layoutManager == null) {
            return
        }

        if (orientation == VERTICAL) {
            val childCount = if (lastItemDecorated) parent.childCount else parent.childCount - 1
            for (i in 0 until childCount) {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams
                val left = parent.paddingLeft + spaceSize
                val right = parent.width - parent.paddingRight - spaceSize
                val top = child.bottom + params.bottomMargin
                val bottom = top + divider.intrinsicHeight
                divider.setBounds(left, top, right, bottom)
                divider.draw(canvas)
            }
        }
    }
}