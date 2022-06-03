package com.example.vzigo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;


import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Scanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView scannerView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
               scannerView.startCamera();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();




    }

    @Override
    public void handleResult(Result rawResult) {
        String code = rawResult.getText();
        try {
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.vzigo.com/visitorservice/").addConverterFactory(GsonConverterFactory.create()).build();
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            String device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            LinkDevices linkDevices = new LinkDevices(device_id, code);
            Call<LinkDevices1> call = apiInterface.linkdevices(linkDevices);


            call.enqueue(new Callback<LinkDevices1>() {
                @Override
                public void onResponse(Call<LinkDevices1> call, Response<LinkDevices1> response) {

                    try {
                        if (response.code() == 404) {
                            // Make a new screen like QR or Manual Screen
                            Toast.makeText(getApplicationContext(), "Please scan the correct qrcode", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else if (response.code() == 200) {

                            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            myEdit.putString("accessToken", response.body().getAccessToken());
                            myEdit.putString("ClientId",response.body().getClientId());
                            //myEdit.commit();
                            //SplashScreen.accessToken = response.body().getAccessToken();
                            SplashScreen.ClientId = response.body().getClientId();
                            SplashScreen.accessToken = response.body().getAccessToken();
                            myEdit.commit();
                            //
                            Intent intent = new Intent(getApplicationContext(), Loading_anim.class);
                            startActivity(intent);
                        }
                        else
                        {
                            //Toast.makeText(getApplicationContext(), "Please enter the correct passcode", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Please scan the correct qrcode", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LinkDevices1> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "error:" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }





    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();

    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }
}