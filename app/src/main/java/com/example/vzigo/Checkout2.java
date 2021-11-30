package com.example.vzigo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

public class Checkout2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout2);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.primary_btn));
        }

        Thread thread = new Thread(){
            public void run(){
                try{
                    sleep(2800);
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