package com.example.philipp.tetheract;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.media.MediaPlayer;
import android.media.MediaRouter;
import android.net.RouteInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.philipp.tetheract.data.Game;
import com.example.philipp.tetheract.data.User;
import com.example.philipp.tetheract.fragments.BlankFragment;
import com.example.philipp.tetheract.layouts.BaseCard;
import com.example.philipp.tetheract.layouts.GameCardView;
import com.example.philipp.tetheract.layouts.NavigationCardView;
import com.example.philipp.tetheract.layouts.ShopButton;
import com.example.philipp.tetheract.services.MultiDisplayService;
import com.example.philipp.tetheract.services.PlayerService;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;


public class MainActivity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener  {
    Dpad mDpad = new Dpad();
     View decorView;

    User user;
     AssetFileDescriptor focusChangeAsset;
     FileDescriptor focusChangeFile;

     AssetFileDescriptor activationAsset;
     FileDescriptor activationFile;


    LinearLayout[] tooltips=new LinearLayout[4];


    public GameCardView[] visibleCardviews;
    public GameCardView lastSlectedCardView;

    public NavigationCardView[] navigationButtons= new NavigationCardView[4];
    public LinearLayout navigationBar;

    //fragment stuff
    private FragmentManager fm;
    private FragmentTransaction ft;

    Handler handler;

    public enum NavigationStatus{

        main,
        shop,
        settings,
        library

    }

    public NavigationStatus navigationStatus  = NavigationStatus.shop;
    public LinearLayout shopLayout;
    public View shopFragmentView;

    boolean selectorIsset=false;

    public static boolean playedChangeSound=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


     /*   //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

*/

        super.onCreate(savedInstanceState);

     //   startService(new Intent(this, MultiDisplayService.class));

        //initialize user
        user = new User((Context)this);

        DisplayManager dm = (DisplayManager)getApplicationContext().getSystemService(DISPLAY_SERVICE);

       /* if (dm != null) {
            Display dispArray[] = dm.getDisplays(DisplayManager.DISPLAY_CATEGORY_PRESENTATION);
            Toast.makeText(this, "asdad " + dispArray.length, Toast.LENGTH_LONG).show();
            if (dispArray.length > 0) {
                Display display = dispArray[0];
                Context displayContext = getApplicationContext().createDisplayContext(display);
                WindowManager wm = (WindowManager)displayContext.getSystemService(WINDOW_SERVICE);
                View view = LayoutInflater.from(displayContext).inflate(R.layout.activity_main,null);
                final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.TYPE_TOAST,
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                        PixelFormat.TRANSLUCENT);
                wm.addView(view, params);
                //Log.e("asdads", "Service using display:" + display.getName());
               // Context displayContext = getApplicationContext().createDisplayContext(display);
            }else{

                setContentView(R.layout.activity_main);
            }

        }*/
        setContentView(R.layout.activity_main);



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

      //  setRequestedOrientation(SCREEN_ORIENTATION_LANDSCAPE);
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




        //initialize navigationButtons
        navigationButtons[0]=(NavigationCardView) findViewById(R.id.Shop);
        navigationButtons[1]=(NavigationCardView) findViewById(R.id.Library);
        navigationButtons[2]=(NavigationCardView) findViewById(R.id.Settings);
        navigationButtons[3]=(NavigationCardView) findViewById(R.id.Community);

