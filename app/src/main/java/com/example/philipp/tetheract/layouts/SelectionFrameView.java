package com.example.philipp.tetheract.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;

import com.example.philipp.tetheract.R;

public class SelectionFrameView extends android.support.v7.widget.AppCompatImageView {

    Drawable border;

    public SelectionFrameView(Context context) {
        super(context);


    }

    public SelectionFrameView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public SelectionFrameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
       /* border = getResources().getDrawable(R.drawable.border);
        border.setBounds(1, 1,148  ,100);
        setImageBitmap(highlightImage());
        getLayoutParams().height = ((View)getParent()).getLayoutParams().height + 10;
        getLayoutParams().width =  ((View)getParent()).getLayoutParams().width + 10;
        requestLayout();*/
        glow(0.8f);

    }

    public void glow(float targetSize) {
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



    public Bitmap highlightImage() {
        // create new bitmap, which will be painted and becomes result image
      Bitmap bmOut = Bitmap.createBitmap(getMeasuredWidth() + 9, getMeasuredHeight() + 9, Bitmap.Config.ARGB_8888);


      //  Bitmap bmOut = Bitmap.createBitmap(100 + 9, 100 + 9, Bitmap.Config.ARGB_8888);

        // setup canvas for painting
        Canvas canvas = new Canvas(bmOut);
        border.draw(canvas);
        // setup default color
        /*canvas.drawColor(0, PorterDuff.Mode.CLEAR);
       // create a blur paint for capturing alpha
        Paint ptBlur = new Paint();
        ptBlur.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.NORMAL));
        int[] offsetXY = new int[2];
        // capture alpha into a bitmap
        Bitmap bmAlpha = bmOut.extractAlpha(ptBlur, offsetXY);
        // create a color paint
        Paint ptAlphaColor = new Paint();
        ptAlphaColor.setColor(getResources().getColor(R.color.highlight));
        // paint color for captured alpha region (bitmap)
        canvas.drawBitmap(bmAlpha, offsetXY[0], offsetXY[1], ptAlphaColor);
        // free memory
        bmAlpha.recycle();

        // paint the image source
        canvas.drawBitmap(bmOut, 0, 0, null);*/

        // return out final image
        return bmOut;
    }



    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static int convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int)dp;
    }



}
