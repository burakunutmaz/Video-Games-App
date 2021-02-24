package com.example.projectvideogames.Activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.projectvideogames.Fragments.FavoritesFragment
import com.example.projectvideogames.ViewModels.MainViewModel
import com.example.projectvideogames.ViewModels.MainViewModelFactory
import com.example.projectvideogames.Models.VideoGame
import com.example.projectvideogames.R
import com.example.projectvideogames.Repository.Repository

class GameDetailsActivity : AppCompatActivity() {

    // Create variables
    private lateinit var viewModel: MainViewModel
    lateinit var tvDetailGameName:TextView
    lateinit var tvDetailMetacritic:TextView
    lateinit var tvDetailReleased:TextView
    lateinit var tvDescription:TextView
    lateinit var ivDetailImage:ImageView
    lateinit var btnLikeButton:Button
    lateinit var btnDeleteButton:Button
    lateinit var currentGame:VideoGame

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // To hide the status bar
        @Suppress("DEPRECATION")
        setContentView(R.layout.activity_game_details)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        // Find items in the layout
        tvDetailGameName = findViewById(R.id.game_details_title)
        tvDescription = findViewById(R.id.game_details_full)
        tvDetailMetacritic = findViewById(R.id.game_details_rating)
        tvDetailReleased = findViewById(R.id.game_details_released)
        ivDetailImage = findViewById(R.id.game_details_image)
        btnLikeButton = findViewById(R.id.add_to_favs_button)
        btnDeleteButton = findViewById(R.id.delete_from_favs_button)

        // Get the "slug" string from the intent and use it to find which video game object
        // the activity currently is dealing with.
        val gameKey = intent.getStringExtra("slug").toString()
        getDetailData(gameKey)
        MainActivity.allGames.forEach { game ->
            if (game.slug == gameKey){
                currentGame = game
            }
        }

        // Handle Like/Delete button visibility
        if (MainActivity.favoriteGames.contains(currentGame)){
            btnLikeButton.visibility = View.GONE
            btnDeleteButton.visibility = View.VISIBLE
        }

        btnLikeButton.setOnClickListener {
            MainActivity.favoriteGames.add(currentGame)
            btnLikeButton.visibility = View.GONE
            btnDeleteButton.visibility = View.VISIBLE
            FavoritesFragment.favoritesAdapter.updateList(MainActivity.favoriteGames)
            Toast.makeText(this, "Game added to favorites!", Toast.LENGTH_SHORT).show()
        }

        btnDeleteButton.setOnClickListener {
            MainActivity.favoriteGames.remove(currentGame)
            btnLikeButton.visibility = View.VISIBLE
            btnDeleteButton.visibility = View.GONE
            FavoritesFragment.favoritesAdapter.updateList(MainActivity.favoriteGames)
            Toast.makeText(this, "Game deleted from favorites!", Toast.LENGTH_SHORT).show()
        }
        
    }

    // Get the game details from the API
    fun getDetailData(gameKey:String){
        val repository = Repository()
        val viewModelFactory =
            MainViewModelFactory(
                repository
            )
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getGameDetail(gameKey)
        viewModel.myResponse3.observe(this, Observer { response ->
            if (response.isSuccessful){
                tvDescription.text = stripHtml(response.body()?.description.toString())
                tvDetailGameName.text = response.body()?.name.toString()

                val score:String = "Metacritic Score:  " +  response.body()?.metacritic.toString()
                tvDetailMetacritic.text = score

                val release:String = "Release date:  " + response.body()?.released.toString()
                tvDetailReleased.text = release
                Glide.with(this)
                        .load(response.body()?.background_image.toString())
                        .centerCrop()
                        .into(ivDetailImage)
            }
        })
    }

    // Game description has html tags in it, this function is to get rid of them.
    fun stripHtml(html:String):String{
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString()
        }
        else{
            return Html.fromHtml(html).toString()
        }
    }
}