package com.example.philipp.tetheract.layouts;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.philipp.tetheract.R;

public class GameCardView extends CardView{
    public GameCardView(Context context) {
        super(context);
    }

    public GameCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);

        if(gainFocus){

            ((TextView)(((FrameLayout)getChildAt(0)).getChildAt(2))).setTextColor(getResources().getColor(R.color.white));
           /* LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    99,
                    LayoutParams.MATCH_PARENT,
                    1f
            );
            param.setMargins(2, 2, 2, 2);*/
            FrameLayout child = (FrameLayout) getChildAt(0);
            child.setPadding(5,5,5,1);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                this.setElevation(50);
            }
          expand(1.2f);

        }else{
            ((TextView)(((FrameLayout)getChildAt(0)).getChildAt(2))).setTextColor(getResources().getColor(R.color.font));
          /*  LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    99,
                    LayoutParams.MATCH_PARENT,
                    1f
            );
            param.setMargins(0, 0, 0, 0);
            getChildAt(0).setLayoutParams(param);*/
            FrameLayout child = (FrameLayout) getChildAt(0);
            child.setPadding(0,0,0,0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                this.setElevation(0);
            }
            collapse();
        }
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
