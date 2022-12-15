package com.kiwizitos.moviedatabase.features.detail.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShowEntity(
    val id: Int,
    val name: String,
    val summary: String?,
    val image: String,
    val genres: List<String>,
    val rating: Double?,
) : Parcelable