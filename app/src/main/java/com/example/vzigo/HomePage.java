package com.example.vzigo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class HomePage extends AppCompatActivity {
    ViewFlipper viewFlipper;
    ImageView qr_code;
    Dialog dialog;
    LinearLayout taptocheckin, taptocheckout;
    TextView feedback, dateandtime;
    String url = "https://forms.gle/jx1YuniuGWSHVX1u9";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        viewFlipper = findViewById(R.id.viewFlipper);
        qr_code = findViewById(R.id.qr_code);
        taptocheckin = findViewById(R.id.tap_to_checkin);
        feedback = findViewById(R.id.Feedback);
        taptocheckout = findViewById(R.id.tap_to_checkout);
        dateandtime = findViewById(R.id.date_and_time);

        taptocheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Checkout1.class);
                startActivity(intent);
            }
        });

        dateandtime.setText("12:01 PM , 20 " + "Jan 2021");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.home_status_color));
        }
        viewFlipper.startFlipping();

        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix matrix = writer.encode(url, BarcodeFormat.QR_CODE,350,350);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            // bitmap.setPixel(0,0, Color.parseColor("8E81BF"));
            qr_code.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }

        dialog = new Dialog(HomePage.this);
        dialog.setContentView(R.layout.checkin_qr_custombox);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),FeedBack.class);
                startActivity(intent);
            }
        });

        LinearLayout qrCheckin = dialog.findViewById(R.id.qrCheckin);
        LinearLayout manualCheckin = dialog.findViewById(R.id.ManualCheckin);
        ImageView close = dialog.findViewById(R.id.close);

        taptocheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();

                qrCheckin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent intent = new Intent(HomePage.this, Qr_Checkin.class);
                        startActivity(intent);
                    }
                });

                manualCheckin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent intent = new Intent(HomePage.this, Manual_Checkin.class);
                        startActivity(intent);
                    }
                });
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        });

    }
}