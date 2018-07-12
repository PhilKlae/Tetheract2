package com.example.philipp.tetheract.layouts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.philipp.tetheract.MainActivity;
import com.example.philipp.tetheract.R;

public class BaseCard extends CardView {


    public String[] buttonTooltips= new String[4];

    public BaseCard(Context context) {
        super(context);
        for(int i=0;i<buttonTooltips.length;i++){
            buttonTooltips[i] = "";
        }
    }

    public BaseCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        for(int i=0;i<buttonTooltips.length;i++){
            buttonTooltips[i] = "";
        }
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.BaseCard,
                0, 0);

        try {
            buttonTooltips[0] = a.getString(R.styleable.BaseCard_aString);
            buttonTooltips[1] = a.getString(R.styleable.BaseCard_bString);
            buttonTooltips[2] = a.getString(R.styleable.BaseCard_xString);
            buttonTooltips[3] = a.getString(R.styleable.BaseCard_yString);

        } finally {
            a.recycle();
        }
    }

    public BaseCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        for(int i=0;i<buttonTooltips.length;i++){
            buttonTooltips[i] = "";
        }
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.BaseCard,
                0, 0);

        try {
            buttonTooltips[0] = a.getString(R.styleable.BaseCard_aString);
            buttonTooltips[1] = a.getString(R.styleable.BaseCard_bString);
            buttonTooltips[2] = a.getString(R.styleable.BaseCard_xString);
            buttonTooltips[3] = a.getString(R.styleable.BaseCard_yString);


        } finally {
            a.recycle();
        }
    }


    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);

        if(gainFocus){
            try {
                ((MainActivity) getContext()).playSound("activation");

            }catch(Exception e){

            }

            setForeground(ResourcesCompat.getDrawable(getResources(), R.drawable.border, null));


        }else{

            setForeground(null);

        }
    }

}
