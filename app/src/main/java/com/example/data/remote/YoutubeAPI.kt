package com.example.data.remote

import com.example.model.PlayList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeAPI {

    @GET("playlists")
    suspend fun fetchAllPlaylists(
        @Query("part") part: String,
        @Query("channelId") channelId: String,
        @Query("maxResults") maxResults: Int,
        @Query("key") apiKey: String,
    ): Response<PlayList>

    @GET("playlists")
    suspend fun fetchNextPlaylists(
        @Query("part") part: String,
        @Query("channelId") channelId: String,
        @Query("pageToken") pageToken: String,
        @Query("key") apiKey: String,
    ): Response<PlayList>

    @GET("playlistItems")
    suspend fun fetchSpecPlaylist(
        @Query("part") part: String,
        @Query("maxResults") maxResults: Int,
        @Query("playlistId") playlistId: String,
        @Query("key") key: String
    ): Response<PlayList>

    @GET("playlistItems")
    suspend fun fetchNextSpecPlaylist(
        @Query("part") part: String,
        @Query("pageToken") pageToken: String,
        @Query("maxResults") maxResults: Int,
        @Query("playlistId") playlistId: String,
        @Query("key") key: String
    ): Response<PlayList>

}