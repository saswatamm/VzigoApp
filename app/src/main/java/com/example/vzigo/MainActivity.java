package com.example.vzigo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.provider.Settings.Secure;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    LinearLayout openCodeScanner;
    public static String password="12345678";
    static TextView wrongpasstext;
    static String android_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.signupback));
        }
        android_id = Secure.getString(getApplicationContext().getContentResolver(),
                Secure.ANDROID_ID);



        wrongpasstext = findViewById(R.id.wrong_pass_text);
        LinearLayout enter = findViewById(R.id.enter_a_code);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheet bottomSheet = new BottomSheet();
                bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
            }
        });
        openCodeScanner = findViewById(R.id.openCodeScanner);
        openCodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, Scanner.class);
                startActivity(intent);
            }
        });
    }
}