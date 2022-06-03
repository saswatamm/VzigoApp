package com.example.vzigo;

import static android.content.Context.MODE_PRIVATE;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class HomePage extends AppCompatActivity {
    ViewFlipper viewFlipper;
    ImageView qr_code;
    Dialog dialog;
    LinearLayout taptocheckin, taptocheckout;
    static Boolean dob,add,city,zipcode,purpose,govt,isCov,email,person,gender,digSig;
    static String companyName1,logo;
    static TextView companyName;
    static TextView feedback, dateandtime,headerText;
    ImageView logoCompany;
    static TextView message1,message2,message3,CompanyNameinFlipper1,CompanyNameinFlipper2,CompanyNameinFlipper3;
    static String msg1,msg2,msg3,companyNameformessages,heading1,heading2,heading3;
    static Boolean printDigitalCard, photoCapture;
    static String link;
    static Bitmap ImageResponse;
    ImageView vzigoLogo;


    ShimmerFrameLayout layout;
    Handler handler;




    String url = "https://www.console.vzigo.com/visitors/check-in?clientId="+SplashScreen.ClientId;
    String url1 = "https://api.vzigo.com/visitorservice/visitors/configs";
    //https://api.vzigo.com/visitorservice/visitors/check-in?clientId={clientid}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);



        headerText = findViewById(R.id.headerTitle);


        String t = new SimpleDateFormat("hh.mm a", Locale.getDefault()).format(new Date());
        String d = new SimpleDateFormat("dd.MM.yy", Locale.getDefault()).format(new Date());


        if(t.charAt(6)=='p')
        {
            t = t.substring(0,2);
            int m = Integer.parseInt(t);
            if(m==12||m==1||m==2||m==3||m==4||m==5)
            headerText.setText("Good Afternoon");
            else if(m==6||m==7||m==8||m==9)
                headerText.setText("Good Evening");
            else if(m==10||m==11)
                headerText.setText("Good Night");

        }
        else
        {
            t = t.substring(0,2);
            int m = Integer.parseInt(t);
            if(m==1||m==2||m==3||m==4)
            headerText.setText("Good Night");
            else
                headerText.setText("Good Morning");

        }
        String months[] = {"Jan","Feb","March", "April","May","June","July", "August","Sept","Oct","Nov","Dec"};

        String month1 = d.substring(3,5);
        int mon = Integer.parseInt(month1);
        String month12 = months[mon-1];
        String finalDate = d.substring(0,2) + " " + month12 + " " + "20" + d.substring(6,8);


        //Toast.makeText(getApplicationContext(), "Print" + HomePage.printDigitalCard, Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MySharedPref1", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("company", companyName1);
        myEdit.putString("msg1", msg1);
        myEdit.putString("msg2", msg2);
        myEdit.putString("msg3", msg3);



        myEdit.commit();

        viewFlipper = findViewById(R.id.viewFlipper);
        qr_code = findViewById(R.id.qr_code);
        taptocheckin = findViewById(R.id.tap_to_checkin);
        feedback = findViewById(R.id.Feedback);
        taptocheckout = findViewById(R.id.tap_to_checkout);
        dateandtime = findViewById(R.id.date_and_time);
        companyName = findViewById(R.id.companyname);
        logoCompany = findViewById(R.id.icon);

        message1 = findViewById(R.id.message1);
        message2 = findViewById(R.id.message2);
        message3 = findViewById(R.id.message3);

        CompanyNameinFlipper1 = findViewById(R.id.CompanyNameinFlipper1);
        CompanyNameinFlipper2 = findViewById(R.id.CompanyNameinFlipper2);
        CompanyNameinFlipper3 = findViewById(R.id.CompanyNameinFlipper3);

        CompanyNameinFlipper1.setText(HomePage.heading1);
        CompanyNameinFlipper2.setText(HomePage.heading2);
        CompanyNameinFlipper3.setText(HomePage.heading3);

        //CompanyNameinFlipper1.setText(msg1);
        message1.setText(msg1);
        message2.setText(msg2);
        message3.setText(msg3);

        Log.e("CLIENT ID", SplashScreen.ClientId);



        HomePage.companyName.setText(companyName1);
       try{
        if(HomePage.logo.equals("")){
            logoCompany.setVisibility(View.GONE);}
        else{
            Log.e("LOGO", HomePage.logo);
        logoCompany.setImageBitmap(ImageResponse);}}
       catch(Exception e){
        //Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
           }




        taptocheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Checkout1.class);
                startActivity(intent);
            }
        });









        dateandtime.setText(finalDate);

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

                        try{
                            Thread.sleep(10);

                        }
                        catch(Exception e)
                        {

                        }
                        finally {
                            Intent intent = new Intent(HomePage.this, PagebeforeManual.class);
                            startActivity(intent);
                        }

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
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();


    }
    }


