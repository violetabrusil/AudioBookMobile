package com.example.thesisaudiobook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText

class AddToPlayList : AppCompatActivity() {

    private lateinit var btnReturn: Button
    private lateinit var btnNewPlayList: Button
    private lateinit var searchPlayList: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_add_to_play_list)
        supportActionBar!!.hide()

        btnReturn = findViewById(R.id.btnReturn)
        btnNewPlayList = findViewById(R.id.btnNewPlaylist)
        searchPlayList = findViewById(R.id.searchPlayList)

        btnReturn.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    Reproductor::class.java
                )
            )
        }

        btnNewPlayList.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    CreatePlayList::class.java
                )
            )
        }
    }
}