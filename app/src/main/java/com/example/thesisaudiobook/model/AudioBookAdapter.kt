package com.example.thesisaudiobook.model

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.thesisaudiobook.R
import com.example.thesisaudiobook.Reproductor
import com.squareup.picasso.Picasso

internal class AudioBookAdapter(private var audioBookList: List<AudioBookList>) :
    RecyclerView.Adapter<AudioBookAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.nameAudioBook)
        var author: TextView = view.findViewById(R.id.nameAuthor)
        var urlImage: ImageView = view.findViewById(R.id.playAudioBook)
        var linearLayout: LinearLayout = view.findViewById(R.id.view_audiobook_linearlayout)
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
        audioBook.getIdAudioBook()
        holder.linearLayout.setOnClickListener {
            v ->
            val intent = Intent(
                v.context,
                Reproductor::class.java

            )
            intent.putExtra("audioBook", audioBook)
            v.context.startActivity(intent)
        }
        Log.d("idAudiobOOK", audioBook.getIdAudioBook().toString())

    }
    override fun getItemCount(): Int {
        return audioBookList.size
    }
}