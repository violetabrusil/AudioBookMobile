package com.example.thesisaudiobook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thesisaudiobook.model.PlayList;
import com.example.thesisaudiobook.utils.PlayListService;
import com.example.thesisaudiobook.utils.RetrofitService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePlayList extends AppCompatActivity {

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_create_play_list);

        initializeComponents();

    }

    private void initializeComponents() {
        EditText namePlayList = findViewById(R.id.namePlayList);
        Button cancel = findViewById(R.id.btnCancelPlayList);
        Button accept = findViewById(R.id.btnAddPlayList);

        fAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = fAuth.getCurrentUser();

        RetrofitService retrofitService = new RetrofitService();
        PlayListService playListService = retrofitService.getRetrofit().create(PlayListService.class);

        accept.setOnClickListener(view -> {
            String newPlayList = String.valueOf(namePlayList.getText());
            String userId = firebaseUser.getUid();

            PlayList playList = new PlayList();
            playList.setNamePlayList(newPlayList);
            playList.setUserId(userId);

            playListService.createPlayList(playList)
                    .enqueue(new Callback<PlayList>() {
                        @Override
                        public void onResponse(Call<PlayList> call, Response<PlayList> response) {
                            Toast.makeText(CreatePlayList.this, "PlayList creada", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onFailure(Call<PlayList> call, Throwable t) {
                            Toast.makeText(CreatePlayList.this, "Error al crear la playlist", Toast.LENGTH_SHORT).show();
                            Logger.getLogger(CreatePlayList.class.getName()).log(Level.SEVERE, "Error ocurred");
                        }
                    });




        });
    }
}