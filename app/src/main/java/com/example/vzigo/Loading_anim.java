package com.example.vzigo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class Loading_anim extends AppCompatActivity {

    private static final String TAG = "Loading_anim";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_anim);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }

        ExampleThread exampleThread = new ExampleThread(getApplicationContext());
        exampleThread.start();


        Thread thread = new Thread(){
            public void run(){
                try{
                    sleep(5000);
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