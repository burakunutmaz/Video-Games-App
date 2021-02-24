package com.example.projectvideogames.Adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectvideogames.Activities.GameDetailsActivity
import com.example.projectvideogames.Models.VideoGame
import com.example.projectvideogames.R

class GameListAdapter(val videoGames:MutableList<VideoGame>) : RecyclerView.Adapter<GameListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.video_game_item, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return videoGames.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvGameName?.text = videoGames[position].name
        holder.tvGameRating?.text = videoGames[position].rating.toString()
        holder.tvGameReleased?.text = videoGames[position].released
        holder.tvGameImage?.let {
            Glide.with(holder.itemView)
                    .load(videoGames[position].background_image)
                    .centerCrop()
                    .into(it)
        }

        holder.view.setOnClickListener {
            val intent = Intent(it.context, GameDetailsActivity::class.java)
            intent.putExtra("slug", videoGames[position].slug)
            val mActivity = it.context as Activity
            it.context.startActivity(intent)
            mActivity.overridePendingTransition(R.anim.slide_from_top, R.anim.slide_from_bottom)
        }
    }

    fun updateList(newlist: List<VideoGame>){
        videoGames.clear()
        videoGames.addAll(newlist)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        var view:View = itemView
        var tvGameName:TextView ?= null
        var tvGameRating:TextView ?= null
        var tvGameImage:ImageView ?= null
        var tvGameReleased:TextView ?= null

        init{
            tvGameImage = view.findViewById(R.id.game_image)
            tvGameName = view.findViewById(R.id.game_name)
            tvGameRating = view.findViewById(R.id.game_rating)
            tvGameReleased = view.findViewById(R.id.game_released)
        }

        override fun onClick(v: View?) {
        }

    }
}