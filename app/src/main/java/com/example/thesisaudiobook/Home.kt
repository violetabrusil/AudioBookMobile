package com.example.thesisaudiobook

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thesisaudiobook.model.AudioBookList
import com.example.thesisaudiobook.model.AudioBookAdapter
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

   private fun callAudioBookService (){

       val retrofitServiceAudioBook = RetrofitServiceAudioBook()
       val audioBookService = retrofitServiceAudioBook.retrofit?.create(
           AudioBookService::class.java
       )
       val result: Call<List<AudioBookList>> = audioBookService!!.getAllAudioBooks()

       var data = MutableLiveData<List<AudioBookList>>()


       result.enqueue(object : Callback<List<AudioBookList>> {
           override fun onFailure(call: Call<List<AudioBookList>>, t: Throwable) {
                Toast.makeText(this@Home, "Error", Toast.LENGTH_SHORT).show()
           }

           override fun onResponse(call: Call<List<AudioBookList>>, response: Response<List<AudioBookList>>
           ) {
               if(response.isSuccessful){
                   data.value = response!!.body()!!
                   val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                   recommends = response.body() as MutableList<AudioBookList>
                   if (recommends != null) {
                       audiobookAdapter = AudioBookAdapter(recommends!!)
                       val mLayoutManager = LinearLayoutManager(applicationContext)
                       mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
                       recyclerView.layoutManager = mLayoutManager
                       recyclerView.itemAnimator = DefaultItemAnimator()
                       recyclerView.adapter = audiobookAdapter
                   }

               }
           }
       })



   }
}