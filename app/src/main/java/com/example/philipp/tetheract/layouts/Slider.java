package com.example.philipp.tetheract.layouts;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.philipp.tetheract.data.Game;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Slider extends BaseCard {
    public String[] imagePaths;
    public String[] videoPaths;

    public String contentPath;

    public int activeView = 0;

    public SliderChild[] childs;
    public boolean busy;

    int[] drawingorder;

    Bitmap[] bitmaps;
    Uri[] videoUris;

    int position=0;
    int videoCount=0;
    int imageCount=0;
    int count;


    public Slider(Context context) {
        super(context);
        setChildrenDrawingOrderEnabled(true);
        setupDrawingOrder();
    }

    public Slider(Context context, AttributeSet attrs) {
        super(context, attrs);
        setChildrenDrawingOrderEnabled(true);
        setupDrawingOrder();
    }

    public Slider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setChildrenDrawingOrderEnabled(true);
        setupDrawingOrder();
    }

    public void setup(String path){
        videoCount = loadVideos(path + "/vid");
        imageCount = loadImages(path + "/img");
        count = imageCount + videoCount;

        if(videoCount>0){
            childs[activeView].setVideo(videoUris[0]);
        }else{
            if(imageCount>0){
                childs[activeView].setImage(bitmaps[0]);
            }else{
                busy=true;
            }
        }
    }

    public void slideDirection(int direction) {
       // requestLayout();
        if(!busy){

            getInactiveView().joinInFromRight();
            position++;

        }
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        childs = new SliderChild[2];

        childs[0]=(SliderChild) getChildAt(0);
        childs[1]=(SliderChild)getChildAt(1);

        childs[0].sibling = childs[1];
        childs[1].sibling = childs[0];

        childs[0].parent = this;
        childs[1].parent = this;
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

    public void swapActiveViewIndex(){

        if(activeView == 0){
            activeView = 1;
            setChildDrawLast(1);
        }else{
            activeView = 0;
            setChildDrawLast(0);
        }

    }

    public SliderChild getInactiveView(){

        //return childs[activeView];
        if(activeView==0){
            return childs[1];
        }else{
            return childs[0];
        }

    }



    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        if(drawingorder.length ==0){
            setupDrawingOrder();
        }
        return drawingorder[i];
    }

    public void setChildDrawLast(int childIndex){

        int[] newDrawingOrder = new int[drawingorder.length];
        boolean hitchild=false;
        for(int i=0;i< drawingorder.length-1;i++){
            if(drawingorder[i]==childIndex){
                hitchild = true;
            }
            if(hitchild){
                newDrawingOrder[i]=drawingorder[i+1];
            }else{
                newDrawingOrder[i]=drawingorder[i];
            }
        }
        if(hitchild){
            newDrawingOrder[newDrawingOrder.length-1] = childIndex;
            drawingorder = newDrawingOrder;
        }else{
            newDrawingOrder[newDrawingOrder.length-1]= drawingorder[drawingorder.length-1];
        }



    }

    public void setupDrawingOrder(){
        drawingorder = new int[getChildCount()];
        for(int i=0;i<getChildCount();i++){
            drawingorder[i]=i;
        }
    }

    public int loadImages(String path){

        String[] paths = new String[0];
        try {
            paths = getContext().getAssets().list(path);



        } catch (IOException e) {
            e.printStackTrace();
        }




        bitmaps = new Bitmap[paths.length];
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Toast.makeText(getContext(), path, Toast.LENGTH_SHORT).show();
        for(int i=0;i<paths.length;i++){
            paths[i] = path + "/" + paths[i];
            Toast.makeText(getContext(), paths[i], Toast.LENGTH_SHORT).show();
            Log.d("ASSETS", "loadImages: " + paths[i]);

            bitmaps[i] = scaleDown(getBitmapFromAsset(getContext(),paths[i]),getMeasuredWidth(),false);
        }

    return paths.length;

    }


    public static Bitmap getBitmapFromAsset(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();

        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            Log.e("ERROR", "getBitmapFromAsset: " + e.toString() );
            // handle exception
        }

        return bitmap;
    }

    public int loadVideos(String path){

        List<String> paths = new ArrayList<String>();
        File directory = new File(path);

        File[] files = directory.listFiles();

        if(files==null){
            return 0;
        }

        for (int i = 0; i < files.length; ++i) {
            paths.add(files[i].getAbsolutePath());
        }

        bitmaps = new Bitmap[paths.size()];
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        for(int i=0;i<paths.size();i++){
            videoUris[i] = FileProvider.getUriForFile(getContext(), getContext().getPackageName(), new File(paths.get(i)));
        }


        return paths.size();

    }

}


