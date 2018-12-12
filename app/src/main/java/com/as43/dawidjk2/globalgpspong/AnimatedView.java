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

public class AnimatedView extends android.support.v7.widget.AppCompatImageView{
    private Context mContext;
    double x = -1;
    double y = -1;
    private int xVelocity;
    private int yVelocity = 10;
    private Handler h;
    private final int FRAME_RATE = 30;
    private Random random;

    double top = 49.3457868; // # north lat
    double left = -124.7844079; // # west long
    double right = -66.9513812; // # east long
    double bottom =  24.7433195; // # south lat

    double horizontalLength = right - left;
    double verticalLength = top - bottom;

    public AnimatedView(Context context, AttributeSet attrs)  {
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

        String url = "http://172.104.23.124:7877/api/getCoordinates";
        Log.d("user_token", LoginActivity.USER_TOKEN);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getInt("code") == 200){
                        JSONObject data = response.getJSONObject("data");
                        x = (data.getDouble("latitude") - left)  * (getHeight() / horizontalLength);
                        y = (data.getDouble("longitude") - bottom)  * (getWidth() / verticalLength);
                        Log.d("coor-height", String.valueOf(y));
                        Log.d("coor-width", String.valueOf(x));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //TODO: handle failure

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();

                headers.put("x-access-token", LoginActivity.USER_TOKEN);
                return headers;
            }
        };

        RequestSingleton.getInstance(getContext()).addToRequestQueue(jsonRequest);


        c.drawBitmap(ball.getBitmap(), (int) x, (int) y, null);
        h.postDelayed(r, FRAME_RATE);
    }
}