        for(int i=0;i<navigationButtons.length;i++){

            navigationButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
                }
            });
        }

        navigationBar=(LinearLayout) findViewById(R.id.Navigation);

        shopLayout =(LinearLayout) findViewById(R.id.ShopView);
        shopFragmentView =(View) findViewById(R.id.ShopFragmentView);



        //initialize tooltips
        tooltips[0]=(LinearLayout)findViewById(R.id.aButtonTooltip);
        tooltips[1]=(LinearLayout)findViewById(R.id.bButtonTooltip);
        tooltips[2]=(LinearLayout)findViewById(R.id.xButtonTooltip);
        tooltips[3]=(LinearLayout)findViewById(R.id.yButtonTooltip);


        //Load sound Files
        focusChangeAsset = getResources().openRawResourceFd(R.raw.txting_type_fail);
        focusChangeFile = focusChangeAsset.getFileDescriptor();

        activationAsset = getResources().openRawResourceFd(R.raw.activation);
        activationFile = activationAsset.getFileDescriptor();




        navigationStatus = NavigationStatus.shop;
        if(!selectorIsset){
            swapNavigationFocus();
            selectorIsset=true;
        }


     //   this.getSupportActionBar().hide();



        //load game cards
       // loadGameCards();
        loadGameCardsFromJson();


        //fragment stuff
        fm = getFragmentManager();

        //start service
        startService(new Intent(this, PlayerService.class));
    }



    @Override
    public boolean dispatchKeyEvent(KeyEvent k){

        if(k.getAction()==KeyEvent.ACTION_UP){

                // main navigation
            if(navigationStatus==NavigationStatus.main) {
                if(k.getKeyCode()==KeyEvent.KEYCODE_BUTTON_A)
                {
                    try {
                        NavigationCardView focusedButton = (NavigationCardView) getCurrentFocus();
                        if(focusedButton == navigationButtons[0]){
                            swapNavigationFocus();
                            return false;
                        }else{
                            Toast.makeText(this, "Not implemented yet", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch(Exception e){

                        Toast.makeText(this, "Not implemented yet", Toast.LENGTH_SHORT).show();

                    }
                }
            }


            //shop navigation
            if(navigationStatus==NavigationStatus.shop){
                if(k.getKeyCode()==KeyEvent.KEYCODE_BUTTON_A){
                    try {
                        GameCardView focusedButton = (GameCardView) getCurrentFocus();

                        if (focusedButton != null) {

                            if(focusedButton.game.isInLibrary){
                                Log.d("counter"," :" + focusedButton.game.packageName);
                                Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(focusedButton.game.packageName);
                                startActivity(LaunchIntent);
                            }else{
                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + focusedButton.game.packageName)));
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + focusedButton.game.packageName)));
                                }
                            }


                        }
                    }
                    catch(Exception e){

                        Toast.makeText(this, "Not implemented yet", Toast.LENGTH_SHORT).show();

                    }
                }
                if(k.getKeyCode()==KeyEvent.KEYCODE_BUTTON_B){
                    swapNavigationFocus();
                    return false;
                }
                if(k.getKeyCode()==KeyEvent.KEYCODE_BUTTON_Y){
                    Intent intent = new Intent(this, GameDetailActivity.class);
                    GameCardView focusedButton = (GameCardView) getCurrentFocus();

                    if (focusedButton != null) {

                        intent.putExtra("game",focusedButton.game);

                    }

                    startActivity(intent);
                   /* ViewGroup fragmentContainer = findViewById(R.id.ShopFragmentView);
                    ft.remove (getFragmentManager().findFragmentById(R.id.ShopFragmentView));
                    ft.commit();*/

                   // Toast.makeText(this, "Not implemented yet", Toast.LENGTH_SHORT).show();
                }

            }






        }
     /*   selectedButton.setBackgroundColor(0xFFFFF);
       // Toast.makeText(this, selectedButton.rightButtonId, Toast.LENGTH_SHORT).show();


        if(selectedButton.rightButtonId.equals("")){
            return false;
        }


        Resources res = getResources();

        int id = res.getIdentifier(selectedButton.rightButtonId, "id", this.getPackageName());
        selectedButton = (ShopButton)findViewById(id);
        selectedButton.setBackgroundColor(selectedColor);*/
        /*Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("air.com.distractionware.vvvvvvmobile.humble");
        startActivity(LaunchIntent);

        Toast.makeText(this, "input", Toast.LENGTH_SHORT).show();*/
        return false;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }




    public void swapNavigationFocus(){


      playSound("focusChange");
        playedChangeSound=true;

        if(navigationStatus == NavigationStatus.main){


            //set shop layout focusable
            shopLayout.setFocusable(true);
            shopLayout.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
            shopFragmentView.setFocusable(true);
            //request focus
           lastSlectedCardView.requestFocus();

            //set navigation not focusable
            navigationBar.setFocusable(false);
            navigationBar.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);




            //change status
            navigationStatus = NavigationStatus.shop;



          //  Toast.makeText(this, "now shop", Toast.LENGTH_SHORT).show();
        }else{

            //set navigation layout focusable
            navigationBar.setFocusable(true);
            navigationBar.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
            //set navigationbuttons focusable
            for(int i=0;i<navigationButtons.length;i++){

               navigationButtons[i].setFocusable(true);
               navigationButtons[i].setFocusableInTouchMode(true);

            }
            //request focus
            navigationButtons[0].requestFocus();


            //set shop not focusable
            shopLayout.setFocusable(false);
            shopLayout.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            shopFragmentView.setFocusable(false);


            //change status
            navigationStatus = NavigationStatus.main;


           // Toast.makeText(this, "now main", Toast.LENGTH_SHORT).show();

        }



    }

    public void playSound(String sound){
        MediaPlayer player = new MediaPlayer();


        if(sound.equals("focusChange")){
            player.setVolume(100,100);
            try {
                player.setDataSource(activationFile, activationAsset.getStartOffset(),
                        activationAsset.getLength());
                player.setLooping(false);
                player.prepare();
                player.start();
            } catch (IOException ex) {
                //LOGGER.error(ex.getLocalizedMessage(), ex);
            }
        }

        if(sound.equals("activation")){
            player.setVolume(6f,6f);
            if(playedChangeSound==true) {

                playedChangeSound = false;
                return;
            }
            try {
                player.setDataSource(focusChangeFile, focusChangeAsset.getStartOffset(), focusChangeAsset.getLength());
                player.setLooping(false);
                player.prepare();
                player.start();

            } catch (IOException ex) {
                //LOGGER.error(ex.getLocalizedMessage(), ex);
            }
        }

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                mp.reset();
                mp.release();
            }
        });


    }

    public void updateButtonTooltips(BaseCard card){

        for(int i=0;i<card.buttonTooltips.length;i++){

            if(card.buttonTooltips[i]==null||card.buttonTooltips[i].isEmpty()){
                tooltips[i].setVisibility(View.GONE);
            }else{
                tooltips[i].setVisibility(View.VISIBLE);
                ((TextView)tooltips[i].getChildAt(1)).setText((CharSequence) card.buttonTooltips[i]);
            }

        }

    }




    public void loadGameCardsFromJson(){

        //load string from raw, later from server
        String jsonString="";
        try {
            Resources res = getResources();
            InputStream in_s = res.openRawResource(R.raw.gamedata);

            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            jsonString = (new String(b));
        } catch (Exception e) {
            // e.printStackTrace();
            jsonString =("Error: can't show help.");
        }





        //convert json to objects
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            LayoutInflater vi =getLayoutInflater();
            visibleCardviews = new GameCardView[jsonArray.length()];
            ViewGroup shopView = findViewById(R.id.ShopView);

            for(int i=0;i<jsonArray.length();i++){
          //  for(int i=0;i<2;i++){

                GameCardView v = (GameCardView)vi.inflate(R.layout.game_card_layout, null);
                Game game= new Game(jsonArray.getJSONObject(i));
                ViewGroup insertPoint = (ViewGroup) findViewById(R.id.genre1);
                if(jsonArray.getJSONObject(i).getString("Genre").equals("Editor's Pick")){
                     insertPoint = (ViewGroup) findViewById(R.id.genre1);
                }
                if(jsonArray.getJSONObject(i).getString("Genre").equals("Racing/Sports")){
                    insertPoint = (ViewGroup) findViewById(R.id.genre2);
                }
                if(jsonArray.getJSONObject(i).getString("Genre").equals("Action")){
                    insertPoint = (ViewGroup) findViewById(R.id.genre3);
                }
                if(jsonArray.getJSONObject(i).getString("Genre").equals("Indie")){
                    insertPoint = (ViewGroup) findViewById(R.id.genre4);
                }
                if(jsonArray.getJSONObject(i).getString("Genre").equals("Evergreens")){
                    insertPoint = (ViewGroup) findViewById(R.id.genre5);
                }
                if(jsonArray.getJSONObject(i).getString("Genre").equals("Free to play")){
                    insertPoint = (ViewGroup) findViewById(R.id.genre6);
                }

                TableLayout.LayoutParams params = new TableLayout.LayoutParams(725, ViewGroup.LayoutParams.MATCH_PARENT,1);
                //TableLayout.LayoutParams params = new TableLayout.LayoutParams()
                params.setMargins(10,0,10,0);
                insertPoint.addView(v, 1, params);
                //vi.inflate(R.layout.game_card_layout,insertPoint);
               //v.game = game;
                v.setGame(game);
                v.parentparent = shopView;
                v.parentIndex = shopView.indexOfChild(insertPoint);

                visibleCardviews[i]=v;



                v.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        // item clicked
                        Game game = ((GameCardView)v).game;
                      /*  if(game.isInLibrary){

                            Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(game.packageName);
                            startActivity(LaunchIntent);
                        }else{
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + game.packageName)));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + game.packageName)));
                            }
                        }*/
                        Intent intent = new Intent(getBaseContext(), GameDetailActivity.class);
                        intent.putExtra("game",game);
                        startActivity(intent);
                    }
                });

            }

            lastSlectedCardView = visibleCardviews[0];
           // LinearLayout.LayoutParams params = new LinearLayout.LayoutParams()//new FrameLayout.LayoutParams(725, ViewGroup.LayoutParams.MATCH_PARENT,2f);
           // visibleCardviews[0].getLayoutParams()//.width=ViewGroup.LayoutParams.MATCH_PARENT;
            visibleCardviews[0].zoom=false;
            visibleCardviews[0].setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1f));
            visibleCardviews[0].requestLayout();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



}
