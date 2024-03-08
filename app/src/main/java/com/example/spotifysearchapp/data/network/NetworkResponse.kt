package com.example.spotifysearchapp.data.network

sealed class NetworkResponse<out T> {

    data class Success<out T>(val response: T): NetworkResponse<T>()

    data class Error(val errorCode: Int? = null) : NetworkResponse<Nothing>()

}