package com.example.philipp.tetheract.layouts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.philipp.tetheract.MainActivity;
import com.example.philipp.tetheract.R;

public class NavigationCardView extends BaseCard{
    boolean drawGlow = false;
    public NavigationCardView(Context context) {
        super(context);
        for(int i=0;i<buttonTooltips.length;i++){
            buttonTooltips[i] = "";
        }
    }

    public NavigationCardView(Context context, AttributeSet attrs) {
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

    public NavigationCardView(Context context, AttributeSet attrs, int defStyleAttr) {
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

                ((MainActivity)getContext()).updateButtonTooltips(this);




          //  Toast.makeText(getContext(), "selected navigation", Toast.LENGTH_SHORT).show();
            ((FrameLayout)getChildAt(1)).setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.highlightgradient, null));
            DrawableCompat.setTint(  ((ImageView)getChildAt(2)).getDrawable(), ContextCompat.getColor(getContext(), R.color.buttonTintHighlight));
        //    ((ImageView)getChildAt(2)).setColorFilter(R.color.buttonTintHighlight);
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    this.setElevation(50);
                }
          //  setForeground(ResourcesCompat.getDrawable(getResources(), R.drawable.border, null));
           // drawGlow = true;
           /* LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    99,
                    LayoutParams.MATCH_PARENT,
                    1f
            );
            param.setMargins(2, 2, 2, 2);*/
          /*  FrameLayout child = (FrameLayout) getChildAt(0);
            child.setPadding(5,5,5,1);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                this.setElevation(50);
            }*/
               expand(1.1f);
            }catch(Exception e){

            }
        }else{

            ((FrameLayout)getChildAt(1)).setBackground(null);
            DrawableCompat.setTint(  ((ImageView)getChildAt(2)).getDrawable(), ContextCompat.getColor(getContext(), R.color.buttonTintNoHighlight));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                this.setElevation(0);
            }
         //   drawGlow = false;
        //    setForeground(null);

          /*  LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    99,
                    LayoutParams.MATCH_PARENT,
                    1f
            );
            param.setMargins(0, 0, 0, 0);
            getChildAt(0).setLayoutParams(param);*/
        /*    FrameLayout child = (FrameLayout) getChildAt(0);
            child.setPadding(0,0,0,0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                this.setElevation(0);
            }*/
            collapse();
        }
    }
           /* Paint paint = new Paint();
    {
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setAlpha(50);
    };

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if(drawGlow)
            canvas.drawCircle(0, 0, 150, paint);
    }*/
    public void highlight(){
               ((FrameLayout)getChildAt(1)).setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.highlightgradient, null));
               DrawableCompat.setTint(  ((ImageView)getChildAt(2)).getDrawable(), ContextCompat.getColor(getContext(), R.color.buttonTintHighlight));
               //    ((ImageView)getChildAt(2)).setColorFilter(R.color.buttonTintHighlight);
               setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }
    public void deHighlight(){
        ((FrameLayout)getChildAt(1)).setBackground(null);
        DrawableCompat.setTint(  ((ImageView)getChildAt(2)).getDrawable(), ContextCompat.getColor(getContext(), R.color.buttonTintNoHighlight));
    }

    public void collapse() {
        final float initialSize = getScaleX();


        float targetSize = 1;

        final float distanceToExpand = targetSize - initialSize;

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1){
                    // Do this after expanded
                }
                setPivotX(0);

                setScaleX((initialSize + (distanceToExpand * interpolatedTime)));
                setScaleY((initialSize + (distanceToExpand * interpolatedTime)));
                //requestLayout();
            }

           /* @Override
            public boolean willChangeBounds() {
                return true;
            }*/
        };

        a.setDuration(100);
        startAnimation(a);
    }

    public void expand(float targetSize) {
        final float initialSize = getScaleX();



        final float distanceToCollapse =  ( targetSize - initialSize);

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1){
                    // Do this after collapsed
                }

                setPivotX(0);

                setScaleX((initialSize + (distanceToCollapse * interpolatedTime)));
                setScaleY((initialSize + (distanceToCollapse * interpolatedTime)));
                //requestLayout();
            }

           /* @Override
            public boolean willChangeBounds() {
                return true;
            }*/
        };

        a.setDuration(100);
        startAnimation(a);
    }

}

