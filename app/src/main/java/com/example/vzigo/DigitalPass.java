package com.example.vzigo;

import static java.time.LocalDate.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class DigitalPass extends AppCompatActivity {
    ImageView imageView, qrCodePass;
    TextView name, phone,date,time,close,companyName;
    Dialog dialog;
    LinearLayout exit,print;
    TextView close_dialog;
    ImageView icon;
    //String url = "https://www.console.vzigo.com/visitors/" + Manual_Checkin.visitorId + "/card?clientId=" + SplashScreen.ClientId;
    String url = "https://www.console.vzigo.com/card?clientId=" + SplashScreen.ClientId + "&visitorId=" + Manual_Checkin.visitorId;
    static String nameofVisitor,phoneofvisitor,visitorId;

    int pagewidth = 153;
    ShapeableImageView VisitorImage;
    RelativeLayout relativeLayout,finalLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_pass);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.home_status_color));
        }





        VisitorImage = findViewById(R.id.userPic);
        print = findViewById(R.id.print);
        icon = findViewById(R.id.icon1);
        imageView = findViewById(R.id.userPic);
        imageView.setImageBitmap(CaptureImage.capture);
        qrCodePass = findViewById(R.id.qr_code_pass);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        close = findViewById(R.id.close);
        companyName = findViewById(R.id.companyname);


        dialog = new Dialog(DigitalPass.this);
        dialog.setContentView(R.layout.digitalpass_dialogbox);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        exit = dialog.findViewById(R.id.exit);
        close_dialog = dialog.findViewById(R.id.cancel);


        String t = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
        String d = new SimpleDateFormat("dd.MM.yy", Locale.getDefault()).format(new Date());

        companyName.setText(HomePage.companyName1);
        if(HomePage.logo.equals(""))
        {
            icon.setVisibility(View.GONE);
        }
        else {
            //Change Homepage.logo to the url of logo
            icon.setImageBitmap(HomePage.ImageResponse);
        }

        if(HomePage.printDigitalCard)
        {
           print.setVisibility(View.VISIBLE);
        }
        else
        {
            print.setVisibility(View.GONE);
        }

        if(!HomePage.photoCapture){
            VisitorImage.setVisibility(View.GONE);
        }



        /*Details filled from manual login*/
            String phoneNo="";




            phoneNo = "+" + DigitalPass.phoneofvisitor;




            name.setText(DigitalPass.nameofVisitor);
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

        LinearLayout print = findViewById(R.id.print);
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ActivityCompat.requestPermissions(DigitalPass.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
                //createPdf();

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


    public void createPdf(){
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Paint tittlePaint = new Paint();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(153,243,1).create();
        PdfDocument.Page Page = pdfDocument.startPage(pageInfo);
        Canvas canvas = Page.getCanvas();

        tittlePaint.setTextAlign(Paint.Align.CENTER);
        tittlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        tittlePaint.setTextSize(50);
        canvas.drawText(HomePage.companyName1, pagewidth/2,50, tittlePaint);

        pdfDocument.finishPage(Page);

        String filePath = Environment.getExternalStorageDirectory().getPath()+"/myPdf.pdf";
        File file = new File(filePath);
        Log.e("Filepath", filePath);
        try{
            pdfDocument.writeTo(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pdfDocument.close();


    }




}

class ExampleThread1 extends Thread {
    Context context;

    ExampleThread1(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        RequestQueue queue = Volley.newRequestQueue(context);

        String url = "https://api.vzigo.com/visitorservice/visitors/" + Manual_Checkin.visitorId;




        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            DigitalPass.nameofVisitor = response.getString("name");
                            DigitalPass.phoneofvisitor = response.getString("phone");
                            DigitalPass.visitorId = response.getString("visitorId");
                            Log.e("TAG", DigitalPass.visitorId);

                        } catch (JSONException e) {
                            Toast.makeText(context.getApplicationContext(), "Catch" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            Log.e("DigitalPass", e.getLocalizedMessage());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR", "error => " + error.toString());
                        Toast.makeText(context, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String device_id = Settings.Secure.getString(context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                params.put("DeviceId", device_id);
                params.put("Authorization", SplashScreen.accessToken);

                return params;

            }
        };
        queue.add(getRequest);

    }
}