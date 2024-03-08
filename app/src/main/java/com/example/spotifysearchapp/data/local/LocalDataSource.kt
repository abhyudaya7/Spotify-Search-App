package com.example.spotifysearchapp.data.local

import com.example.spotifysearchapp.data.models.SearchDataResult

class LocalDataSource(
    private val dao: SearchDataDao
) {

    suspend fun getStoredSearchData(query: String): List<SearchDataResult> {
        return dao.getStoredSearchDataByQuery(query)
    }

    suspend fun storeSearchData(data: SearchDataResult) {
        dao.upsertSearchData(data)
    }

}