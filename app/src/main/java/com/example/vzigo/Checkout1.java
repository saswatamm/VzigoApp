package com.example.vzigo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Checkout1 extends AppCompatActivity  implements ZXingScannerView.ResultHandler{
    ZXingScannerView scannerView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        //setContentView(R.layout.activity_scanner);
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
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler((ZXingScannerView.ResultHandler) this);
        scannerView.startCamera();

    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {

        //Taking out the url
        String latestUrl = rawResult.toString();
        int i=0;
        String code = "";
        for(int j=latestUrl.length()-1;j>=0;j--)
        {
            if(latestUrl.charAt(j)=='=')
            {
                i=j;
                break;
            }
        }
        String url =  "https://api.vzigo.com/visitorservice/visitors/" + latestUrl.substring(i+1,latestUrl.length())+"/";
        //Toast.makeText(getApplicationContext(), "Url" + url, Toast.LENGTH_LONG).show();






        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        //String url = "https://api.vzigo.com/visitorservice/visitors/" + rawResult.toString() + "/";
        String device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.e("ATG", device_id +"  " + SplashScreen.accessToken);











        Log.e("TAG", url);
        ApiInterface apiInterface;
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(ApiInterface.class);



        //String device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
               // Settings.Secure.ANDROID_ID);

        Call<ResponseBody> call = apiInterface.checkout(device_id, SplashScreen.accessToken);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                if(response.code()==404)
                {
                    Toast.makeText(getApplicationContext(), "Visitor Not Found", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),HomePage.class);
                    startActivity(intent);
                }
                else if(response.code()==204)
                {
                    Intent intent = new Intent(getApplicationContext(),Checkout2.class);
                    startActivity(intent);

                }
                else if(response.code()==403)
                {
                    Toast.makeText(getApplicationContext(), response.code() + "Body" + response.body(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),Subscription.class);
                    startActivity(intent);
                }
                else if(response.code()==500)
                {
                    Toast.makeText(getApplicationContext(), "Could not update visitor.Wrong status ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),HomePage.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });



    }
}
