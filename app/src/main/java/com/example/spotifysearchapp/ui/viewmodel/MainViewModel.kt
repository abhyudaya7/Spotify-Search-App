package com.example.spotifysearchapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifysearchapp.ui.UiLoadingState
import com.example.spotifysearchapp.data.Repository
import com.example.spotifysearchapp.data.models.Items
import com.example.spotifysearchapp.data.models.SpotifySearchData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _searchState =
        MutableStateFlow<UiLoadingState<SpotifySearchData>>(UiLoadingState.LoadingStarted)

    var selectedItem: Items? = null

    fun getSearchResults(query: String): StateFlow<UiLoadingState<SpotifySearchData>> {
        _searchState.value = UiLoadingState.LoadingStarted
        viewModelScope.launch {
            val response = repository.getSearchResults(query)
            if (response != null) {
                _searchState.value = UiLoadingState.LoadingSuccess(response)
            } else {
                _searchState.value = UiLoadingState.LoadingError
            }
        }
        return _searchState
    }
}