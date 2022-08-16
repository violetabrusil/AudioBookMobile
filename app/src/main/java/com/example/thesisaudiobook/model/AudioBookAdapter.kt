package com.example.thesisaudiobook.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.thesisaudiobook.R
import com.squareup.picasso.Picasso

internal class AudioBookAdapter(private var audioBookList: List<AudioBookList>) :
    RecyclerView.Adapter<AudioBookAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.nameAudioBook)
        var author: TextView = view.findViewById(R.id.nameAuthor)
        var urlImage: ImageView = view.findViewById(R.id.playAudioBook)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_audio_book, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val audioBook = audioBookList[position]
        holder.title.text = audioBook.getTitleAudiobook()
        holder.author.text = audioBook.getAuthor()
        Picasso.get().load(audioBook.getUrlImage()).into(holder.urlImage)
    }
    override fun getItemCount(): Int {
        return audioBookList.size
    }
}