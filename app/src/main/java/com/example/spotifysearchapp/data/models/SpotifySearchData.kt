package com.example.spotifysearchapp.data.models

import com.google.gson.annotations.SerializedName

data class SpotifySearchData(
    @SerializedName("albums") var albums: SearchItem? = SearchItem(),
    @SerializedName("artists") var artists: SearchItem? = SearchItem(),
    @SerializedName("playlists") var playlists: SearchItem? = SearchItem()
)

data class SearchItem(
    @SerializedName("items") var items: ArrayList<Items?> = arrayListOf(),
    )


data class Images(
    @SerializedName("height") var height: Int? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("width") var width: Int? = null
)

data class Items(
    @SerializedName("description") var description: String? = null,
    @SerializedName("id") var id: String? = null,
    @SerializedName("images") var images: ArrayList<Images> = arrayListOf(),
    @SerializedName("name") var name: String? = null,
    @SerializedName("type") var type: String? = null
)
