package com.kiwizitos.moviedatabase.features.detail.domain.entities

import coil.load
import com.kiwizitos.moviedatabase.databinding.ShowEpisodeCellBinding
import io.github.enicolas.genericadapter.adapter.BaseCell

class ShowEpisodeCell(private val viewBinding: ShowEpisodeCellBinding) :
BaseCell(viewBinding.root) {
    fun set(
        id: Int,
        name: String,
        season: Int,
        number: Int,
        image: String
    ) {
        viewBinding.episodeName.text = name
        viewBinding.episodeNumber.text = "Episódio $number"
        viewBinding.episodeImage.load(image)
    }
}