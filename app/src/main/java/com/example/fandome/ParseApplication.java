package com.example.fandome;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("jWL7CDXwvU4H0BFe2CYKl14KcFd2PDTbPMK1GDGJ")
                .clientKey("kYTYqvUUWrbuLpVZR0JE4pXyXI8GkFrZtUUoUc0P")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
