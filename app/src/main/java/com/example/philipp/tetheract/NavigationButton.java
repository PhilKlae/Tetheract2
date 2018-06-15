package com.example.philipp.tetheract;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;
import com.example.philipp.tetheract.MainActivity.NavigationStatus;
public class NavigationButton extends AppCompatImageButton {
    NavigationStatus buttonStatus;

    public NavigationButton(Context context) {
        super(context);
    }

    public NavigationButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.navigationButton,
                0, 0);

        try {


            int ordinal = a.getInt(R.styleable.navigationButton_statusLink, 0);

            if (ordinal >= 0 && ordinal < NavigationStatus.values().length) {
                buttonStatus = NavigationStatus.values()[ordinal];
            }
        } finally {
            a.recycle();
        }

    }

    public NavigationButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.navigationButton,
                0, 0);

        try {


            int ordinal = a.getInt(R.styleable.navigationButton_statusLink, 0);

            if (ordinal >= 0 && ordinal < NavigationStatus.values().length) {
                buttonStatus = NavigationStatus.values()[ordinal];
            }
        } finally {
            a.recycle();
        }



    }



}
