package com.example.vzigo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class SplashScreen extends AppCompatActivity {

    static String accessToken="";
    RelativeLayout splashBackground;
    VideoView videoView;
    ImageView imageView;
    static String ClientId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        splashBackground = findViewById(R.id.SplashBackground);
        videoView = findViewById(R.id.videoView);
        imageView = findViewById(R.id.VzigoLogo);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SplashScreen.accessToken = sharedPreferences.getString("accessToken","");
        SplashScreen.ClientId = sharedPreferences.getString("ClientId","");

        Log.e("AccessToken", SplashScreen.accessToken);

        ExampleThread exampleThread = new ExampleThread(getApplicationContext());
        exampleThread.start();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.splash_screen_color));
        }



        String path = "android.resource://com.example.vzigo/" + R.raw.vzigo;

        Uri uri = Uri.parse(path);
        videoView.setVideoURI(uri);

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            connected = true;
        }
        else
            connected = false;

        if(!connected)
        {
            Toast.makeText(getApplicationContext(), "Please connect to Internet", Toast.LENGTH_SHORT).show();
            finishAndRemoveTask();
        }








        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                imageView.setVisibility(View.VISIBLE);


                if(accessToken.equals(""))
                {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else
                {


                    Thread thread = new Thread(){
                        public void run(){
                            try{
                                sleep(2600);
                            }
                            catch (Exception e)
                            {

                            }
                            finally {
                                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                                startActivity(intent);
                            }
                        }
                    };thread.start();

                }

            }
        });



    }
}

