package com.example.fandome.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject  {
    public static final String KEY_BODY="body";
    public static final String KEY_FANDOME="fandome";
    public static final String KEY_USER="user";

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
    public void setKeyFandome(ParseObject fandome){
        put(KEY_FANDOME, fandome);
    }
}
