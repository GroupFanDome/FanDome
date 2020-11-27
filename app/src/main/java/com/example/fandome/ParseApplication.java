package com.example.fandome;

import android.app.Application;

import com.example.fandome.models.Fandome;
import com.example.fandome.models.Following;
import com.example.fandome.models.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Register your parse models
        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(Fandome.class);
        ParseObject.registerSubclass(Following.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("jWL7CDXwvU4H0BFe2CYKl14KcFd2PDTbPMK1GDGJ")
                .clientKey("kYTYqvUUWrbuLpVZR0JE4pXyXI8GkFrZtUUoUc0P")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
