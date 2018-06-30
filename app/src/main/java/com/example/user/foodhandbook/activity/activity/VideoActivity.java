package com.example.user.foodhandbook.activity.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.foodhandbook.R;
import com.example.user.foodhandbook.activity.model.TienIch;
import com.example.user.foodhandbook.activity.ultil.Server;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    public static final String API_KEY = "AIzaSyAYkVlHSjB6wL3J-t_PNLmQPANAcGudBm8";
    private int idmonan = 0;
    private TienIch tienIch = new TienIch();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        // Initializing Youtube player
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        youTubePlayerView.initialize(API_KEY,this);
        GetIdMonAn();
        GetData();

    }
    private void GetIdMonAn() {
        idmonan = getIntent().getIntExtra("idmon", -1);
        //Toast.makeText(getApplicationContext(),idmonan+"", Toast.LENGTH_SHORT).show();
        // Log.d("IDloaisp", idloai + "");
    }
    private void GetData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongan = Server.Duongdantienich;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongan, new Response.Listener<String>() {
            // dùng phương thức này để lấy dữ liệu về
            @Override
            public void onResponse(String response) {
                // tạo biến hứng giá trị
                int idtienich;
                double vido;
                double kinhdo;
                String chude;
                String chuthich;
                String video;
                int id;
                if (response != null&& response.length() != 2) {
                    // có dữ liệu trả về thì thanh progressBar tắt đi
                    try {
                        JSONArray jsonArr = new JSONArray(response);
                        // lấy từng object của Array
                        for (int i = 0; i < jsonArr.length(); i++) {
                            // lấy từng values của từng object
                            JSONObject jsonObject = jsonArr.getJSONObject(i);
                            idtienich = jsonObject.getInt("idtienich");
                            vido = jsonObject.getDouble("vido");
                            kinhdo = jsonObject.getDouble("kinhdo");
                            chude = jsonObject.getString("chude");
                            chuthich = jsonObject.getString("chuthich");
                            video = jsonObject.getString("video");
                            id = jsonObject.getInt("id");
                            // thêm dữ liệu vào mảng
                            tienIch = new TienIch(idtienich,vido,kinhdo,chude,chuthich,video,id);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            // phương thức dùng để đẩy dữ liệu lên server
            // dưới dạng 1 HashMap
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // HashMap<Key,value>
                HashMap<String, String> param = new HashMap<String, String>();
                // đưa giá trị của idloai lên server
                param.put("id", String.valueOf(idmonan));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        // add listeners to YoutubePlayer instance
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEvenListener);
        // start bufering
        if(!wasRestored){
            youTubePlayer.cueVideo(tienIch.getVideo());
            youTubePlayer.setFullscreen(true);
        }
    }
    private YouTubePlayer.PlaybackEventListener playbackEvenListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };
    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show();
    }
}
