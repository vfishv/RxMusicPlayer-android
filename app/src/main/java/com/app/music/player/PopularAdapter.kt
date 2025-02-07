package com.app.music.player

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.orfium.rx.musicplayer.common.*
import com.orfium.rx.musicplayer.media.Media

class PopularAdapter(private val media: List<Media>) : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PopularViewHolder(inflater.inflate(R.layout.item_popular, parent, false))
    }

    override fun getItemCount(): Int = media.size

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val media = media[position]
        Glide.with(holder.coverImage.context)
            .load(media.image)
            .transition(DrawableTransitionOptions.withCrossFade(150))
            .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(16)))
            .into(holder.coverImage)
        holder.titleText.text = media.title
        holder.artistText.text = media.artist
        holder.mediaStateIcon.setImageResource(if (media.isPlaying()) android.R.drawable.ic_media_pause else android.R.drawable.ic_media_play)
        holder.mediaStateIcon.setOnClickListener{ media.playStop() }
        holder.addIcon.setOnClickListener { media.addQueue() }
    }

    class PopularViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val coverImage: ImageView = view.findViewById(R.id.coverImage)
        val mediaStateIcon: ImageView = view.findViewById(R.id.mediaStateIcon)
        val addIcon: ImageView = view.findViewById(R.id.addIcon)
        var titleText: TextView = view.findViewById(R.id.titleText)
        var artistText: TextView = view.findViewById(R.id.artistText)
    }

    /*
    class PopularViewHolder(val containerView: View) : RecyclerView.ViewHolder(containerView) {

        fun bind(media: Media) {
            Glide.with(coverImage.context)
                .load(media.image)
                .transition(DrawableTransitionOptions.withCrossFade(150))
                .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(16)))
                .into(coverImage)
            titleText.text = media.title
            artistText.text = media.artist
            mediaStateIcon.setImageResource(if (media.isPlaying()) android.R.drawable.ic_media_pause else android.R.drawable.ic_media_play)
            mediaStateIcon.setOnClickListener{ media.playStop() }
            addIcon.setOnClickListener { media.addQueue() }
        }
    }
    */


}