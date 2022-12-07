package com.kiwizitos.moviedatabase.core.network

import com.kiwizitos.moviedatabase.features.detail.data.models.ShowEpisodesResponse
import com.kiwizitos.moviedatabase.features.detail.data.models.ShowSeasonsResponse
import com.kiwizitos.moviedatabase.features.home.data.models.ShowResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl("https://api.tvmaze.com/")
    .build()

interface MazeApiService {
    @GET("shows?page=0")
    suspend fun getShows(): Response<List<ShowResponse>>

    @GET("shows/{id}/episodes")
    suspend fun getEpisodes(@Path("id") id: Int): Response<List<ShowEpisodesResponse>>

    @GET("shows/{id}/seasons")
    suspend fun getSeasons(@Path("id") id: Int): Response<List<ShowSeasonsResponse>>

    @GET("seasons/{id}/episodes")
    suspend fun getSeasonEpisodes(@Path("id") id: Int): Response<List<ShowEpisodesResponse>>
}

object MazeApi {
    val retrofitService: MazeApiService by lazy { retrofit.create(MazeApiService::class.java)}
}