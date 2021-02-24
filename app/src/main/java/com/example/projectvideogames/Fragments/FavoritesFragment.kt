package com.example.projectvideogames.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectvideogames.Adapters.GameListAdapter
import com.example.projectvideogames.Activities.MainActivity
import com.example.projectvideogames.Models.VideoGame
import com.example.projectvideogames.R


class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    lateinit var mContext:Context
    lateinit var favoritesRecycler:RecyclerView
    lateinit var nofav:TextView

    companion object{
        var favoritesList:MutableList<VideoGame> = mutableListOf()
        var favoritesAdapter: GameListAdapter =
            GameListAdapter(favoritesList)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoritesRecycler = view.findViewById(R.id.favourites_recycler)
        favoritesAdapter =
            GameListAdapter(favoritesList)
        favoritesRecycler.layoutManager = LinearLayoutManager(mContext)
        favoritesRecycler.adapter = favoritesAdapter

        // If there are no games in favorite games list, show message
        nofav = view.findViewById(R.id.no_fav)
        if(!favoritesList.isEmpty()){
            nofav.visibility = View.GONE
        } else{
            nofav.visibility = View.VISIBLE
        }
    }



}