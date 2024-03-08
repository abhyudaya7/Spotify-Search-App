package com.example.spotifysearchapp.data.local

import androidx.room.TypeConverter
import com.example.spotifysearchapp.data.models.SpotifySearchData
import com.google.gson.Gson

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun dataToString(data: SpotifySearchData): String = gson.toJson(data)

    @TypeConverter
    fun toSpotifySearchData(json: String): SpotifySearchData = gson.fromJson(json, SpotifySearchData::class.java)

}