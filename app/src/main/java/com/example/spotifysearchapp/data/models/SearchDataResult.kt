package com.example.spotifysearchapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchDataResult(
    @PrimaryKey val query: String,
    val data: SpotifySearchData
)