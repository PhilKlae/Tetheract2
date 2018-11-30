package com.example.philipp.tetheract.layouts;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

public class SliderChild extends FrameLayout {
    public SliderChild sibling;
    public Slider parent;

    VideoView videoView;
    ImageView imageView;

    public SliderChild(Context context) {
        super(context);
        setupChilds();
    }

    public SliderChild(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupChilds();
    }

    public SliderChild(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupChilds();
    }

    public void setupChilds(){
        imageView =(ImageView)(getChildAt(0));
        videoView = (VideoView)   getChildAt(1);
    }

    public void setVideo(Uri uri){

        videoView.setVisibility(VISIBLE);
        imageView.setVisibility(INVISIBLE);

        videoView.setVideoURI(uri);
    }

    public void setImage(Bitmap bitmap){
        videoView.setVisibility(INVISIBLE);
        imageView.setVisibility(VISIBLE);

        imageView.setImageBitmap(bitmap);
    }

    public void joinInFromRight(){
        parent.busy = true;
        setVisibility(VISIBLE);

        setTranslationX(getMeasuredWidth());

        final float distanceMove =  ( getMeasuredWidth());


        final float initialTranslate=getTranslationX();
        final float targetTranslate=0f;
        parent.swapActiveViewIndex();
        Animation a = new Animation() {
            boolean triggered=false;
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1){
                    if(!triggered){
                        sibling.setVisibility(INVISIBLE);

                        parent.busy = false;
                        triggered = true;
                    }
                }

                setTranslationX((initialTranslate - (distanceMove * interpolatedTime)));
                //setTranslationX(100);
                //requestLayout();
            }



            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration(1500);
        startAnimation(a);
    }


    public void joinInLeft(){

    }




}


