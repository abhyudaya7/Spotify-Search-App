package com.example.spotifysearchapp.data.network

import com.example.spotifysearchapp.data.models.TokenData
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SpotifyAuthApiInterface {

    @FormUrlEncoded
    @POST("api/token/")
    suspend fun getSpotifyAuthToken(
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
    ): Response<TokenData>

}