package com.as43.dawidjk2.globalgpspong;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
}
