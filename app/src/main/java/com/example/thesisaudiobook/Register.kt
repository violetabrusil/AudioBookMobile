package com.example.thesisaudiobook

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.firestore.FirebaseFirestore
import android.os.Bundle
import android.view.WindowManager
import com.example.thesisaudiobook.R
import android.text.TextUtils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import com.example.thesisaudiobook.Login
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.gms.tasks.OnSuccessListener
import com.example.thesisaudiobook.Home
import com.example.thesisaudiobook.model.User
import com.google.android.gms.tasks.OnFailureListener
import java.lang.Exception

class Register : AppCompatActivity() {

    private lateinit var userName: EditText
    private lateinit var userEmail: EditText
    private lateinit var userPassword: EditText
    private lateinit var signUp: Button
    private lateinit var registerWithGoogle: Button
    private lateinit var fAuth: FirebaseAuth
    private lateinit var progressBar: ProgressBar
    private lateinit var loginBtn: TextView

    private var googleSignInClient: GoogleSignInClient? = null
    private var db: FirebaseFirestore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar!!.hide()
        setContentView(R.layout.activity_register)
        userName = findViewById(R.id.userName)
        userEmail = findViewById(R.id.userEmail)
        userPassword = findViewById(R.id.userPassword)
        signUp = findViewById(R.id.signUp)
        progressBar = findViewById(R.id.progressBar)
        loginBtn = findViewById(R.id.createText)
        registerWithGoogle = findViewById(R.id.registerWithGoogle)
        fAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        signUp.setOnClickListener(View.OnClickListener {
            val name = userName.getText().toString().trim { it <= ' ' }
            val email = userEmail.getText().toString().trim { it <= ' ' }
            val password = userPassword.getText().toString().trim { it <= ' ' }
            val access = "true"
            val rol = "final"
            val photo = ""
            if (TextUtils.isEmpty(name)) {
                userName.setError("UserName is required")
                return@OnClickListener
            } else if (TextUtils.isEmpty(email)) {
                userEmail.setError("Email is required")
                return@OnClickListener
            } else if (TextUtils.isEmpty(password)) {
                userPassword.setError("Password is required")
                return@OnClickListener
            } else if (password.length < 6) {
                userPassword.setError("La contraseña debe ser mayor de 6 caracteres")
                return@OnClickListener
            } else {
                Toast.makeText(this@Register, "Usuario Creado", Toast.LENGTH_SHORT).show()
            }
            progressBar.setVisibility(View.VISIBLE)

            //Register the user in Firebase
            fAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = fAuth!!.currentUser
                    val userId = firebaseUser!!.uid
                    Log.d("userid", userId)
                    db!!.collection("users").document(userId).get()
                    val userToCreate = User(name, email, access, photo, rol)
                    db!!.collection("users").document(userId).set(userToCreate)
                    Toast.makeText(this@Register, "Usuario Creado", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, Login::class.java))
                } else {
                    Toast.makeText(
                        this@Register,
                        "Error " + task.exception!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    progressBar.setVisibility(View.GONE)
                }
            }
        })
        loginBtn.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    Login::class.java
                )
            )
        })
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        registerWithGoogle.setOnClickListener(View.OnClickListener { //Design google sign in
            Log.d(TAG, "Google SignIn")
            val intent = googleSignInClient!!.signInIntent
            startActivityForResult(intent, RC_SIGN_IN)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            Log.d(TAG, "onActivityResult: Google SignIn intent result")
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = accountTask.getResult(
                    ApiException::class.java
                )
                firebaseAuthWithGoogleAccount(account)
            } catch (e: Exception) {
                Log.d(TAG, "onActivityResult: " + e.message)
            }
        }
    }

    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount) {
        Log.d(TAG, "FirebaseAuthWithGoogleAccount: begin firebase auth with google account")
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        fAuth!!.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                Log.d(TAG, "onSuccess: Logged in")
                val firebaseUser = fAuth!!.currentUser
                val userId = firebaseUser!!.uid
                val nameUser = firebaseUser.displayName
                val emailUser = firebaseUser.email
                val photoUser = firebaseUser.photoUrl.toString()
                val access = "true"
                val rol = "final"
                if (authResult.additionalUserInfo!!.isNewUser) {
                    db!!.collection("users").document(userId).get()
                    val userToCreate = User(nameUser, emailUser, access, photoUser, rol)
                    db!!.collection("users").document(userId).set(userToCreate)
                    Log.d(TAG, "onSuccesfull")
                    Toast.makeText(this@Register, "Usuario Creado", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, Login::class.java))
                } else {
                    Toast.makeText(this@Register, "Usuario existente", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, Home::class.java))
                }
            }
            .addOnFailureListener { e -> Log.d(TAG, "onFailure loggin failed" + e.message) }
    }

    companion object {
        private const val TAG = "GOOGLE_SIGN_IN_TAG"
        private const val RC_SIGN_IN = 100
    }
}