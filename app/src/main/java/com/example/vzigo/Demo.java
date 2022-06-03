package com.example.vzigo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Demo extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        TextView name1 ;
        name1 = findViewById(R.id.name);
        FindValues fd = new FindValues(getApplicationContext(),"1234512345");
        fd.Values();
        //name1.setText();

/*        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        String no = "1234512345";
        String normal= "{" + "\"phone\"" + ":" + "\"" + no + "\"" + "}";
        try {
            normal = URLEncoder.encode(normal, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String utf=StringFormatter.convertStringToUTF8(normal);
        String url = "https://api.vzigo.com/visitorservice/visitors?filter=" + utf;
        name1 = findViewById(R.id.name);
        name1.setText(url);




        JsonObjectRequest getRequest1 = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray ar = response.getJSONArray("visitors");
                            JSONObject obj = ar.getJSONObject(0);
                            Log.e("Name" , obj.getString("name"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR",error.getLocalizedMessage());
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                params.put("DeviceId", device_id);
                params.put("Authorization", SplashScreen.accessToken);

                return params;

            }
        };
        queue.add(getRequest1);*/













    }
}
class StringFormatter {

    // convert UTF-8 to internal Java String format
    public static String convertUTF8ToString(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("ISO-8859-1"), "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }

    // convert internal Java String format to UTF-8
    public static String convertStringToUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }

}



class FindValues
{

    static Context context;
    static String no;
    static String name = "";
    static String gender = "";
    static String address="";
    static String dateofbirth="";
    static String email="";
    static String city="";
    static String person="";
    static String zipcode="";
    static String govtId="";
    static String purpose="";
    static Boolean cov=false;
    FindValues(Context context, String no)
    {
        this.context = context;
        this.no = no;
    }
    public static void Values()
    {
        RequestQueue queue = Volley.newRequestQueue(context);

        //String no = "1234512345";
        String normal= "{" + "\"phone\"" + ": " + "\"" + no + "\"" + "}";
        Log.e("Normal", normal);
        try {
            normal = URLEncoder.encode(normal, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String utf=StringFormatter.convertStringToUTF8(normal);
        String utf1="";
        for(int i=0;i<utf.length();i++)
        {
            char c = utf.charAt(i);
            if(c=='+')
                utf1 +="%20";
            else
                utf1 = utf1 + c;
        }
        utf = utf1;
        Log.e("UTF", utf);
        String url = "https://api.vzigo.com/visitorservice/visitors?filter=" + utf;





        JsonObjectRequest getRequest1 = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray ar = response.getJSONArray("visitors");
                            JSONObject obj = ar.getJSONObject(0);
                            Log.e("Name" , obj.getString("name"));
                            //Manual_Checkin.name.setText(obj.getString("name"));
                            name = obj.getString("name");
                           // String gender = obj.getString("gender");
                            gender = obj.getString("gender");
                            //String add = obj.getString("address");
                            address = obj.getString("address");

                            dateofbirth = obj.getString("dateOfBirth");
                            /*if(HomePage.dob)
                            {
                                Manual_Checkin.date.setText(dateOfBirth.substring(0,2));
                                Manual_Checkin.month.setText(dateOfBirth.substring(1,3));
                                Manual_Checkin.year.setText(dateOfBirth.substring(4,8));
                            }*/
                            //if(HomePage.email){
                             email = obj.getString("emailId");
                            //Manual_Checkin.email.setText(email);}
                            /*if(HomePage.digSig)
                            {
                                Manual_Checkin.checkBox.setChecked(true);
                            }*/
                            //if(HomePage.isCov)
                            //{
                                 cov = obj.getBoolean("isCovidVaccinated");
                                //Manual_Checkin.Switch.setChecked(cov);
                            //}
                            //if(HomePage.city)
                           // {
                                 city = obj.getString("city");
                               // Manual_Checkin.city.setText(city);
                            //}
                           // if(HomePage.person)
                            //{
                                 person = obj.getString("whomToVisit");
                            //ToastToast.makeText(context.getApplicationContext(), "Whom " + person, Toast.LENGTH_SHORT).show();
                              //  Manual_Checkin.persontovisit.setText(person);
                           // }
                            //if(HomePage.zipcode)
                           // {
                                 zipcode = obj.getString("zipCode");
                           // Toast.makeText(context.getApplicationContext(), "ZipCode" + zipcode, Toast.LENGTH_SHORT).show();
                                //Manual_Checkin.zipcode.setText(zip);
                            //}
                            // Date of birth ta dekhte hobe

                            //if(HomePage.govt)
                           // {
                                 govtId = obj.getString("govtId");
                                //Manual_Checkin.govtdoc.setText(gov);
                           // }
                            int genId;
                           /* if(HomePage.gender)
                            {
                                String gen = obj.getString("gender");

                                if(gen.equals("Male")){
                                    genId = 1; }

                                else if(gen.equals("Female")) {
                                    genId = 2;
                                }
                                else
                                    genId = 3;
                                Manual_Checkin.gender.setSelection(genId);
                            }*/
                            //if(HomePage.purpose)
                            //{
                                 purpose = obj.getString("purpose");
                                //Manual_Checkin.reason.setText(reason);
                           // }







                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR",error.getLocalizedMessage());
                        Toast.makeText(context, "Phone is not registered", Toast.LENGTH_SHORT).show();
                    }
                })
        {
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
        queue.add(getRequest1);
    }
}