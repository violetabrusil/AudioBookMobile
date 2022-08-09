package com.example.thesisaudiobook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.thesisaudiobook.R
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import android.view.View
import android.view.Window
import com.example.thesisaudiobook.Login

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar!!.hide()
        setContentView(R.layout.activity_main)
    }

    fun logout(view: View?) {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(applicationContext, Login::class.java))
        finish()
    }
}