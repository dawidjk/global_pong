package com.as43.dawidjk2.globalgpspong;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class MenuActivity extends AppCompatActivity {

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
                    Music.player.pause();
                } else {
                    musicState = true;
                    Music.player.start();
                }
            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        Music.player.stop();
        Music.player.release();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Music.player.start();
    }
}
