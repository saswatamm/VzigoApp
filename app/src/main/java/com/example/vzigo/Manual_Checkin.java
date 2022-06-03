package com.example.vzigo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
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
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Manual_Checkin extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView phoneno;
    static Boolean Manualcheck = false;
    LinearLayout takeImage;
    static SwitchCompat Switch;
    static CheckBox checkBox;
    public static EditText Phoneno, name, date, month, year, address, city, zipcode, reason, govtdoc,email,persontovisit;
    static LinearLayout dateofbirth, AddressBox, City, ZipCode, PurposeOfVisit, GovernmentId, CovidVaccinated,Email,Gender,PersonToVisit,DigitalSignature;
    TextView takeImageText;
    String ph ;
    String name1 ;
    String dateofbirth1 ;
    String address1 ;
    String zipcode1 ;
    String city1 ;
    String purpose ;
    String govtId ;
    boolean covid ;
    static String nameFinal,visitorId;
    Boolean vaccinated;
    ImageView backImage;
    static Spinner gender;
    CountryCodePicker ccp;
    static String PhoneCode;
    static String GenderValue="";
    Boolean value = false;
    //int flag=0;
    int clicked = 0;
    ImageView vector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_checkin);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.home_status_color));
        }
        phoneno = findViewById(R.id.Phoneno);

        phoneno.setText("+" + PagebeforeManual.no);


        backImage = findViewById(R.id.backImage);
       // vector = findViewById(R.id.vector);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PagebeforeManual.class);
                startActivity(intent);
            }
        });
        //ccp = findViewById(R.id.ccp);


        takeImageText = findViewById(R.id.TakeImageText);

         if(!HomePage.photoCapture)
         {
             takeImageText.setText("SUBMIT");
         }


        DigitalSignature = findViewById(R.id.DigitalSignature);
        takeImage = findViewById(R.id.takeImage);
        Switch = findViewById(R.id.Switch);
        checkBox = findViewById(R.id.checkbox);
        //Phoneno = findViewById(R.id.Phoneno);
        name = findViewById(R.id.Visitorname);
        date = findViewById(R.id.Visitor_dob_date);
        month = findViewById(R.id.Visitor_dob_month);
        year = findViewById(R.id.Visitor_dob_year);
        address = findViewById(R.id.Visitoraddress);
        city = findViewById(R.id.Visitor_city);
        zipcode = findViewById(R.id.Visitor_zipcode);
        reason = findViewById(R.id.Visitor_reasontovisit);
        govtdoc = findViewById(R.id.Visitor_govtdoc);
        dateofbirth = findViewById(R.id.dateofbirth);
        City = findViewById(R.id.City);
        GovernmentId = findViewById(R.id.GovernmentId);
        CovidVaccinated = findViewById(R.id.CovidVaccinated);
        PurposeOfVisit = findViewById(R.id.PurposeOfVisit);
        AddressBox = findViewById(R.id.Address);
        ZipCode = findViewById(R.id.ZipCode);
        persontovisit = findViewById(R.id.VisitorPersontovisit);
        PersonToVisit = findViewById(R.id.PersontoVisit);
        //gender = findViewById(R.id.Visitor_Gender);
        Gender = findViewById(R.id.Gender);
        email = findViewById(R.id.Visitor_email);
        Email = findViewById(R.id.email);
        gender = findViewById(R.id.Spinner);


        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(arrayAdapter) ;
        gender.setOnItemSelectedListener(this);




        name.setText(FindValues.name);
        //Toast.makeText(getApplicationContext(), "Name:" + FindValues.name, Toast.LENGTH_SHORT).show();
       if(!HomePage.digSig){
           DigitalSignature.setVisibility(View.GONE);
       }

        if(!HomePage.city) {
            City.setVisibility(View.GONE);


        }
        else
        {
            city.setText(FindValues.city);
        }
        try {
            if(!HomePage.dob){
                dateofbirth.setVisibility(View.GONE);
            }
            else
            {
                              if(FindValues.dateofbirth.length()!=0)
                            {
                                Manual_Checkin.date.setText(FindValues.dateofbirth.substring(0,2));
                                Manual_Checkin.month.setText(FindValues.dateofbirth.substring(1,3));
                                Manual_Checkin.year.setText(FindValues.dateofbirth.substring(4,8));
                            }

            }

        }
        catch (Exception e)
        {
            //Toast.makeText(getApplicationContext(), "dob" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }


       try {
           if (!HomePage.govt) {
               GovernmentId.setVisibility(View.GONE);
           }
           else
           {
               govtdoc.setText(FindValues.govtId);
           }
       }
       catch (Exception e)
       {
          // Toast.makeText(getApplicationContext(), "govt" + e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
       }

           if (!HomePage.isCov) {
               CovidVaccinated.setVisibility(View.GONE);
           }
           else
           {
               Switch.setChecked(FindValues.cov);
           }
        try {
           if (!HomePage.purpose) {
               PurposeOfVisit.setVisibility(View.GONE);
           }

           else
           {
               reason.setText(FindValues.purpose);
           }
           if (!HomePage.add) {
               AddressBox.setVisibility(View.GONE);
           }
           else
           {
              // Toast.makeText(getApplicationContext(), "Address " + FindValues.address, Toast.LENGTH_SHORT).show();
               address.setText(FindValues.address);
           }
           if (!HomePage.zipcode) {
               ZipCode.setVisibility(View.GONE);
           }
           else
           {
             //  Toast.makeText(getApplicationContext(), "Zipcode" + FindValues.zipcode, Toast.LENGTH_SHORT).show();
               zipcode.setText(FindValues.zipcode);
           }
           if(!HomePage.email)
           {
               Email.setVisibility(View.GONE);
           }
           else
           {
               email.setText(FindValues.email);
           }
           if(!HomePage.gender)
           {
               Gender.setVisibility(View.GONE);
           }
           else
           {
               String gen = FindValues.gender;
               int genId=0;
               if(gen.equals("Male")){
                   genId = 1; }

               else if(gen.equals("Female")) {
                   genId = 2;
               }
               else if(gen.equals("Others"))
                   genId = 3;
               gender.setSelection(genId);
           }
           if(!HomePage.person){
               PersonToVisit.setVisibility(View.GONE);
           }
           else
           {
               persontovisit.setText(FindValues.person);
           }
       }
       catch (Exception e)
       {
           //Toast.makeText(getApplicationContext(), "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
       }



        Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();




        takeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int flag=0;
                //Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();



                    Boolean CheckBox = HomePage.digSig;
                    if (CheckBox) {
                        CheckBox = checkBox.isChecked();
                    } else {
                        CheckBox = true;
                    }
                    if (CheckBox) {
                        if (HomePage.isCov)
                            vaccinated = Switch.isChecked();
                        //PhoneCode = ccp.getSelectedCountryCode();

                        Manualcheck = true;
                        nameShortener();

                        String url = "https://api.vzigo.com/visitorservice/clients/" + SplashScreen.ClientId + "/";
                        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
                        ApiInterface apiInterface = retrofit.create(ApiInterface.class);


                        String device_id1 = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                                Settings.Secure.ANDROID_ID);
                        Log.e("DeviceId", device_id1);
                        Log.e("Access", SplashScreen.accessToken);


                        String ph = PagebeforeManual.no;
                        String name1 = name.getText().toString();
                    /*String dateofbirth = date.getText().toString() +"/" + month.getText().toString() + "/" + year.getText().toString();
                    String address1 = address.getText().toString();
                    String zipcode = ZipCode.toString();

                    String purpose = reason.toString();
                    String govtId = govtdoc.toString();
                    boolean covid = Switch.isChecked();*/


                        if (name1.length() == 0) {
                            name.setError("Please enter the field");
                            flag = 1;
                        }


                        if (HomePage.city) {
                            city1 = city.getText().toString();
                            if (city1.length() == 0) {
                                city.setError("Please fill the city");
                                flag = 1;
                            }

                        } else
                            city1 = "";
                        if (HomePage.person) {
                            String m = persontovisit.getText().toString();
                            if (m.length() == 0) {
                                persontovisit.setError("Please fill whom to visit");
                                flag = 1;
                            }
                        }


                        if (HomePage.dob) {
                            dateofbirth1 = date.getText().toString() + month.getText().toString() + year.getText().toString();
                            if (dateofbirth1.length() != 8) {
                                if (date.getText().toString().length() != 2)
                                    date.setError("Enter the date");
                                if (month.getText().toString().length() != 2)
                                    month.setError("Enter the month");
                                if (year.getText().toString().length() != 4)
                                    year.setError("Please enter the year");
                                flag = 1;
                            }

                        } else
                            dateofbirth1 = "";


                        if (HomePage.govt) {
                            govtId = govtdoc.getText().toString();
                            if (govtId.length() == 0) {
                                govtdoc.setError("Please enter the field");
                                flag = 1;
                            }

                        } else
                            govtId = "";

                        if (HomePage.isCov)
                            covid = Switch.isChecked();
                        else
                            covid = false;


                        if (HomePage.purpose) {
                            purpose = reason.getText().toString();
                            if (purpose.length() == 0) {
                                reason.setError("Please enter the field");
                                flag = 1;
                            }

                        } else
                            purpose = "";

                        if (HomePage.add) {
                            address1 = address.getText().toString();
                            if (address1.length() == 0) {
                                address.setError("Please enter the field");
                                flag = 1;
                            }

                        }
                        if (HomePage.zipcode) {
                            zipcode1 = zipcode.getText().toString();
                            if (zipcode1.length() == 0) {
                                zipcode.setError("Please enter the field");
                                flag = 1;
                            }

                        }
                        if (HomePage.gender) {
                            if (GenderValue.equals("")) {
                                flag = 1;
                                //GenderValue.("Please enter the field");
                            }
                        }

                        if (HomePage.email) {
                            if (email.getText().toString().equals("")) {
                                flag = 1;
                                email.setError("Please enter the field");
                            }
                        }

                       // Toast.makeText(getApplicationContext(), "Perosn" + persontovisit.getText().toString(), Toast.LENGTH_SHORT).show();


                        String device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                                Settings.Secure.ANDROID_ID);
                        ph = PagebeforeManual.no;
                        name1 = nameShortener();
                        RegisterVisitors registerVisitors = new RegisterVisitors(name1, ph, dateofbirth1, GenderValue, email.getText().toString(), address1, city1, zipcode1, purpose, persontovisit.getText().toString(), govtId, covid, "ACCEPTED");
                        Log.e("F", String.valueOf(flag));
                        if (flag == 0) {
                            try {
                                Call<CheckInresponse> call = apiInterface.checkin(device_id, SplashScreen.accessToken, registerVisitors);
                                call.enqueue(new Callback<CheckInresponse>() {
                                    @Override
                                    public void onResponse(Call<CheckInresponse> call, Response<CheckInresponse> response) {
                                       // Toast.makeText(getApplicationContext(), "Response Code:"+response.code(), Toast.LENGTH_SHORT).show();

                                        if (response.code() == 200) {
                                            Log.e("Code is", String.valueOf(response.code()));
                                            //Toast.makeText(getApplicationContext(), "Code:" + response.code(), Toast.LENGTH_SHORT).show();
                                            try {
                                                visitorId = response.body().getVisitorId();
                                            } catch (Exception e) {
                                                Log.e("Catch", e.getLocalizedMessage());
                                            }

                                            if (HomePage.photoCapture) {
                                                //Toast.makeText(getApplicationContext(), "Photo Capture", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getApplicationContext(), CaptureImage.class);
                                                startActivity(intent);
                                            } else {
                                                Intent intent = new Intent(getApplicationContext(), Loading_anim2.class);
                                                startActivity(intent);
                                            }
                                        } else if (response.code() == 403) {
                                            Intent intent = new Intent(getApplicationContext(), Subscription.class);
                                            startActivity(intent);
                                        } else if (response.code() == 404) {
                                            Toast.makeText(getApplicationContext(), "Subscription not found", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), HomePage.class);
                                            startActivity(intent);
                                        } else if (response.code() == 500) {
                                            Toast.makeText(getApplicationContext(), "Visitor monthly quota exhausted", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), HomePage.class);
                                            startActivity(intent);
                                        }
                                        else
                                            Toast.makeText(getApplicationContext(), "Response : " + response.code() , Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<CheckInresponse> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), "Error is" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } catch (Exception e) {
                                Log.e("TAG", e.getLocalizedMessage());
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Fill all the fields", Toast.LENGTH_SHORT).show();
                        }
                    } else
                        Toast.makeText(getApplicationContext(), "Please select the box", Toast.LENGTH_SHORT).show();
                }





        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), PagebeforeManual.class);
        startActivity(intent);
    }

    private String nameShortener() {
        String s = name.getText().toString();
        s = s.trim();
        int c2=0;
        for(int i=0;i<s.length();i++)
        {
            char c11 = s.charAt(i);
            if(c11==' ')
                c2++;
        }
        if(c2>1) {
            int first = 0, last = 0;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == ' ') {
                    first = i;
                    break;
                }
            }
            for (int i = s.length() - 1; i >= 0; i--) {
                char c = s.charAt(i);
                if (c == ' ') {
                    last = i;
                    break;
                }
            }
            String middleName = "";
            for (int i = 0; i < last; i++) {
                char c = s.charAt(i);
                if (c == ' ') {
                    middleName = middleName + s.charAt(i + 1) + ".";

                }
            }
            middleName = middleName.substring(0, middleName.length() - 1);
            return (s.substring(0, first) + " " + middleName + s.substring(last, s.length()));
        }
        else
        {
            return s;
        }


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(i==0)
        {
           // flag=1;
        }
        else {

            GenderValue = adapterView.getItemAtPosition(i).toString();
            //adapterView.setSelection(2);
            //flag=0;
         }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}


