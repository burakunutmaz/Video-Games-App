package com.example.projectvideogames.Fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.projectvideogames.*
import com.example.projectvideogames.Activities.MainActivity
import com.example.projectvideogames.Adapters.GameListAdapter
import com.example.projectvideogames.Adapters.ViewPagerAdapter
import com.example.projectvideogames.Models.VideoGame
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment(R.layout.fragment_home) {
    lateinit var gamesListRecycler:RecyclerView
    lateinit var viewpagerRecycler:ViewPager2
    lateinit var indicator:TabLayout
    lateinit var mContext: Context
    lateinit var searchBar: EditText

    companion object{
        lateinit var gamesListAdapter: GameListAdapter
        lateinit var viewPagerAdapter: ViewPagerAdapter
    }

    var videoGameList: MutableList<VideoGame> = mutableListOf()
    var viewPagerGameList: MutableList<VideoGame> = mutableListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gamesListAdapter =
            GameListAdapter(videoGameList)
        viewPagerAdapter =
            ViewPagerAdapter(
                viewPagerGameList
            )

        gamesListRecycler = view.findViewById(R.id.games_recycler)
        viewpagerRecycler = view.findViewById(R.id.view_pager)
        indicator = view.findViewById(R.id.tabDots)
        searchBar = view.findViewById(R.id.search_bar_input)

        gamesListRecycler.layoutManager = LinearLayoutManager(mContext)
        viewpagerRecycler.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        gamesListRecycler.adapter = gamesListAdapter
        viewpagerRecycler.adapter = viewPagerAdapter

        // Handling the search bar actions
        searchBar.addTextChangedListener(object :TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                // If letter count is above or equal to 3,
                // Do a search and filter the list, update the adapters accordingly
                if (searchBar.text.length >= 3){
                    val newVideoGames = MainActivity.allGames.filter {
                        videoGame -> videoGame.name.toUpperCase().contains(searchBar.text.toString().toUpperCase())
                    }
                    viewpagerRecycler.visibility = View.GONE
                    indicator.visibility = View.GONE
                    gamesListAdapter.updateList(newVideoGames)

                    // If ater a search, there are no games in the filtered list, show message
                    if ( newVideoGames.isEmpty() ){
                        view.findViewById<TextView>(R.id.uzgunuz).visibility = View.VISIBLE
                    }
                    else{
                        view.findViewById<TextView>(R.id.uzgunuz).visibility = View.GONE
                    }
                }
                // If user clears the search bar, turn everything back to it's original state
                else if (searchBar.text.isEmpty()){
                    viewpagerRecycler.visibility = View.VISIBLE
                    indicator.visibility = View.VISIBLE
                    gamesListAdapter.updateList(MainActivity.allGames.subList(3, MainActivity.allGames.size-1))
                    view.findViewById<TextView>(R.id.uzgunuz).visibility = View.GONE
                }
            }

        })

        TabLayoutMediator(indicator, viewpagerRecycler) { tab, position ->
        }.attach()

    }
}