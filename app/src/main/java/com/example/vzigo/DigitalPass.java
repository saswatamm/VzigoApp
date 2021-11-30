package com.example.vzigo;

import static java.time.LocalDate.*;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class DigitalPass extends AppCompatActivity {
    ImageView imageView, qrCodePass;
    TextView name, phone,date,time,close;
    Dialog dialog;
    LinearLayout exit;
    TextView close_dialog;
    String url = "https://forms.gle/jx1YuniuGWSHVX1u9";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_pass);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.home_status_color));
        }

        imageView = findViewById(R.id.userPic);
        imageView.setImageBitmap(CaptureImage.capture);
        qrCodePass = findViewById(R.id.qr_code_pass);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        close = findViewById(R.id.close);


        dialog = new Dialog(DigitalPass.this);
        dialog.setContentView(R.layout.digitalpass_dialogbox);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        exit = dialog.findViewById(R.id.exit);
        close_dialog = dialog.findViewById(R.id.cancel);


        String t = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
        String d = new SimpleDateFormat("dd.MM.yy", Locale.getDefault()).format(new Date());




        /*Details filled from manual login*/


            String phoneNo = "+91 " + Manual_Checkin.Phoneno.getText().toString();
            name.setText(Manual_Checkin.name.getText().toString());
            phone.setText(phoneNo);

        time.setText(t);
        date.setText(d);





        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix matrix = writer.encode(url, BarcodeFormat.QR_CODE, 350, 350);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            qrCodePass.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();

                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(DigitalPass.this, HomePage.class);
                        startActivity(intent);
                    }
                });
                close_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        dialog.show();
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DigitalPass.this, HomePage.class);
                startActivity(intent);
            }
        });
        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}