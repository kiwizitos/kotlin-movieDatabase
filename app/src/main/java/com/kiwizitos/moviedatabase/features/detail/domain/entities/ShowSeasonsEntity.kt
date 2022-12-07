package com.kiwizitos.moviedatabase.features.detail.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShowSeasonsEntity(
    val id: Int,
    val number: Int
) : Parcelable