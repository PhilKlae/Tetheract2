package com.example.philipp.tetheract.layouts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.philipp.tetheract.MainActivity;
import com.example.philipp.tetheract.R;

public class GameDeatailCardView extends BaseCard{
    boolean drawGlow = false;
    public GameDeatailCardView(Context context) {
        super(context);
        for(int i=0;i<buttonTooltips.length;i++){
            buttonTooltips[i] = "";
        }
    }

    public GameDeatailCardView(Context context, AttributeSet attrs) {
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

    public GameDeatailCardView(Context context, AttributeSet attrs, int defStyleAttr) {
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

      /*  if(gainFocus){
           ((MainActivity)getContext()).updateButtonTooltips(this);
            ((FrameLayout)getChildAt(1)).setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.highlightgradient, null));
            DrawableCompat.setTint(  ((ImageView)getChildAt(2)).getDrawable(), ContextCompat.getColor(getContext(), R.color.buttonTintHighlight));

            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            ((ImageView)getChildAt(1)).setImageResource(R.drawable.steam_bg_icon);

        }else{
            ((ImageView)getChildAt(1)).setImageDrawable(null);
            ((FrameLayout)getChildAt(1)).setBackground(null);
            DrawableCompat.setTint(  ((ImageView)getChildAt(2)).getDrawable(), ContextCompat.getColor(getContext(), R.color.buttonTintNoHighlight));

        }*/
    }



}

