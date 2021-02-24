package com.example.projectvideogames.Adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectvideogames.Activities.GameDetailsActivity
import com.example.projectvideogames.Models.VideoGame
import com.example.projectvideogames.R

class ViewPagerAdapter(private var videoGames:MutableList<VideoGame>)
    : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val itemImage: ImageView = itemView.findViewById(R.id.viewpager_image)
        override fun onClick(v: View?) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.video_game_viewpager, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return videoGames.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
                .load(videoGames[position].background_image)
                .centerCrop()
                .into(holder.itemImage)

        holder.itemImage.setOnClickListener {
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
}