package com.example.fandome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fandome.activities.MainActivity;
import com.example.fandome.fragments.HomeFragment;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/*
 * A login screen that offers login via username or email/password
 */
public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText usernameInputBox;
    private EditText passwordInputBox;
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_login);
//        setContentView(R.layout.fragment_profile);


        //checks if user has previously logged on so they can go straight to main activity
        if (ParseUser.getCurrentUser() != null){
            goMainActivity();
        }

        usernameInputBox = findViewById(R.id.usernameInputBox);
        passwordInputBox = findViewById(R.id.passwordInputBox);
        loginButton = findViewById(R.id.signUpButton);
        registerButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "login button clicked!");
                String username = usernameInputBox.getText().toString();
                String password = passwordInputBox.getText().toString();

                loginUser(username, password);
                // TODO: also allow user to log in via email and password

                if (username.isEmpty() && password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please log in with a valid username and password. (Username and password cannot be empty)", Toast.LENGTH_SHORT).show();
                } else if(username.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please log in with a valid username. (Username cannot be empty)", Toast.LENGTH_SHORT).show();
                } else if(password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please log in with a valid password. (Password cannot be empty)", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "register button clicked!");
                goRegisterActivity();
            }
        });
    }

    //This is where we let our user login in depending on if their login credentials are correct
    private void loginUser(String username, String password) {
        Log.i(TAG, "attempting to log in user " + username);
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            //checking if logging in was an issue and if so then it has an error that we check for
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issue with login", e);
                    return;
                }
                //navigates to main activity if the user successfully signs in properly
                goMainActivity();
                //Toasts visually show the user that they were able to log in with message @ bottom of screen
                Toast.makeText(LoginActivity.this, "Hacker voice: I'm in", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //entering the main activity page
    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

        //removes the chance to press the back button and go into the log in page again
        finish();
    }

    //entering the register activity page
    private void goRegisterActivity() {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);

        finish();
    }

}