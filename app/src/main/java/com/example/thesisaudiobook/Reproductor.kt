package com.example.thesisaudiobook

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import com.squareup.picasso.Picasso

class Reproductor : AppCompatActivity() {

    private var imageView: ImageView? = null
    private lateinit var btnPlay: ImageButton
    private var mp: MediaPlayer? = null
    private lateinit var sbProgress: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_reproductor)
        supportActionBar!!.hide()
        imageView = findViewById(R.id.reproductorView)
        btnPlay = findViewById(R.id.btnPlayReproductor)
        sbProgress = findViewById(R.id.seekbar)


        val imageUrl = "https://firebasestorage.googleapis.com/v0/b/fir-multimedia-storage.appspot.com/o/violeta.jpeg?alt=media&token=0b83c7b5-7b1e-42c3-bdb0-ad6698fa4a88"
        Picasso.get().load(imageUrl).into(imageView)

        val audioUrl = "https://firebasestorage.googleapis.com/v0/b/fir-multimedia-storage.appspot.com/o/foster-the-people-imagination-official-audio.mp3?alt=media&token=ae82b669-2fb9-44e2-9808-ff4727756fcb"

        var myTracking = myAudioTrack()
        myTracking.start()

        btnPlay.setOnClickListener(View.OnClickListener {
            mp = MediaPlayer()
            try {
                mp!!.setDataSource(audioUrl)
                mp!!.prepare()
                mp!!.start()
                sbProgress.max = mp!!.duration
            }catch (ex:Exception){
            }

        })

    }

    inner class myAudioTrack(): Thread() {

        override fun run() {
            while (true) {
                try {
                    Thread.sleep(1000)
                }catch (ex:Exception){ }
                runOnUiThread{
                    if (mp != null) {
                        sbProgress.progress = mp!!.currentPosition
                    }
                }
            }
        }

    }
}