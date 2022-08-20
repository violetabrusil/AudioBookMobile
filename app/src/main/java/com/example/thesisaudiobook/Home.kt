package com.example.thesisaudiobook

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thesisaudiobook.model.AudioBookList
import com.example.thesisaudiobook.model.AudioBookAdapter
import com.example.thesisaudiobook.model.AudioBookViewModel
import com.example.thesisaudiobook.utils.AudioBookService
import com.example.thesisaudiobook.utils.RetrofitServiceAudioBook
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Home : AppCompatActivity() {

    private lateinit  var fAuth: FirebaseAuth
    private lateinit  var fStore: FirebaseFirestore
    private lateinit var userId: String
    private lateinit var nameUser: TextView
    private lateinit var reproductor: Button
    private var recommends: MutableList<AudioBookList> = mutableListOf<AudioBookList>()
    private lateinit var audiobookAdapter: AudioBookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar!!.hide()
        setContentView(R.layout.activity_home)
        fAuth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        nameUser = findViewById(R.id.nameUser)
        userId = fAuth!!.currentUser!!.uid
        Log.d("usuerioa id", userId!!)
        val documentReference = fStore!!.collection("users").document(
            userId!!
        )
        documentReference.addSnapshotListener(this) { documentSnapshot, error ->
            nameUser.setText(
                documentSnapshot!!.getString("userName")
            )
        }

        reproductor = findViewById(R.id.btnReproductor)

        reproductor.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    Reproductor::class.java
                )
            )
        })

        callAudioBookService()


    }

//    private fun checkUser() {
//        //Get User
//        val firebaseUser = fAuth!!.currentUser
//        if (firebaseUser!!.displayName === "") {
//            val documentReference = fStore!!.collection("users").document(
//                userId!!
//            )
//            documentReference.addSnapshotListener(this) { documentSnapshot, error ->
//                nameUser!!.text = documentSnapshot!!.getString("userName")
//            }
//        } else {
//            val name = firebaseUser!!.displayName
//            nameUser!!.text = name
//        }
//    }

   private fun callAudioBookService() {

       val viewModel = ViewModelProvider(this).get(AudioBookViewModel::class.java)
       viewModel.getAudioBooks()
       viewModel.audioBookList.observe(this, Observer {
           for (audioBook in it) {
               recommends.add(audioBook)
           }

           if (recommends != null) {
               val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
               audiobookAdapter = AudioBookAdapter(recommends!!)
               audiobookAdapter.notifyDataSetChanged()
               val mLayoutManager = LinearLayoutManager(applicationContext)
               mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
               recyclerView.layoutManager = mLayoutManager
               recyclerView.itemAnimator = DefaultItemAnimator()
               recyclerView.adapter = audiobookAdapter
           }

       })


   }
}


