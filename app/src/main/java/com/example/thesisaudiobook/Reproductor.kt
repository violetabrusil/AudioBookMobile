package com.example.thesisaudiobook

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import com.example.thesisaudiobook.model.AudioBookList
import com.squareup.picasso.Picasso
import java.util.concurrent.TimeUnit

class Reproductor : AppCompatActivity() {

    private var imageView: ImageView? = null
    private lateinit var btnPlay: Button
    private lateinit var mp: MediaPlayer
    private lateinit var sbProgress: SeekBar
    private lateinit var textCurrentTime:TextView
    private lateinit var textDurationTime:TextView
    private lateinit var audioBookSelected: AudioBookList
    private lateinit var titleAudioBook: TextView
    private lateinit var nameAuthor: TextView
    private var totalTime: Int = 0

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
        titleAudioBook = findViewById(R.id.titleNameBook)
        nameAuthor = findViewById(R.id.nameAutor)
        textCurrentTime = findViewById(R.id.textCurrentTime)
        textDurationTime = findViewById(R.id.textTotalDuration)

        audioBookSelected = intent.getSerializableExtra("audioBook") as AudioBookList

        val imageUrl = audioBookSelected.getUrlImage()
        Picasso.get().load(imageUrl).into(imageView)

        titleAudioBook.setText(audioBookSelected.getTitleAudiobook())
        nameAuthor.setText(audioBookSelected.getAuthor())

        //MediaPLayer
        val audioUrl = audioBookSelected.getUrlAudio()
        mp = MediaPlayer()
        mp.setDataSource(audioUrl)
        mp.prepare()
        totalTime = mp.duration

        //Progress Bar
        sbProgress.max = totalTime
        sbProgress.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        mp.seekTo(progress)
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                    TODO("Not yet implemented")
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                    TODO("Not yet implemented")
                }
            }
        )

        //Thread
        Thread(Runnable {
            while (mp != null) {
                try {
                    var msg = Message()
                    msg.what = mp.currentPosition
                    handler.sendMessage(msg)
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {

                }
            }
        }).start()

    }

    fun playAudioBook(v : View) {
        if (mp.isPlaying) {
            //Stop
            mp.pause()
            btnPlay.setBackgroundResource(R.drawable.ic_baseline_play_circle_filled_24)
        } else {
            //Start
            mp.start()
            btnPlay.setBackgroundResource(R.drawable.ic_baseline_pause_circle_filled_24)
        }
    }

    @SuppressLint("HandlerLeak")
    var handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            var currentPosition = msg.what

            //Update progressBar
            sbProgress.progress = currentPosition

            //Update text
            var currentTime = createTimeLabel(currentPosition)
            textCurrentTime.text = currentTime
            var remainingTime = createTimeLabel(totalTime - currentPosition)
            textDurationTime.text = "-$remainingTime"
        }
    }

    fun createTimeLabel(time: Int): String {
        var timeLabel = ""
        var min = time / 1000 / 60
        var sec = time / 1000 % 60

        timeLabel = "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec
        return timeLabel
    }



    override fun onDestroy() {
        super.onDestroy()
        if(mp == null)
            mp!!.release()
        mp == null
    }


}


