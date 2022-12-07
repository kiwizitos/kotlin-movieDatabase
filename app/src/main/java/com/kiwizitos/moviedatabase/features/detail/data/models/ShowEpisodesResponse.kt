package com.kiwizitos.moviedatabase.features.detail.data.models

data class ShowEpisodesResponse(
    val id: Int,
    val name: String,
    val season: Int,
    val number: Int?,
    val image: Image?
) {
    data class Image(
        val medium: String,
        val original: String
    )
}