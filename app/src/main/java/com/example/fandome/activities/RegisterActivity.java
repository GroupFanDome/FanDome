package com.example.fandome.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.fandome.R;

/*
 * A register screen that offers user registration through user's provided information
 */
public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
}