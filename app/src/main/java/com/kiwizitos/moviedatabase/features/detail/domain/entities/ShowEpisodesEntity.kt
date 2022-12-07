package com.kiwizitos.moviedatabase.features.detail.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShowEpisodesEntity(
    val id: Int,
    val name: String,
    val season: Int,
    val number: Int,
    val image: String,
) : Parcelable {
    @Parcelize
    data class Image(
        val medium: String,
        val original: String
    ) : Parcelable
}