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
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.HashSet;

public class Manual_Checkin extends AppCompatActivity {
    static Boolean Manualcheck=false;
    LinearLayout takeImage;
    SwitchCompat Switch;
    CheckBox checkBox;
    public static EditText Phoneno, name,date, month, year, address , city, zipcode, reason , govtdoc;
    static String nameFinal;
    Boolean vaccinated;

    Boolean value = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_checkin);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.home_status_color));
        }

        takeImage = findViewById(R.id.takeImage);
        Switch = findViewById(R.id.Switch);
        checkBox = findViewById(R.id.checkbox);
        Phoneno = findViewById(R.id.Phoneno);
        name = findViewById(R.id.Visitorname);
        date = findViewById(R.id.Visitor_dob_date);
        month = findViewById(R.id.Visitor_dob_month);
        year = findViewById(R.id.Visitor_dob_year);
        address = findViewById(R.id.Visitoraddress);
        city = findViewById(R.id.Visior_city);
        zipcode = findViewById(R.id.Visitor_zipcode);
        reason = findViewById(R.id.Visitor_reasontovisit);
        govtdoc = findViewById(R.id.Visitor_govtdoc);


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

                if(checkBox.isChecked())
                {
                    vaccinated = Switch.isChecked();
                    Manualcheck = true;
                    nameShortener();
                 Intent intent = new Intent(getApplicationContext(), CaptureImage.class);
                 startActivity(intent);


                }
                else
                    Toast.makeText(getApplicationContext(), "Please select the box", Toast.LENGTH_SHORT).show();

            }
        });



    }



    private void nameShortener() {
        String temp = name.getText().toString();
        int count=0;
        HashSet<Integer> hs = new HashSet<>();
        for(int i=0;i<temp.length();i++)
        {
            if(temp.charAt(i)==' ') {
                count++;
                hs.add(i);
            }
        }
        if(count>1)
        {
            for(int i=0;i<temp.length();i++)
            {

            }
        }



    }
}