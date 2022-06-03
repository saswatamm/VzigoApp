package com.example.vzigo;

import static android.content.Context.TELEPHONY_SERVICE;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BottomSheet extends BottomSheetDialogFragment {
    EditText e1,e2,e3,e4,e5,e6,e7,e8;
    LinearLayout next;
    TextView openScanner;
    int digits=0;
    String code;
    ApiInterface apiInterface;
    TelephonyManager telephonyManager;
    LinearLayout exit;
    public BottomSheet() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);


        openScanner = view.findViewById(R.id.openScanner);
        //exit = view.findViewById(R.id.exit);

        e1 = view.findViewById(R.id.ed1);
        e2 = view.findViewById(R.id.ed2);
        e3 = view.findViewById(R.id.ed3);
        e4 = view.findViewById(R.id.ed4);
        e5 = view.findViewById(R.id.ed5);
        e6 = view.findViewById(R.id.ed6);
        e7 = view.findViewById(R.id.ed7);
        e8 = view.findViewById(R.id.ed8);
        next = view.findViewById(R.id.next);
        TextView incorrectCode = view.findViewById(R.id.incorrectCode);

        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               if(!e1.toString().trim().isEmpty()){
                   digits++;
                   e2.requestFocus();
               }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length()==0)
                {
                    e1.requestFocus();
                }
            }
        });

        e2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                   if(!e2.toString().trim().isEmpty()){
                       digits++;
                       e3.requestFocus();
                   }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                  if(editable.toString().length()==0)
                  {
                      e1.requestFocus();
                  }
            }
        });

        e3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!e3.toString().trim().isEmpty()){
                    digits++;
                    e4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(editable.toString().length()==0)
                {
                    e2.requestFocus();
                }

            }
        });

        e4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!e4.toString().trim().isEmpty()){
                    digits++;
                    e5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(editable.toString().length()==0)
                {
                    e3.requestFocus();
                }

            }
        });

        e5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!e5.toString().trim().isEmpty()){
                    digits++;
                    e6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length()==0)
                {
                    e4.requestFocus();
                }

            }
        });

        e6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!e6.toString().trim().isEmpty()){
                    digits++;
                    e7.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length()==0)
                {
                    e5.requestFocus();
                }
            }
        });

        e7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!e7.toString().trim().isEmpty()){
                    digits++;
                    e8.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(editable.toString().length()==0)
                {
                    e6.requestFocus();
                }

            }
        });
        e8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!e8.toString().trim().isEmpty()){


                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(editable.toString().length()==0)
                {
                    e7.requestFocus();
                }

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    code = e1.getText().toString() + e2.getText().toString() + e3.getText().toString() + e4.getText().toString() + e5.getText().toString() + e6.getText().toString() + e7.getText().toString() + e8.getText().toString();

                    try {
                        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.vzigo.com/visitorservice/").addConverterFactory(GsonConverterFactory.create()).build();
                        apiInterface = retrofit.create(ApiInterface.class);
                        String device_id = Settings.Secure.getString(getContext().getContentResolver(),
                                Settings.Secure.ANDROID_ID);
                        LinkDevices linkDevices = new LinkDevices(device_id, code);
                        Call<LinkDevices1> call = apiInterface.linkdevices(linkDevices);


                        call.enqueue(new Callback<LinkDevices1>() {
                            @Override
                            public void onResponse(Call<LinkDevices1> call, Response<LinkDevices1> response) {

                                try {
                                    if (response.code() == 404) {
                                        Toast toast= Toast.makeText(getActivity(),
                                                "Please enter the correct passcode", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.TOP| Gravity.CENTER_HORIZONTAL, 0, 0);
                                        toast.show();
                                        incorrectCode.setVisibility(View.VISIBLE);
                                        // Make a new screen like QR or Manual Screen

                                    } else if (response.code() == 200) {

                                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                        myEdit.putString("accessToken", response.body().getAccessToken());
                                        myEdit.putString("ClientId",response.body().getClientId());
                                        myEdit.commit();
                                        SplashScreen.accessToken = response.body().getAccessToken();
                                        SplashScreen.ClientId = response.body().getClientId();
                                        //Toast.makeText(getActivity(), "Client Id"  + SplashScreen.ClientId, Toast.LENGTH_SHORT).show();

                                        // Change it from homepage to loading page
                                        Intent intent = new Intent(getActivity(), Loading_anim.class);
                                        startActivity(intent);
                                    }
                                    else if(response.code()==500)
                                    {
                                        Toast.makeText(getActivity(), "Cannot link this device, as it is already in use", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getActivity(),MainActivity.class);
                                        startActivity(intent);
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(getActivity(), "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<LinkDevices1> call, Throwable t) {

                            }
                        });
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getActivity(), "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }





        }});

        openScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Scanner.class);
                startActivity(intent);
            }
        });










        return view;
    }




}