package com.example.spotifysearchapp.ui

sealed class UiLoadingState<out T> {

    data object LoadingStarted : UiLoadingState<Nothing>()

    data class LoadingSuccess<out T>(val result: T): UiLoadingState<T>()

    data object LoadingError: UiLoadingState<Nothing>()
}