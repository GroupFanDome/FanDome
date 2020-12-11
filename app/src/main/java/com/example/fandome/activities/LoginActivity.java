package com.example.fandome.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.fandome.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/*
 * A login screen that offers login via username or email/password
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(ParseUser.getCurrentUser() != null){
            Log.i("login", "Logged in as "+ParseUser.getCurrentUser().getUsername());
            goMainActivity();
        }
        loginUser("TheTester","tester1234");
    }

    private void loginUser(final String username, String password) {
        ParseUser.logInInBackground(username, password,new LogInCallback(){

            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null){
                    Log.e("login", "Issue with Login "+e,e);
                    return;
                }
                //if login successful then navigate to main activity
                Log.i("login", "Login Successful");
                goMainActivity();
            }
        });
    }
    private void goMainActivity() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }
}