class ExampleThread extends Thread
{
    Context context;

    ExampleThread(Context context)
    {
        this.context = context;
    }
    @Override
    public void run() {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://api.vzigo.com/visitorservice/clients/" + SplashScreen.ClientId + "/configs";


        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        try {

                            if(SplashScreen.ClientId.equals(""))
                            SplashScreen.ClientId = response.getString("clientId");
                            HomePage.companyName1 = response.getJSONObject("branding").getString("clientName");
                            HomePage.logo = response.getJSONObject("branding").getString("logo");
                            Log.e("LOGO", HomePage.logo);

                            HomePage.printDigitalCard = response.getJSONObject("features").getBoolean("printDigitalCard");
                            HomePage.photoCapture = response.getJSONObject("features").getBoolean("photoCapture");


                            HomePage.companyNameformessages = "Welcome to " + HomePage.companyName1;

                            HomePage.msg1 = response.getJSONArray("messages").getJSONObject(0).getString("body");
                            HomePage.heading1 = response.getJSONArray("messages").getJSONObject(0).getString("heading");

                            HomePage.msg2 = response.getJSONArray("messages").getJSONObject(1).getString("body");
                            HomePage.heading2 = response.getJSONArray("messages").getJSONObject(1).getString("heading");

                            HomePage.msg3 = response.getJSONArray("messages").getJSONObject(2).getString("body");
                            HomePage.heading3 = response.getJSONArray("messages").getJSONObject(2).getString("heading");


                            Boolean DigitalSign = response.getJSONObject("visitorForm").getBoolean("digitalSignature");
                            if (!DigitalSign) {
                                HomePage.digSig = false;
                            } else
                                HomePage.digSig = true;
                            // Toast.makeText(getApplicationContext(), ""+response.toString(), Toast.LENGTH_LONG).show();
                            Boolean GovtId = response.getJSONObject("visitorForm").getBoolean("govtId");
                            if (!GovtId) {
                                HomePage.govt = false;
                            } else
                                HomePage.govt = true;
                            Boolean IsCovid = response.getJSONObject("visitorForm").getBoolean("isCovidVaccinated");
                            if (!IsCovid) {
                                HomePage.isCov = false;
                            } else
                                HomePage.isCov = true;

                            Boolean dateofbirth1 = response.getJSONObject("visitorForm").getBoolean("dob");
                            if (!dateofbirth1) {
                                //Toast.makeText(getApplicationContext(), "dob "  + dateofbirth1, Toast.LENGTH_SHORT).show();
                                HomePage.dob = false;
                            } else
                                HomePage.dob = true;
                            Boolean Address1 = response.getJSONObject("visitorForm").getBoolean("address");
                            if (!Address1) {
                                HomePage.add = false;
                            } else
                                HomePage.add = true;
                            Boolean City2 = response.getJSONObject("visitorForm").getBoolean("city");
                            if (!City2) {
                                HomePage.city = false;
                            } else
                                HomePage.city = true;
                            Boolean ZipCode1 = response.getJSONObject("visitorForm").getBoolean("zipCode");
                            if (!ZipCode1) {
                                HomePage.zipcode = false;
                            } else
                                HomePage.zipcode = true;
                            Boolean Purpose = response.getJSONObject("visitorForm").getBoolean("purpose");
                            if (!Purpose) {
                                HomePage.purpose = false;
                            }
                            else
                                HomePage.purpose = true;
                            Boolean email = response.getJSONObject("visitorForm").getBoolean("emailId");
                            if (!email) {
                                HomePage.email = false;
                            } else
                                HomePage.email = true;
                            Boolean person = response.getJSONObject("visitorForm").getBoolean("whomToVisit");
                            if (!person) {
                                HomePage.person = false;
                            } else
                                HomePage.person = true;
                            Boolean gender = response.getJSONObject("visitorForm").getBoolean("gender");
                            if (!gender) {
                                HomePage.gender = false;
                            } else
                                HomePage.gender = true;


                            //Log.d("Response", String.valueOf(response.getJSONObject("visitorForm").getBoolean("dob")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR", "error => " + error.toString());
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

        //if (!HomePage.logo.equals("")) {
            String url1 = "https://api.vzigo.com/visitorservice/clients/" + SplashScreen.ClientId + "/logo";

            ImageRequest stringRequest = new ImageRequest(url1, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    try {
                        HomePage.ImageResponse = response;
                        Log.e("ImageResponse", String.valueOf(response));


                    } catch (Exception e) {
                        Log.e("ErrorNew", e.getLocalizedMessage());
                    }
                }
            }, 0, 0, ImageView.ScaleType.CENTER_CROP, null,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Log.d("ERROR", "error => " + error.toString());
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
                    params.put("Content-Type", "image/jpeg");
                    return params;

                }
            };
            queue.add(stringRequest);
        //} else {

       // }


    }}