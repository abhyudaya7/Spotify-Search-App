package com.example.spotifysearchapp.data.network

import com.example.spotifysearchapp.BuildConfig
import com.example.spotifysearchapp.data.models.SpotifySearchData
import com.example.spotifysearchapp.data.models.TokenData

class NetworkDataSource(
    private val spotifyApiInterface: SpotifyApiInterface,
    private val spotifyAuthApiInterface: SpotifyAuthApiInterface
) {

    suspend fun getAuthToken(): NetworkResponse<TokenData> {
        val response = spotifyAuthApiInterface.getSpotifyAuthToken(
            "client_credentials",
            BuildConfig.CLIENT_ID,
            BuildConfig.CLIENT_SECRET
        )

        return if (response.isSuccessful) {
            response.body()?.let {
                NetworkResponse.Success(it)
            } ?: NetworkResponse.Error()
        } else {
            NetworkResponse.Error(response.code())
        }
    }

    suspend fun getSearchData(
        searchQuery: String,
        authorization: String
    ): NetworkResponse<SpotifySearchData> {

        val response = spotifyApiInterface.getSearchData(
            searchQuery,
            "artist,album,playlist",
            authorization
        )

        return if (response.isSuccessful) {
            response.body()?.let {
                NetworkResponse.Success(it)
            } ?: NetworkResponse.Error()
        } else {
            NetworkResponse.Error(response.code())
        }
    }

}