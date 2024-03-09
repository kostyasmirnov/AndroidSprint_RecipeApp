package com.practicum.androidsprint.model

import android.os.Parcelable
import com.practicum.androidsprint.model.Ingredient
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    var id: Int,
    val title: String,
    val ingredients: List<Ingredient>,
    val method: List<String>,
    val imageUrl: String,
) : Parcelable