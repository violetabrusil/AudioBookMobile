package com.example.thesisaudiobook

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import android.os.Bundle
import android.view.WindowManager
import android.text.TextUtils
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import java.lang.Exception

class Login : AppCompatActivity() {

    lateinit var lEmail: EditText
    lateinit var lPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var signInGoogle: Button
    private lateinit var progressBar: ProgressBar
    private  lateinit var fAuth: FirebaseAuth
    private lateinit var createBtn: TextView

    private var googleSignInClient: GoogleSignInClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar!!.hide()
        setContentView(R.layout.activity_login)
        lEmail = findViewById(R.id.email)
        lPassword = findViewById(R.id.password)
        progressBar = findViewById(R.id.progressBar2)
        btnLogin = findViewById(R.id.signIn)
        createBtn = findViewById(R.id.createText2)
        signInGoogle = findViewById(R.id.signInWithGoogle)
        fAuth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener(View.OnClickListener {
            val email = lEmail.getText().toString().trim { it <= ' ' }
            val password = lPassword.getText().toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(email)) {
                lEmail.setError("El email es requerido")
            }
            if (TextUtils.isEmpty(password)) {
                lPassword.setError("La contraseña es requerida")
            }
            if (password.length < 6) {
                lPassword.setError("La contraseña debe ser mayor de 6 caracteres")
            }
            progressBar.setVisibility(View.VISIBLE)

            //Authenticate the user
            fAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@Login, "Logged is succesfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, Home::class.java))
                } else {
                    Toast.makeText(this@Login, "Error: " + task.exception, Toast.LENGTH_SHORT)
                        .show()
                    progressBar.setVisibility(View.GONE)
                }
            }
        })
        createBtn.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    Register::class.java
                )
            )
        })

        //Configure the Google SignIn
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        signInGoogle.setOnClickListener(View.OnClickListener { //Design google sign in
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
            .addOnSuccessListener {
                Log.d(TAG, "onSuccess: Logged in")
                val firebaseUser = fAuth!!.currentUser
                val uid = firebaseUser!!.uid
                val email = firebaseUser.email
                Toast.makeText(this@Login, "Logged is succesfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, Home::class.java))
            }
            .addOnFailureListener { e -> Log.d(TAG, "onFailure loggin failed" + e.message) }
    }

    companion object {
        private const val TAG = "GOOGLE_SIGN_IN_TAG"
        private const val RC_SIGN_IN = 100
    }
}