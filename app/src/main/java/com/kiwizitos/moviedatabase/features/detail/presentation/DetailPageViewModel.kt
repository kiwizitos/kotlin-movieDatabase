package com.kiwizitos.moviedatabase.features.detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kiwizitos.moviedatabase.core.network.MazeApi
import com.kiwizitos.moviedatabase.features.detail.domain.entities.ShowEpisodesEntity
import com.kiwizitos.moviedatabase.features.detail.domain.entities.ShowSeasonsEntity
import kotlinx.coroutines.flow.flow

class DetailPageViewModel : ViewModel() {
    var episodesList: List<ShowEpisodesEntity> = arrayListOf()
    var seasonsList: List<ShowSeasonsEntity> = arrayListOf()

    fun getEpisodesResponse(id: Int) = flow {
        val episodesResponse = MazeApi.retrofitService.getSeasonEpisodes(id)
        val list = episodesResponse.body()?.map {
            ShowEpisodesEntity(
                id = it.id,
                name = it.name,
                season = it.season,
                number = it.number ?: -1,
                image = it.image?.medium ?: "",
                summary = it.summary
            )
        } ?: emptyList()
        episodesList = list
        emit(list)
    }.asLiveData()

    fun getSeasonsResponse(id: Int) = flow {
        val seasonsResponse = MazeApi.retrofitService.getSeasons(id)
        val list = seasonsResponse.body()?.map {
            ShowSeasonsEntity(
                id = it.id,
                number = it.number
            )
        } ?: emptyList()
        seasonsList = list
        emit(list)
    }.asLiveData()
}