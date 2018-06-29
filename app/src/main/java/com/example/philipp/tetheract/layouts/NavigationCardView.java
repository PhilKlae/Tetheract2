package com.example.philipp.tetheract.layouts;

import android.content.Context;
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

import com.example.philipp.tetheract.R;

public class NavigationCardView extends CardView{
    boolean drawGlow = false;
    public NavigationCardView(Context context) {
        super(context);
    }

    public NavigationCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NavigationCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);

        if(gainFocus){

          //  Toast.makeText(getContext(), "selected navigation", Toast.LENGTH_SHORT).show();
            ((FrameLayout)getChildAt(1)).setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.highlightgradient, null));
            DrawableCompat.setTint(  ((ImageView)getChildAt(2)).getDrawable(), ContextCompat.getColor(getContext(), R.color.buttonTintHighlight));
        //    ((ImageView)getChildAt(2)).setColorFilter(R.color.buttonTintHighlight);
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            setForeground(ResourcesCompat.getDrawable(getResources(), R.drawable.border, null));
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
            //   expand(10);

        }else{

            ((FrameLayout)getChildAt(1)).setBackground(null);
            DrawableCompat.setTint(  ((ImageView)getChildAt(2)).getDrawable(), ContextCompat.getColor(getContext(), R.color.buttonTintNoHighlight));
         //   drawGlow = false;
            setForeground(null);

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
            //collapse();
        }
    }
            Paint paint = new Paint();
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
    }



 /* public void collapse() {
        final int initialWidth = getWidth();

        measure(99, LayoutParams.MATCH_PARENT);
        int targetWidth = getMeasuredWidth();

        final int distanceToExpand = targetWidth - initialWidth;

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1){
                    // Do this after expanded
                }

                getLayoutParams().width = (int) (initialWidth + (distanceToExpand * interpolatedTime));
                requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration((long) distanceToExpand);
        startAnimation(a);
    }

    public void expand(int targetWidth) {
        final int initialWidth = getMeasuredWidth();



        final int distanceToCollapse = (int) (initialWidth - targetWidth);

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1){
                    // Do this after collapsed
                }



                getLayoutParams().width = (int) (initialWidth - (distanceToCollapse * interpolatedTime));
                requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration((long) distanceToCollapse);
        startAnimation(a);
    }*/
}

