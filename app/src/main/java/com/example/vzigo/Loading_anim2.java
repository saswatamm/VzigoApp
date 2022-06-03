package com.example.vzigo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

public class Loading_anim2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_anim2);
        //Toast.makeText(getApplicationContext(), "PhotoCapture Value" + HomePage.photoCapture, Toast.LENGTH_SHORT).show();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }

        if (HomePage.photoCapture) {
           // Toast.makeText(getApplicationContext(), "Loading Screen",Toast.LENGTH_SHORT).show();
            try {
                Thread thread2 = new Thread(){
                    public void run(){
                        try{
                        ExampleThread2 ex = new ExampleThread2(getApplicationContext());
                        ex.start();
                        sleep(3500);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(), "Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }

                    }

                };
                thread2.start();

            }
            catch(Exception e)
            {
                Toast.makeText(getApplicationContext(), "Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
            finally {
                Thread thread1 = new Thread() {
                    public void run() {
                        try {
                            ExampleThread1 ex = new ExampleThread1(getApplicationContext());
                            ex.start();
                            sleep(3500);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error:" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        } finally {
                            if(CaptureImage.intent!=null)
                            startActivity(CaptureImage.intent);
                            //else
                                //Toast.makeText(getApplicationContext(), "Error: intent empty", Toast.LENGTH_SHORT).show();

                        }
                    }
                };
                thread1.start();
            }
        }
        if (!HomePage.photoCapture) {
            Thread thread = new Thread() {
                public void run() {
                    try {
                        ExampleThread1 ex = new ExampleThread1(getApplicationContext());
                        ex.start();
                        sleep(4000);
                    } catch (Exception e) {

                    } finally {


                        Intent intent = new Intent(getApplicationContext(), DigitalPass.class);
                        startActivity(intent);

                    }
                }
            };
            thread.start();

        }
    }
}

