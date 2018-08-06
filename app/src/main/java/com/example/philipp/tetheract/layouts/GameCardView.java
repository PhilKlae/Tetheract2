package com.example.philipp.tetheract.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.philipp.tetheract.MainActivity;
import com.example.philipp.tetheract.R;
import com.example.philipp.tetheract.data.Game;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;

public class GameCardView extends BaseCard{

    public Game game;
    String price;
    String title;
    String packageName;
    Bitmap image;

    public boolean zoom = true;
    ScrollView shopScrollView;
    public GameCardView(Context context) {
        super(context);
        buttonTooltips[1]="Back to Navigation";
        buttonTooltips[3]="Open detail page";

       // game=new Game();


       // Log.d("thumbnail", "Fickscheiße 1 " );

    }

    public GameCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        buttonTooltips[1]="Back to Navigation";
        buttonTooltips[3]="Open detail page";

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.GameCard,
                0, 0);

        try {


        } finally {
            a.recycle();
        }

    }

    public GameCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        buttonTooltips[1]="Back to Navigation";
        buttonTooltips[3]="Open detail page";

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.GameCard,
                0, 0);

        try {

        } finally {
            a.recycle();
        }
    }



    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);

        if(gainFocus){



            ((MainActivity)getContext()).updateButtonTooltips(this);
            ((MainActivity)getContext()).lastSlectedCardView=this;
            ((TextView)(getChildAt(1))).setTextColor(getResources().getColor(R.color.white));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                this.setElevation(50);
            }
           // setForeground(ResourcesCompat.getDrawable(getResources(), R.drawable.border, null));

            if(zoom){
                expand(1.2f);
            }

        }else{
            ((TextView)(getChildAt(1))).setTextColor(getResources().getColor(R.color.font));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                this.setElevation(0);
            }
          //  setForeground(null);
            if(zoom){
                collapse();
            }

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

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

      /*  shopScrollView=(ScrollView) findViewById(R.id.ShopScrollView);
        if(shopScrollView!=null){
            shopScrollView.smoothScrollTo(0,(int)(getHeight() * -1.2f));
        }*/
    }

    public void setGame(Game newGame){
        requestLayout();
        game = newGame;
        if(game.isInLibrary){
            ((TextView)getChildAt(1)).setText("Already Purchased");
            buttonTooltips[0]="Run Game";

        }else{

                ((TextView)getChildAt(1)).setText(game.price + " $");


            buttonTooltips[0]="Buy Game";
        }

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if(game.thumbnailPath !=null&&!game.thumbnailPath.equals("")){
                    int resId = getResources().getIdentifier(game.thumbnailPath, "drawable", getContext().getPackageName());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        image = BitmapFactory.decodeResource(getContext().getResources(), resId);
                        image = scaleDown(image,getWidth()-2,false);
                        ((ImageView)getChildAt(0)).setImageBitmap(image);
                    }
                }            }
        });



      //  try {

      /*  } catch (IOException e) {
            e.printStackTrace();
        }*/


    }



    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

}
