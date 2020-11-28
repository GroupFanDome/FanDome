package com.example.fandome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
/*
 * A login screen that offers login via username or email/password
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}