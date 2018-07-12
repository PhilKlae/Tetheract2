package com.example.philipp.tetheract.layouts;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

public class SmoothScroll extends ScrollView {

    boolean scrolling= false;

    public SmoothScroll(Context context) {
        super(context);
        setSmoothScrollingEnabled(true);
    }

    public SmoothScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
        setSmoothScrollingEnabled(true);
    }

    public SmoothScroll(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setSmoothScrollingEnabled(true);

    }
    @Override
    public void requestChildFocus(View child, View focused) {
        // avoid scrolling to focused view
        //setSmoothScrollingEnabled(true);
       super.requestChildFocus(child, focused);
        //int[] test = new int[2];
         //focused.getLocationOnScreen(test);
        //Rect rect;
        //rect = new Rect();
      //  focused.getDrawingRect(rect);
        // requestChildRectangleOnScreen(focused,rect,false);
      //  Log.d("test", "requestChildFocus: " + (test[1]- getScrollY()));

    //    smoothScrollBy(0, test[1] - focused.getHeight() - getScrollY());
        //Log.d("test", "requestChildFocus: requested");
      //  smoothScrollTo(0,(int)focused.getY());

        /*
         if (!mIsLayoutDirty) {
            scrollToChild(focused);
        } else {
            // The child may not be laid out yet, we can't compute the scroll yet
            mChildToScrollTo = focused;
        }
        super.requestChildFocus(child, focused);
        */



    }
  /*  @Override
    public boolean requestChildRectangleOnScreen(View child, Rect rectangle,
                                                 boolean immediate) {
        // offset into coordinate space of this scroll view
        rectangle.offset(child.getLeft() - child.getScrollX(),
                child.getTop() - child.getScrollY());
        return scrollToChildRect(rectangle, immediate);
    }

    private boolean scrollToChildRect(Rect rect, boolean immediate) {
        Log.d("test", "scrollToChildRect: scrolled");
        rect.bottom *=1.2;
        final int delta = computeScrollDeltaToGetChildRectOnScreen(rect);
        final boolean scroll = delta != 0;
        if (scroll) {
            if (immediate) {
                scrollBy(0, delta);
            } else {
                smoothScrollBy(0, delta);
            }
        }
        return scroll;
    }*/


    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(android.graphics.Rect rect){
        setSmoothScrollingEnabled(true);
        rect.top +=rect.height() - (rect.height()*1.2);
        rect.bottom -=rect.height() - (rect.height()*1.2);
        return super.computeScrollDeltaToGetChildRectOnScreen(rect);
    }


    @Override
    public void scrollTo(int x, int y){
        /*if(!scrolling){

            smoothScrollTo(x,y);
            scrolling = true;
        }else{
        }*/
       // smoothScrollBy(x,y);
        super.scrollTo(x, y);
    }
/*
    @Override
    protected boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        return false;
    }

    @Override
    public void setSmoothScrollingEnabled(boolean smoothScrollingEnabled) {
        super.setSmoothScrollingEnabled(smoothScrollingEnabled);
    }*/
}
