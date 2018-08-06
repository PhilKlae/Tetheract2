package com.example.philipp.tetheract.data;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Game implements Serializable {
    public String title;
    public String packageName;
    public String thumbnailPath;
    public String price;
    public boolean isInLibrary;
    public String description;
    public String ratingCount;
    public String rating;
    public String releaseDate;
    public String bigimagePath;
    public String tags;


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

        this.description = json.getString("Description");
        this.bigimagePath = json.getString("bigPictureName");
        this.rating = json.getString("rating");
        this.releaseDate = json.getString("releasedate");
        this.ratingCount = json.getString("reviewstotal");
        this.tags = json.getString("tags");


        if(User.user == null){
            throw  new AssertionError("User not Initialized!");
        }else{
            isInLibrary = User.user.gameIsInLibrary(packageName);
        }

        Log.d("imognimehr", "Game: " + price);
    }

}
