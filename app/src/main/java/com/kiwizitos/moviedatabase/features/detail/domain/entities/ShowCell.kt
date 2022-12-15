package com.kiwizitos.moviedatabase.features.detail.domain.entities

import android.text.Html
import coil.load
import com.kiwizitos.moviedatabase.databinding.ShowCellBinding
import io.github.enicolas.genericadapter.adapter.BaseCell

class ShowCell(private var viewbinding: ShowCellBinding) :
    BaseCell(viewbinding.root) {
    fun set(
        id: Int,
        name: String,
        summary: String?,
        image: String,
        genres: List<String>,
        rating: String,
    ) {
        viewbinding.txtShowName.text = name
        viewbinding.txtShowId.text = id.toString()
        viewbinding.txtShowSummary.text = (Html.fromHtml(summary))
        viewbinding.imgShowBanner.load(image)
        viewbinding.txtShowRating.text = rating
    }
}