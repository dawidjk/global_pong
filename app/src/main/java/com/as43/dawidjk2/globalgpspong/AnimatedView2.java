package com.as43.dawidjk2.globalgpspong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AnimatedView2 extends android.support.v7.widget.AppCompatImageView{
    private Context mContext;
    double x = getHeight()/2;
    double y = getWidth()/2;
    private int xVelocity = 10;
    private Handler h;
    private final int FRAME_RATE = 30;
    private Random random;


    public AnimatedView2(Context context, AttributeSet attrs)  {
        super(context, attrs);
        mContext = context;
        h = new Handler();
        random = new Random();
        xVelocity = random.nextInt(10);
    }
    private Runnable r = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };
    protected void onDraw(Canvas c) {
        BitmapDrawable ball = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.circle);



        c.drawBitmap(ball.getBitmap(), (int) x, (int) y, null);
        h.postDelayed(r, FRAME_RATE);
    }
}
