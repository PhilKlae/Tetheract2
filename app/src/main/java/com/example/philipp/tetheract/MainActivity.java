package com.example.philipp.tetheract;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.philipp.tetheract.fragments.BlankFragment;

import java.lang.reflect.Field;
import java.util.List;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;


public class MainActivity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener  {
    Dpad mDpad = new Dpad();
     View decorView;

    public ShopButton selectedButton;
    public int selectedColor=0xFFCC0000;

    public ImageButton[] navigationButtons= new ImageButton[3];
    public LinearLayout navigationBar;

    enum NavigationStatus{

        main,
        shop

    }

    public NavigationStatus navigationStatus  = NavigationStatus.shop;
    public LinearLayout shopLayout;
    public View shopFragmentView;

    boolean selectorIsset=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


     /*   //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(SCREEN_ORIENTATION_LANDSCAPE);
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








        final PackageManager pm = getPackageManager();
//get a list of installed apps.
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            String TAG= "packages";
            Log.d(TAG, "package :" + packageInfo.packageName);
            Log.d(TAG, "name :" + packageInfo.loadLabel(getPackageManager()).toString());

        }


        //initialize navigationButtons
        navigationButtons[0]=(ImageButton) findViewById(R.id.Shop);
        navigationButtons[1]=(ImageButton) findViewById(R.id.Library);
        navigationButtons[2]=(ImageButton) findViewById(R.id.Settings);

        navigationBar=(LinearLayout) findViewById(R.id.Navigation);

        shopLayout =(LinearLayout) findViewById(R.id.ShopView);
        shopFragmentView =(View) findViewById(R.id.ShopFragmentView);

        navigationStatus = NavigationStatus.shop;

        if(!selectorIsset){
            swapNavigationFocus();
            selectorIsset=true;
        }

        this.getSupportActionBar().hide();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent k){

        if(k.getAction()==KeyEvent.ACTION_UP){



                if(k.getKeyCode()==KeyEvent.KEYCODE_BUTTON_A){
                    try {
                        ShopButton focusedButton = (ShopButton) getCurrentFocus();

                        if (focusedButton != null) {
                            Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(focusedButton.PackageName);
                            startActivity(LaunchIntent);

                        }
                    }
                    catch(Exception e){

                    }
                }

                if(k.getKeyCode()==KeyEvent.KEYCODE_BUTTON_B){
                    swapNavigationFocus();
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

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void onShopItemClicked(String packageName) {

        Toast.makeText(this, packageName, Toast.LENGTH_SHORT).show();

    }


    public void swapNavigationFocus(){


        if(navigationStatus == NavigationStatus.main){


            //set shop layout focusable
            shopLayout.setFocusable(true);
            shopLayout.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
            shopFragmentView.setFocusable(true);
            //request focus
            ((ShopButton)findViewById(R.id.feature1)).requestFocus();

            //set navigation not focusable
            navigationBar.setFocusable(false);
            navigationBar.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);




            //change status
            navigationStatus = NavigationStatus.shop;



            Toast.makeText(this, "now shop", Toast.LENGTH_SHORT).show();
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


            Toast.makeText(this, "now main", Toast.LENGTH_SHORT).show();

        }



    }



}
