package com.as43.dawidjk2.globalgpspong;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MenuActivity extends AppCompatActivity {

    Boolean musicState = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Music.SoundPlayer(this, R.raw.bensoundhouse);
        Button trackBall = findViewById(R.id.track);
        Button signin = findViewById(R.id.signin);
        Button toggleSound = findViewById(R.id.sound);
        final Context context = getApplicationContext();

        final TextView east_score = findViewById(R.id.east_score);
        final TextView west_score = findViewById(R.id.west_score);
        final TextView user_score = findViewById(R.id.user_score);


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

        String url = "http://172.104.23.124:7877/api/getCoastScore";
        Log.d("user_token", LoginActivity.USER_TOKEN);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getInt("code") == 200){
                        JSONArray data = response.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject objects = data.getJSONObject(i);
                            if (objects.getString("coast").equals("east")) {
                                east_score.setText("East Coast Score: " + objects.getString("score"));
                            }
                            if (objects.getString("coast").equals("west")) {
                                west_score.setText("West Coast Score: " + objects.getString("score"));
                            }
                            if (objects.getString("coast").equals("user")) {
                                user_score.setText("User Total Hits: " + objects.getString("score"));
                            }
                        }
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

        RequestSingleton.getInstance(MenuActivity.this).addToRequestQueue(jsonRequest);

        url = "http://172.104.23.124:7877/api/getUserScore?userId=" + LoginActivity.APPLICATION_USER_ID;
        Log.d("user_token", LoginActivity.USER_TOKEN);

        JsonObjectRequest jsonRequest1 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getInt("code") == 200){
                        JSONObject data = response.getJSONObject("data");
                        user_score.setText("User Total Hits: " + data.getString("score"));
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

        RequestSingleton.getInstance(MenuActivity.this).addToRequestQueue(jsonRequest1);

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
