package com.example.thesisaudiobook

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import com.example.thesisaudiobook.utils.RetrofitServicePlayList
import com.example.thesisaudiobook.utils.PlayListService
import com.example.thesisaudiobook.model.PlayList
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.logging.Level
import java.util.logging.Logger

class CreatePlayList : AppCompatActivity() {
    var fAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar!!.hide()
        setContentView(R.layout.activity_create_play_list)
        initializeComponents()
    }

    private fun initializeComponents() {
        val namePlayList = findViewById<EditText>(R.id.namePlayList)
        val cancel = findViewById<Button>(R.id.btnCancelPlayList)
        val accept = findViewById<Button>(R.id.btnAddPlayList)
        fAuth = FirebaseAuth.getInstance()
        val firebaseUser = fAuth!!.currentUser
        val retrofitServicePlayList = RetrofitServicePlayList()
        val playListService = retrofitServicePlayList.retrofit?.create(
            PlayListService::class.java
        )
        accept.setOnClickListener { view: View? ->
            val newPlayList = namePlayList.text.toString()
            val userId = firebaseUser!!.uid
            val playList = PlayList()
            playList.namePlayList = newPlayList
            playList.userId = userId
            playListService?.createPlayList(playList)
                ?.enqueue(object : Callback<PlayList?> {
                    override fun onResponse(call: Call<PlayList?>, response: Response<PlayList?>) {
                        Toast.makeText(this@CreatePlayList, "PlayList creada", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onFailure(call: Call<PlayList?>, t: Throwable) {
                        Toast.makeText(
                            this@CreatePlayList,
                            "Error al crear la playlist",
                            Toast.LENGTH_SHORT
                        ).show()
                        Logger.getLogger(CreatePlayList::class.java.name)
                            .log(Level.SEVERE, "Error ocurred")
                    }
                })
        }
    }
}