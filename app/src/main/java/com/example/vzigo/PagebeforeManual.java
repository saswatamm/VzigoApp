package com.example.vzigo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

public class PagebeforeManual extends AppCompatActivity {
    static EditText ph;
    static  CountryCodePicker ccp;
    LinearLayout btn, background;
    static String no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagebefore_manual);
        ph = findViewById(R.id.Phoneno);
        ccp = findViewById(R.id.ccp);
        btn = findViewById(R.id.proceed);
        ImageView backImage = findViewById(R.id.backImage);
       ProgressBar pgsBar = (ProgressBar) findViewById(R.id.pBar);
       LinearLayout progress = findViewById(R.id.progressBar);
       background = findViewById(R.id.background);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.home_status_color));
        }

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int flag=0;
                no = ccp.getSelectedCountryCode() + "" + ph.getText().toString();

                if (ph.getText().toString().length() == 0) {
                    ph.setError("Please enter correct phoneno");
                    flag = 1;
                }
                if(flag==0){

                background.setVisibility(View.VISIBLE);
                progress.setVisibility(View.VISIBLE);
                pgsBar.setVisibility(View.VISIBLE);

                FindValues fd = new FindValues(getApplicationContext(),no);
                no = ccp.getSelectedCountryCode() + "" + ph.getText().toString();
                fd.Values();
                Thread thread = new Thread(){
                    public void run(){
                        try{
                            sleep(3500);
                        }
                        catch (Exception e)
                        {

                        }
                        finally {
                            Intent intent = new Intent(getApplicationContext(), Manual_Checkin.class);
                            startActivity(intent);
                        }
                    }
                };thread.start();

            }
                else
                {

                }
        }

        }
        );



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), HomePage.class);
        startActivity(intent);
    }
}