package com.example.projectvideogames.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectvideogames.Models.GameDetail
import com.example.projectvideogames.Models.VideoGamesList
import com.example.projectvideogames.Repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository:Repository): ViewModel() {

    // Holds initial video game list
    val myResponse: MutableLiveData<Response<VideoGamesList>> = MutableLiveData()

    // Holds video game list got with page number
    val myResponse2: MutableLiveData<Response<VideoGamesList>> = MutableLiveData()

    // Holds game details
    val myResponse3: MutableLiveData<Response<GameDetail>> = MutableLiveData()

    fun getVideoGame(){
        viewModelScope.launch {
            val response = repository.getVideGame()
            myResponse.value = response
        }
    }

    fun getVideoGamePage(url: String){
        viewModelScope.launch {
            val response = repository.getVideoGamePage(url)
            myResponse2.value = response

        }
    }

    fun getGameDetail(gameKey: String){
        viewModelScope.launch {
            val response = repository.getGameDetail(gameKey)
            myResponse3.value = response
        }
    }
}