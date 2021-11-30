package com.example.vzigo;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class BottomSheet extends BottomSheetDialogFragment {
    EditText e1,e2,e3,e4,e5,e6,e7,e8;
    LinearLayout next;
    TextView openScanner;
    int digits=0;
    String code;
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

        e1 = view.findViewById(R.id.ed1);
        e2 = view.findViewById(R.id.ed2);
        e3 = view.findViewById(R.id.ed3);
        e4 = view.findViewById(R.id.ed4);
        e5 = view.findViewById(R.id.ed5);
        e6 = view.findViewById(R.id.ed6);
        e7 = view.findViewById(R.id.ed7);
        e8 = view.findViewById(R.id.ed8);
        next = view.findViewById(R.id.next);

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

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    code = e1.getText().toString() + e2.getText().toString() + e3.getText().toString() + e4.getText().toString() + e5.getText().toString() + e6.getText().toString() + e7.getText().toString() + e8.getText().toString();

                    if(code.equals(MainActivity.password))
                    {
                        Intent intent = new Intent(getActivity(), HomePage.class);
                        startActivity(intent);
                    }

                else
                    Toast.makeText(getActivity(), "Please enter all the digits", Toast.LENGTH_SHORT).show();

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