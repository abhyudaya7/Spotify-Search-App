package com.example.spotifysearchapp.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.spotifysearchapp.data.models.SearchDataResult

@Dao
interface SearchDataDao {

    @Upsert
    suspend fun upsertSearchData(data: SearchDataResult)

    @Query("SELECT * FROM searchdataresult WHERE `query` = :query")
    suspend fun getStoredSearchDataByQuery(query: String): List<SearchDataResult>

}