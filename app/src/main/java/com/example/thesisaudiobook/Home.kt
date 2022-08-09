package com.example.thesisaudiobook

import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.thesisaudiobook.model.AudioBook
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

       lateinit var imageAudioBook: ImageView
       lateinit var nameAudioBook: TextView
       lateinit var nameAuthor: TextView

       val retrofitServiceAudioBook = RetrofitServiceAudioBook()
       val audioBookService = retrofitServiceAudioBook.retrofit?.create(
           AudioBookService::class.java
       )
       val result: Call<List<AudioBook>> = audioBookService!!.getAllAudioBooks()


       result.enqueue(object : Callback<List<AudioBook>> {
           override fun onFailure(call: Call<List<AudioBook>>, t: Throwable) {
                Toast.makeText(this@Home, "Error", Toast.LENGTH_SHORT).show()
           }

           override fun onResponse(call: Call<List<AudioBook>>, response: Response<List<AudioBook>>
           ) {
               val audioBook: List<AudioBook>? = response.body()
               


               Toast.makeText(this@Home, "OK", Toast.LENGTH_SHORT).show()

           }
       })
   }
}