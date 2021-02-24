package com.example.projectvideogames.Api

import com.example.projectvideogames.Models.GameDetail
import com.example.projectvideogames.Models.VideoGamesList
import retrofit2.Response
import retrofit2.http.*

interface NetworkClient {

    @Headers(	"x-rapidapi-key: 742c37f70dmshfd6f66eb61718e3p1e4f81jsncb950cd798a3",
                        "x-rapidapi-host: rawg-video-games-database.p.rapidapi.com",
                        "useQueryString: true")
    @GET("games")
    suspend fun getVideoGame(): Response<VideoGamesList>


    @GET
    suspend fun getVideoGamePage(
        @Url url: String
    ) : Response<VideoGamesList>

    @Headers(	"x-rapidapi-key: 742c37f70dmshfd6f66eb61718e3p1e4f81jsncb950cd798a3",
            "x-rapidapi-host: rawg-video-games-database.p.rapidapi.com",
            "useQueryString: true")
    @GET("games/{gameKey}")
    suspend fun getVideoGameDetail(
        @Path("gameKey") key:String
    ) : Response<GameDetail>

}