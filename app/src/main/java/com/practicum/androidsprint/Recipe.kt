package com.practicum.androidsprint

data class Recipe(
    val id: Int,
    val title: String,
    val ingredient: List<Ingredient>,
    val method: List<String>,
    val imageUrl: String,
)