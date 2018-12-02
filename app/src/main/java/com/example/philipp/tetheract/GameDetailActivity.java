package com.example.philipp.tetheract;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.philipp.tetheract.data.Game;
import com.example.philipp.tetheract.layouts.BaseCard;
import com.example.philipp.tetheract.layouts.Slider;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

public class GameDetailActivity extends AppCompatActivity {
    View decorView;
    Game game;
    Handler handler;
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
        DisplayManager dm = (DisplayManager)getApplicationContext().getSystemService(DISPLAY_SERVICE);
        if (dm != null){
            Display dispArray[] = dm.getDisplays(DisplayManager.DISPLAY_CATEGORY_PRESENTATION);

            if (dispArray.length>0) {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.screenBrightness = 0f;
                getWindow().setAttributes(params);
            }
        }

        DisplayManager.DisplayListener mDisplayListener = new DisplayManager.DisplayListener() {
            @Override
            public void onDisplayAdded(int displayId) {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.screenBrightness = 0f;
                getWindow().setAttributes(params);
                android.util.Log.i("Display", "Display #" + displayId + " added.");
            }

            @Override
            public void onDisplayChanged(int displayId) {
                android.util.Log.i("Display", "Display #" + displayId + " changed.");
            }

            @Override
            public void onDisplayRemoved(int displayId) {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.screenBrightness = 1.0f;
                getWindow().setAttributes(params);
            }
        };
        DisplayManager displayManager = (DisplayManager) getApplicationContext().getSystemService(Context.DISPLAY_SERVICE);
        handler = new Handler();
        displayManager.registerDisplayListener(mDisplayListener, handler);

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

        //TODO reimplement
        final ImageView bigImage = ((ImageView)findViewById(R.id.img2));


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

   /*     final Slider slider = (Slider) findViewById(R.id.ImageSlider);
        slider.getViewTreeObserver().addOnGlobalLayoutListener(

                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        PackageManager m = getPackageManager();
                        String s = getPackageName();
                        PackageInfo p = null;
                        try {
                            p = m.getPackageInfo(s, 0);
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                        s = p.applicationInfo.dataDir;

                        s= getFilesDir().getAbsolutePath();

                        slider.setup(game.thumbnailPath);
                    }
                }

        );


        findViewById(R.id.ImageSlider).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Slider)v).slideDirection(1);


            }
        });

*/


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
