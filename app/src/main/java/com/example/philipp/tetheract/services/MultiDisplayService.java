package com.example.philipp.tetheract.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.philipp.tetheract.R;

public class MultiDisplayService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(getApplicationContext(),"sesrvice",Toast.LENGTH_LONG);
        DisplayManager dm = (DisplayManager) getApplicationContext().getSystemService(DISPLAY_SERVICE);
        if (dm != null) {
            Display dispArray[] = dm.getDisplays(DisplayManager.DISPLAY_CATEGORY_PRESENTATION);
            Toast.makeText(getApplicationContext(),"Displays: " + dispArray.length,Toast.LENGTH_LONG);
            if (dispArray.length > 0) {
                Display display = dispArray[0];
                Log.e("Display", "Service using display:" + display.getName());
                Context displayContext = getApplicationContext().createDisplayContext(display);
            /*    WindowManager wm = (WindowManager)displayContext.getSystemService(WINDOW_SERVICE);
                View view = LayoutInflater.from(displayContext).inflate(R.layout.fragment_main,null);
                final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.TYPE_TOAST,
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                        PixelFormat.TRANSLUCENT);
                wm.addView(view, params);*/
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}