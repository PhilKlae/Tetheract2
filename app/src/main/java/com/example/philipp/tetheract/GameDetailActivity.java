package com.example.philipp.tetheract;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.philipp.tetheract.data.Game;
import com.example.philipp.tetheract.layouts.BaseCard;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

public class GameDetailActivity extends AppCompatActivity {
    View decorView;
    Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        Intent intent = getIntent();
        game  = (Game)intent.getSerializableExtra("game");

        decorView = getWindow().getDecorView();
// Hide both the navigation bar and the status bar.
// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
// a general rule, you should design your app to hide the status bar whenever you
// hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        decorView.setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        // Note that system bars will only be "visible" if none of the
                        // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                            decorView.setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_FULLSCREEN);
                            // TODO: The system bars are visible. Make any desired
                            // adjustments to your UI, such as showing the action bar or
                            // other navigational controls.
                        } else {
                            // TODO: The system bars are NOT visible. Make any desired
                            // adjustments to your UI, such as hiding the action bar or
                            // other navigational controls.
                        }
                    }
                });

      //  this.getSupportActionBar().hide();


        findViewById(R.id.ImageSlider).requestFocus();
        setUpViews();
    }


    public void setUpViews(){

        ((TextView)findViewById(R.id.GameTitle)).setText(game.title.toUpperCase());
        ((TextView)findViewById(R.id.BuyGameTitle)).setText("Buy "+ game.title.toUpperCase());


        if(game.isInLibrary){

            ((TextView)findViewById(R.id.DetailPagePrice)).setText("In Library, Start Game");

        }
        else{

                ((TextView)findViewById(R.id.DetailPagePrice)).setText(game.price + " $");

        }



        //TODO Iamge, Features
        ((TextView)findViewById(R.id.ReviewCount)).setText("(" + game.ratingCount + ")");
        ((TextView)findViewById(R.id.rating)).setText( game.rating );
        ((TextView)findViewById(R.id.releaseDate)).setText( "Release Date: " +  game.releaseDate);
        ((TextView)findViewById(R.id.UserTags)).setText(  game.tags);
        ((TextView)findViewById(R.id.descriptiontext)).setText(  game.description);



        (findViewById(R.id.GameDetailStartGame)).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                // item clicked
                if(game.isInLibrary){

                    Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(game.packageName);
                    startActivity(LaunchIntent);
                }else{
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + game.packageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + game.packageName)));
                    }
                }
            }
        });


        final ImageView bigImage = ((ImageView)findViewById(R.id.bigImage));


        bigImage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                bigImage.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                if(game.thumbnailPath !=null&&!game.thumbnailPath.equals("")){
                    int resId = getResources().getIdentifier(game.bigimagePath, "drawable", getPackageName());
                    Bitmap image;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        image = BitmapFactory.decodeResource(getResources(), resId);
                        image = scaleDown(image,bigImage.getWidth()-2,false);
                        bigImage.setImageBitmap(image);
                    }

                }
        }});

        findViewById(R.id.wishlist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
            }
        });




    }






    //  try {

      /*  } catch (IOException e) {
            e.printStackTrace();
        }*/






    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }



}