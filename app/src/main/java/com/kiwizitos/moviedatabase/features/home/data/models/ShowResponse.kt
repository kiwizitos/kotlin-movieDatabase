package com.kiwizitos.moviedatabase.features.home.data.models

data class ShowResponse(
    val id: Int,
    val name: String,
    val summary: String,
    val image: Image,
    val genres: List<String>,
    val rating: Rating,
) {
    data class Image(
        val medium: String,
        val original: String
    )
    data class Rating(
        val average: Double?
    )
}