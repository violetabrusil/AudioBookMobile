package com.example.thesisaudiobook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thesisaudiobook.model.PlayList;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class Login extends AppCompatActivity {

    EditText lEmail, lPassword;
    Button btnLogin, signInGoogle;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    TextView createBtn;

    private GoogleSignInClient googleSignInClient;
    private static final String TAG = "GOOGLE_SIGN_IN_TAG";
    private static final int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        lEmail = findViewById(R.id.email);
        lPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar2);
        btnLogin = findViewById(R.id.signIn);
        createBtn = findViewById(R.id.createText2);
        signInGoogle = findViewById(R.id.signInWithGoogle);

        fAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = lEmail.getText().toString().trim();
                String password = lPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    lEmail.setError("El email es requerido");
                }

                if (TextUtils.isEmpty(password)) {
                    lPassword.setError("La contraseña es requerida");
                }

                if (password.length() < 6) {
                    lPassword.setError("La contraseña debe ser mayor de 6 caracteres");
                }

                progressBar.setVisibility(View.VISIBLE);

                //Authenticate the user

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Login.this, "Logged is succesfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Home.class));
                        } else {
                            Toast.makeText(Login.this, "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

        //Configure the Google SignIn
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        signInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Design google sign in
                Log.d(TAG,"Google SignIn");
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == RC_SIGN_IN) {
            Log.d(TAG, "onActivityResult: Google SignIn intent result");
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = accountTask.getResult(ApiException.class);
                firebaseAuthWithGoogleAccount(account);
            } catch (Exception e) {
                Log.d(TAG, "onActivityResult: " + e.getMessage());
            }
        }
    }

    private void firebaseAuthWithGoogleAccount(GoogleSignInAccount account) {
        Log.d(TAG, "FirebaseAuthWithGoogleAccount: begin firebase auth with google account");
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        fAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d(TAG, "onSuccess: Logged in");
                        FirebaseUser firebaseUser = fAuth.getCurrentUser();
                        String uid = firebaseUser.getUid();
                        String email = firebaseUser.getEmail();
                        Toast.makeText(Login.this, "Logged is succesfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Home.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure loggin failed" + e.getMessage());
                    }
                });
    }
}