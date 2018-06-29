package com.example.philipp.tetheract.data;

public class GameCard {
    public String title;
    public String packageName;
    public String thumbnailPath;
    public long price;
    public boolean isInLibrary;

    GameCard(String title, String packageName, String thumbnailPath, long price){

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

}
