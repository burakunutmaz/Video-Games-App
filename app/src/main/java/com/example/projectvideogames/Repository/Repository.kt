package com.example.projectvideogames.Repository

import com.example.projectvideogames.Api.RetrofitInstance
import com.example.projectvideogames.Models.GameDetail
import com.example.projectvideogames.Models.VideoGamesList
import retrofit2.Response

class Repository {
    suspend fun getVideGame(): Response<VideoGamesList>{
        return RetrofitInstance.api.getVideoGame()
    }

    suspend fun getVideoGamePage(url: String): Response<VideoGamesList>{
        return RetrofitInstance.api.getVideoGamePage(url)
    }

    suspend fun getGameDetail(gameKey: String): Response<GameDetail>{
        return RetrofitInstance.api.getVideoGameDetail(gameKey)
    }
}