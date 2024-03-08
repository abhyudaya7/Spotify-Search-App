package com.example.spotifysearchapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.spotifysearchapp.data.models.SearchItem

object AppUtils {

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

        connectivityManager?.let { cm ->
            val networkCapabilities = cm.getNetworkCapabilities(cm.activeNetwork)
            networkCapabilities?.let { cap ->
                return cap.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            } ?: return false
        } ?: return false
    }

    fun checkIfTokenIsValid(timeStamp: Long): Boolean {
        return (System.currentTimeMillis() - timeStamp) <= 3600000
    }

    fun checkIfSearchItemValid(item: SearchItem?): Boolean {
        return if (item != null && item.items.isNotEmpty()) {
            var valid = false
            for (currItem in item.items) {
                if (currItem != null) {
                    valid = true
                    break
                }
            }
            valid
        } else {
            false
        }
    }

    fun <T> filterNonNullList(list: List<T?>) : List<T> {
        val newList = mutableListOf<T>()
        for (item in list) {
            if (item != null) {
                newList.add(item)
            }
        }
        return newList
    }

}