package com.example.fandome.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fandome.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/*
 * A register screen that offers user registration through user's provided information
 */
public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = "RegisterActivity";
    private EditText firstNameInputBox;
    private EditText lastNameInputBox;
    private EditText usernameInputBox;
    private EditText passwordInputBox;
    private EditText emailInputBox;
    private Button signUpButton;
    private TextView logInText;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_register);

        firstNameInputBox = findViewById(R.id.firstNameInputBox);
        lastNameInputBox = findViewById(R.id.lastNameInputBox);
        usernameInputBox = findViewById(R.id.usernameInputBox);
        passwordInputBox = findViewById(R.id.passwordInputBox);
        emailInputBox = findViewById(R.id.emailInputBox);
        logInText = findViewById(R.id.logInText);

        signUpButton = findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "signin button clicked!");

                String firstName = firstNameInputBox.getText().toString();
                String lastName = lastNameInputBox.getText().toString();
                String username = usernameInputBox.getText().toString();
                String password = passwordInputBox.getText().toString();
                String email = emailInputBox.getText().toString();

                signinUser(username, password);

                if (firstName.isEmpty() && lastName.isEmpty() && username.isEmpty() && password.isEmpty() && email.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please do not leave any inputs empty", Toast.LENGTH_SHORT).show();
                } else if(firstName.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please log in with a valid first name. (The first name cannot be empty)", Toast.LENGTH_SHORT).show();
                } else if(lastName.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please log in with a valid last name. (The last name cannot be empty)", Toast.LENGTH_SHORT).show();
                } else if(username.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please log in with a valid username. (Username cannot be empty)", Toast.LENGTH_SHORT).show();
                } else if(password.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please log in with a valid password. (Password cannot be empty)", Toast.LENGTH_SHORT).show();
                } else if(email.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please log in with a valid email. (Email cannot be empty)", Toast.LENGTH_SHORT).show();
                }

            }
        });

        logInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "log in text clicked!");
                goLoginActivity();
            }
        });

    }

    //This is where we let our user login in depending on if their login credentials are correct
    private void signinUser(String username, String password){
        String firstName = firstNameInputBox.getText().toString();
        String lastName = lastNameInputBox.getText().toString();
        String email = emailInputBox.getText().toString();

        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.put("firstName", firstName);
        user.put("lastName", lastName);

        Log.i(TAG, "attempting to log in user " + username);
        user.signUpInBackground(new SignUpCallback() {
            //checking if logging in was an issue and if so then it has an error that we check for
            public void done(ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issue with login", e);
                    return;
                }
                //navigates to main activity if the user successfully signs in properly
                goMainActivity();
                //Toasts visually show the user that they were able to log in with message @ bottom of screen
                Toast.makeText(RegisterActivity.this, "Hacker voice: I'm in", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //entering the main activity page
    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

        finish();
    }

    //entering the login activity page
    private void goLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);

        finish();
    }


}