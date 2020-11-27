package com.example.fandome.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Fandome")
public class Fandome extends ParseObject  {
    public static final String KEY_NAME="name";
    public static final String KEY_DESCRIPTION="description";
    public static final String KEY_KEYWORD="keyword";

    public String getName(){
        return getString(KEY_NAME);
    }
    public void setName(String name){
        put(KEY_NAME, name);
    }

    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }
    public void setDescription(String description){
        put(KEY_DESCRIPTION,description);
    }

    public String getKeyword(){
        return getString(KEY_KEYWORD);
    }
    public void setKeyword(String keyword){
        put(KEY_KEYWORD,keyword);
    }

}
