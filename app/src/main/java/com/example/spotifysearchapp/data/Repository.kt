package com.example.spotifysearchapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.spotifysearchapp.utils.AppConstants
import com.example.spotifysearchapp.utils.AppUtils
import com.example.spotifysearchapp.data.local.LocalDataSource
import com.example.spotifysearchapp.data.models.SearchDataResult
import com.example.spotifysearchapp.data.models.SpotifySearchData
import com.example.spotifysearchapp.data.network.NetworkDataSource
import com.example.spotifysearchapp.data.network.NetworkResponse
import kotlinx.coroutines.flow.first

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "appAuthPrefs")

class Repository(
    private val appContext: Context,
    private val localDataSource: LocalDataSource,
    private val networkDataSource: NetworkDataSource
) {

    private val authTokenKey = stringPreferencesKey(AppConstants.KEY_AUTH_TOKEN)
    private val timeStampKey = longPreferencesKey(AppConstants.KEY_TIME_STAMP)

    suspend fun getSearchResults(query: String): SpotifySearchData? {

        try {
            if (AppUtils.isNetworkAvailable(appContext)) {
                // fetch from remote dataSource and save locally
                return when (val response = networkDataSource.getSearchData(query, getAuthToken())) {
                    is NetworkResponse.Success -> {
                        localDataSource.storeSearchData(SearchDataResult(query, response.response))
                        response.response
                    }

                    is NetworkResponse.Error -> {
                        val localData = fetchDataFromLocalDB(query)
                        if (localData.isNotEmpty()) {
                            localData.first().data
                        } else {
                            null
                        }
                    }
                }
            } else {
                // fetch from local data source
                val localData = fetchDataFromLocalDB(query)
                return if (localData.isNotEmpty()) {
                    localData.first().data
                } else {
                    null
                }
            }
        } catch (e: Exception) {
           return null
        }
    }

    private suspend fun fetchDataFromLocalDB(query: String): List<SearchDataResult> {
        return localDataSource.getStoredSearchData(query)
    }

    private suspend fun getAuthToken(): String {
        val preferences = appContext.dataStore.data.first()
        val localKey = preferences[authTokenKey] ?: ""
        val localTimeStamp = preferences[timeStampKey] ?: 0L

        return if (AppUtils.checkIfTokenIsValid(localTimeStamp) && localKey.isNotEmpty()) {
            localKey
        } else {
            refreshAuthToken() ?: ""
        }
    }

    private suspend fun refreshAuthToken(): String? {
        return when (val authResponse = networkDataSource.getAuthToken()) {
            is NetworkResponse.Success -> {
                appContext.dataStore.edit { appAuthPrefs ->
                    appAuthPrefs[authTokenKey] = "Bearer ${authResponse.response.accessToken}"
                    appAuthPrefs[timeStampKey] = System.currentTimeMillis()
                }
                "Bearer ${authResponse.response.accessToken}"
            }

            is NetworkResponse.Error -> {
                null
            }
        }
    }

}