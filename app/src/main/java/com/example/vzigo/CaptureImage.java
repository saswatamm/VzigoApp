package com.example.vzigo;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CaptureImage extends AppCompatActivity {

    public static Bitmap capture;
    static String encodedimage;
    static File finalFile;
    ApiInterface apiInterface;
    static Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image);

        Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                //Toast.makeText(getApplicationContext(), "Permission Accepted", Toast.LENGTH_SHORT).show();
                //Log.e("VisitorId", Manual_Checkin.visitorId);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                startActivityForResult(intent, 100);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            try {
                //Toast.makeText(getApplicationContext(), "OnActivityStart", Toast.LENGTH_SHORT).show();
                capture = (Bitmap) data.getExtras().get("data");
                //Toast.makeText(getApplicationContext(), "Capture:" + capture, Toast.LENGTH_SHORT).show();
                Uri tempUri = getImageUri(getApplicationContext(), capture);
                finalFile = new File(getRealPathFromURI(tempUri));
                //Toast.makeText(getApplicationContext(), "PhotoCaptured", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                Toast.makeText(getApplicationContext(), "Error:" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
            finally
            {
                Intent intent = new Intent(getApplicationContext(), Loading_anim2.class);
                startActivity(intent);
            }
            //Toast.makeText(getApplicationContext(), "Photo Captured", Toast.LENGTH_SHORT).show();


        }
        else
        {
            //Toast.makeText(getApplicationContext(), "Error after image captured", Toast.LENGTH_SHORT).show();
        }


    }




    /*public void encodebitmap(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] byteofImages = byteArrayOutputStream.toByteArray();
        encodedimage = android.util.Base64.encodeToString(byteofImages, Base64.DEFAULT);
        uploadToServer();



    }*/





    public Uri getImageUri(Context inContext, Bitmap inImage) {

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            //Toast.makeText(getApplicationContext(), "Version" + Build.VERSION.SDK_INT, Toast.LENGTH_SHORT).show();

            String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, UUID.randomUUID().toString() + ".jpeg", null);


        if(path==null || path.equals(""))
        {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q)
            {
                try {
                    ContentResolver resolver = getContentResolver();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Image_" + ".jpg");
                    contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
                    contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.pathSeparator + "TestFolder");
                    Uri imagePath = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                    //Toast.makeText(getApplicationContext(), "Image uri + " + imagePath, Toast.LENGTH_SHORT).show();
                    return imagePath;
                }
                catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

            }
            else
            {
                Toast.makeText(getApplicationContext(), "Version Not Supported" + Build.VERSION.SDK_INT, Toast.LENGTH_SHORT).show();
            }
        }
        //if (path != null)
        //Toast.makeText(getApplicationContext(), "Image uri + " + path, Toast.LENGTH_SHORT).show();
            return Uri.parse(path);


    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
      //  Intent intent = new Intent(getApplicationContext(), )
        //Intent intent = new Intent(getApplicationContext(),)
    }
    /*    public void uploadToServer()
    {
        String url = "https://api.vzigo.com/visitorservice/visitors/" + Manual_Checkin.visitorId + "/image";
        Log.e("VisitorId", Manual_Checkin.visitorId);

        StringRequest request  = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "File uploaded successfully", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", String.valueOf(error.getMessage()));
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                String device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                params.put("DeviceId", device_id);
                params.put("Authorization", SplashScreen.accessToken);
                params.put("Content-Type","multipart/form-data");
                params.put("file",encodedimage);
                return params;
            }

        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);


        ExampleThread1 ex = new ExampleThread1(getApplicationContext());
        ex.start();

        try{
            Thread.sleep(2000);
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "e: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
        finally {
            Intent intent = new Intent(getApplicationContext(),DigitalPass.class);
            startActivity(intent);
        }


    }*/
}
class ExampleThread2 extends Thread {
    Context context;

    ExampleThread2(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        //RequestQueue queue = Volley.newRequestQueue(context);
        try {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), CaptureImage.finalFile);
            MultipartBody.Part requestImage = MultipartBody.Part.createFormData("file", CaptureImage.finalFile.getName(), requestFile);
            String url = "https://api.vzigo.com/visitorservice/visitors/" + Manual_Checkin.visitorId + "/";


            Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);


            String device_id1 = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            Log.e("DeviceId", device_id1);
            Log.e("Access", SplashScreen.accessToken);
            Call<ResponseBody> call = apiInterface.sendImage(device_id1, SplashScreen.accessToken, "multipart/form-data", requestImage);
            //Toast.makeText(getApplicationContext(), "Calling", Toast.LENGTH_SHORT).show();
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    //Toast.makeText(getApplicationContext(),  + response.code() + " " + response.message(), Toast.LENGTH_SHORT).show();
                    if (response.code() == 200) {
                        CaptureImage.intent = new Intent(context, DigitalPass.class);

                    } else if (response.code() == 404) {
                        Toast.makeText(context, "Visitor Not Found", Toast.LENGTH_SHORT).show();
                        CaptureImage.intent = new Intent(context, HomePage.class);

                    } else if (response.code() == 500) {
                        Toast.makeText(context, "Visitor is already CHECK_IN", Toast.LENGTH_SHORT).show();
                        CaptureImage.intent = new Intent(context, HomePage.class);
                    } else {
                        Toast.makeText(context, "Error Code:" + response.code(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(context, "Error:" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("Error:", t.getLocalizedMessage());
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(context, "Error:" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }














    }
}

