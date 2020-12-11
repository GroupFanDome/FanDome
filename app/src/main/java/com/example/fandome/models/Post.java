package com.example.fandome.models;

import com.example.fandome.TimeFormatter;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Locale;

@ParseClassName("Post")
public class Post extends ParseObject  {
    public static final String KEY_BODY="body";
    public static final String KEY_USER="user";
    public static final String KEY_FANDOME="fandome";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_POST_IMAGE = "post_image";

    public String getBody(){
        return getString(KEY_BODY);
    }

    public void setBody(String body){
        put(KEY_BODY, body);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }

    public ParseObject getFandome(){
        return getParseObject(KEY_FANDOME);
    }

    public void setFandome(ParseObject fandome){
        put(KEY_FANDOME, fandome);
    }

    public ParseFile getPostImage() {
        return getParseFile(KEY_POST_IMAGE);
    }
    public void setPostImage(ParseFile parseFile){
        put(KEY_POST_IMAGE, parseFile);
    }


    public String getDateCreated() {
        String Format = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat format = new SimpleDateFormat(Format, Locale.ENGLISH);
        String strDate = format.format(getCreatedAt());
        return TimeFormatter.getTimeDifference(strDate);
    }
}
