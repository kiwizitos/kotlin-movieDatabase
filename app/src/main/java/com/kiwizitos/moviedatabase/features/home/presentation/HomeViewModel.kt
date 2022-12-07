package com.kiwizitos.moviedatabase.features.home.presentation

import androidx.lifecycle.*
import com.kiwizitos.moviedatabase.core.network.MazeApi
import com.kiwizitos.moviedatabase.features.detail.domain.entities.ShowEntity
import kotlinx.coroutines.flow.flow

class HomeViewModel: ViewModel() {
    var showList: List<ShowEntity> = arrayListOf()

    fun getResponse() = flow {
        val response = MazeApi.retrofitService.getShows()
        val list = response.body()?.map {
            ShowEntity(
                id = it.id,
                name = it.name,
                summary = it.summary,
                image = it.image.medium,
                genres = it.genres,
                rating = it.rating.average
            )
        }?: emptyList()
        showList = list
        emit(list)
    }.asLiveData()
}