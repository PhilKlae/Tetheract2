package com.example.philipp.tetheract.layouts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageButton;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.philipp.tetheract.R;



/**
 * TODO: document your custom view class.
 */
public class ShopButton extends AppCompatImageButton {

    String leftButtonId ="";
    String rightButtonId ="";
    String upButtonId ="";
    String downButtonId ="";

    public String PackageName = "";

    public ShopButton(Context context) {
        super(context);
    }

    public ShopButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MainView,
                0, 0);

        try {
            leftButtonId = a.getString(R.styleable.MainView_leftId);
            rightButtonId = a.getString(R.styleable.MainView_rightId);
            upButtonId = a.getString(R.styleable.MainView_upId);
            downButtonId = a.getString(R.styleable.MainView_downId);
            PackageName = a.getString(R.styleable.MainView_packageName);
        } finally {
            a.recycle();
        }

    }

    public ShopButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MainView,
                0, 0);

        try {
            leftButtonId = a.getString(R.styleable.MainView_leftId);
            rightButtonId = a.getString(R.styleable.MainView_rightId);
            upButtonId = a.getString(R.styleable.MainView_upId);
            downButtonId = a.getString(R.styleable.MainView_downId);
            PackageName = a.getString(R.styleable.MainView_packageName);
        } finally {
            a.recycle();
        }



    }



}
