package com.example.vzigo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeedBack extends AppCompatActivity {
    LinearLayout submitFeedback;
    EditText feedback;
    RatingBar ratingBar;
    ImageView back;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.home_status_color));
        }
        back = findViewById(R.id.back);
        submitFeedback = findViewById(R.id.submitFeedback);
        feedback = findViewById(R.id.feedback);
        ratingBar = findViewById(R.id.ratingBar);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),HomePage.class);
                startActivity(intent);
            }
        });

        submitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag++;
                if (flag == 1) {
                    ApiInterface apiInterface;
                    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.vzigo.com/visitorservice/").addConverterFactory(GsonConverterFactory.create()).build();
                    apiInterface = retrofit.create(ApiInterface.class);

                    Feedbacks feedbacks = new Feedbacks(ratingBar.getRating(), feedback.getText().toString());

                    String device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    SplashScreen.accessToken = sharedPreferences.getString("accessToken", "");


                    Call<ResponseBody> call = apiInterface.feedbacks(device_id, SplashScreen.accessToken, feedbacks);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            flag=0;
                            //Toast.makeText(getApplicationContext(), "Response Code: " + response.code(), Toast.LENGTH_SHORT).show();
                            if (response.code() == 201) {
                                Intent intent = new Intent(getApplicationContext(), Feedback_splash.class);
                                startActivity(intent);
                            } else if (response.code() == 403) {
                                Toast.makeText(getApplicationContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Subscription.class);
                                startActivity(intent);
                            } else if (response.code() == 404) {
                                Toast.makeText(getApplicationContext(), "Subscription Not Found", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Feedback is processing", Toast.LENGTH_SHORT).show();
                }
            }







            }
        );


    }
}