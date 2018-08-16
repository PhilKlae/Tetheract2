package com.example.philipp.tetheract.layouts;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class ControlledDrawningOrderLayout extends LinearLayout {

    int[] drawingorder;

    public ControlledDrawningOrderLayout(Context context) {
        super(context);
        setChildrenDrawingOrderEnabled(true);
        setupDrawingOrder();
    }

    public ControlledDrawningOrderLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setChildrenDrawingOrderEnabled(true);
        setupDrawingOrder();
    }

    public ControlledDrawningOrderLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setChildrenDrawingOrderEnabled(true);
        setupDrawingOrder();

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
}
