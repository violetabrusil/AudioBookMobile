package com.example.thesisaudiobook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {

    private FirebaseAuth fAuth;
    TextView nameUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);

        fAuth = FirebaseAuth.getInstance();
        nameUser = findViewById(R.id.nameUser);
    }

    private void checkUser() {
        //Get User
        FirebaseUser firebaseUser = fAuth.getCurrentUser();
        if (firebaseUser != null) {
            //user not logged
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        } else {
            String name = firebaseUser.getDisplayName();
            nameUser.setText(name);
        }
    }
}