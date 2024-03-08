package com.example.spotifysearchapp.data.models

import com.google.gson.annotations.SerializedName

data class TokenData(
    @SerializedName("access_token" ) var accessToken : String? = null,
    @SerializedName("token_type"   ) var tokenType   : String? = null,
    @SerializedName("expires_in"   ) var expiresIn   : Int?    = null
)
