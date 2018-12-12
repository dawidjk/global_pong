package com.as43.dawidjk2.globalgpspong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class MapActivity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mImageView = findViewById(R.id.mapImage);

        try {
            // get input stream
            InputStream ims = getAssets().open("map.jpeg");
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            mImageView.setImageDrawable(d);
            ims.close();
        }
        catch(IOException ex) {
            return;
        }
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
        Music.SoundPlayer(this, R.raw.bensoundhouse);
        Music.player.start();
    }

}
