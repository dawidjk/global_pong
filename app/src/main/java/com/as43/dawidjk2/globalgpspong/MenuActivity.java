package com.as43.dawidjk2.globalgpspong;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class MenuActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    Boolean musicState = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button trackBall = findViewById(R.id.track);
        Button signin = findViewById(R.id.signin);
        Button toggleSound = findViewById(R.id.sound);
        final Context context = getApplicationContext();


        trackBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        toggleSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (musicState) {
                    musicState = false;
                    mediaPlayer.pause();
                } else {
                    musicState = true;
                    mediaPlayer.start();
                }
            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
        mediaPlayer.release();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bensoundhouse);
        mediaPlayer.start();
    }
}
