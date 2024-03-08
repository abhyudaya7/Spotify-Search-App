package com.example.spotifysearchapp.data.network

import com.example.spotifysearchapp.data.models.SpotifySearchData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SpotifyApiInterface {

    @GET("/v1/search/")
    suspend fun getSearchData(
        @Query("q") searchQuery: String,
        @Query("type") type: String,
        @Header("Authorization") authorization: String
    ): Response<SpotifySearchData>

}