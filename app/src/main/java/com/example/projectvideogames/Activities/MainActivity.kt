package com.example.projectvideogames.Activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.projectvideogames.Fragments.FavoritesFragment
import com.example.projectvideogames.Fragments.HomeFragment
import com.example.projectvideogames.Repository.Repository
import com.example.projectvideogames.Utils.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.lifecycle.Observer
import com.example.projectvideogames.ViewModels.MainViewModel
import com.example.projectvideogames.ViewModels.MainViewModelFactory
import com.example.projectvideogames.Models.VideoGame
import com.example.projectvideogames.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    // Having the lists in a companion object makes it easier for the fragments access them
    companion object{
        var allGames:MutableList<VideoGame> = mutableListOf()
        var favoriteGames:MutableList<VideoGame> = mutableListOf()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_games)

        // To hide the status bar
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        // Using the api to get the list of games
        getData()

        val homeFragment = HomeFragment()
        val favoritesFragment = FavoritesFragment()

        makeCurrentFragment(homeFragment)

        val bottomNavBar = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavBar.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home_fragment -> makeCurrentFragment(homeFragment)
                R.id.favourites_fragment -> makeCurrentFragment(favoritesFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }
    }

    // Getting the games list from the API
    fun getData() {
        val repository = Repository()
        val viewModelFactory =
            MainViewModelFactory(
                repository
            )
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getVideoGame()
        viewModel.myResponse.observe(this, Observer { response ->
            if (response.isSuccessful){
                changeLists(response.body()?.results!!)
            }
        })
        Handler(Looper.myLooper()!!).postDelayed({
            for (i in 2..50){
                val nextUrl = "${Constants.PAGE_URL}?page=$i"
                viewModel.getVideoGamePage(nextUrl)
                viewModel.myResponse2.observe(this, Observer {
                    if (it.isSuccessful){
                        changeLists(it.body()?.results!!)
                    }
                })
            }
        }, 500)

    }

    // Import LiveData to allGames list and update adapters of HomeFragment
    fun changeLists(videoGames:MutableList<VideoGame>){
        for (game in videoGames){
            if (!allGames.contains(game)){
                allGames.add(game)
            }
        }

        if (allGames.size >= 500){
            findViewById<ProgressBar>(R.id.loading_circle).visibility = View.GONE
        }

        HomeFragment.viewPagerAdapter.updateList(listOf(allGames[0], allGames[1], allGames[2]))
        HomeFragment.gamesListAdapter.updateList(allGames.subList(3, allGames.size-1))
    }
}

