package com.example.spotifysearchapp.di

import android.content.Context
import androidx.room.Room
import com.example.spotifysearchapp.data.Repository
import com.example.spotifysearchapp.data.local.Database
import com.example.spotifysearchapp.data.local.LocalDataSource
import com.example.spotifysearchapp.data.local.SearchDataDao
import com.example.spotifysearchapp.data.network.NetworkDataSource
import com.example.spotifysearchapp.data.network.SpotifyApiInterface
import com.example.spotifysearchapp.data.network.SpotifyAuthApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSpotifyApiInterface(): SpotifyApiInterface {
        return Retrofit.Builder()
            .baseUrl("https://api.spotify.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpotifyApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideSpotifyAuthApiInterface(): SpotifyAuthApiInterface {
        return Retrofit.Builder()
            .baseUrl("https://accounts.spotify.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpotifyAuthApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): Database {
        return Room.databaseBuilder(
            appContext,
            Database::class.java,
            "Database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideSearchDao(database: Database): SearchDataDao {
        return database.searchDataDao
    }

    @Provides
    @Singleton
    fun provideNetworkDataSource(
        spotifyAuthApiInterface: SpotifyAuthApiInterface,
        spotifyApiInterface: SpotifyApiInterface
    ): NetworkDataSource {
        return NetworkDataSource(spotifyApiInterface, spotifyAuthApiInterface)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(doa: SearchDataDao): LocalDataSource {
        return LocalDataSource(doa)
    }

    @Provides
    @Singleton
    fun provideRepository(
        @ApplicationContext appContext: Context,
        networkDataSource: NetworkDataSource,
        localDataSource: LocalDataSource
    ): Repository {
        return Repository(appContext, localDataSource, networkDataSource)
    }

}