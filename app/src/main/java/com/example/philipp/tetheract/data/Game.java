package com.example.philipp.tetheract.data;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Game {
    public String title;
    public String packageName;
    public String thumbnailPath;
    public String price;
    public boolean isInLibrary;

    public Game(String title, String packageName, String thumbnailPath, String price){

        this.title = title;
        this.packageName = packageName;
        this.thumbnailPath = thumbnailPath;
        this.price = price;

        if(User.user == null){
            throw  new AssertionError("User not Initialized!");
        }else{
            isInLibrary = User.user.gameIsInLibrary(packageName);
        }
    }

    public Game(JSONObject json) throws JSONException {

        this.title = json.getString("title");
        this.packageName = json.getString("packageName");
        this.thumbnailPath = json.getString("thumbnailName");
        this.price = json.getString("price");

        if(User.user == null){
            throw  new AssertionError("User not Initialized!");
        }else{
            isInLibrary = User.user.gameIsInLibrary(packageName);
        }

        Log.d("imognimehr", "Game: " + price);
    }

}
