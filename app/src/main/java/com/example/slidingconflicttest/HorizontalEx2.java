package com.example.slidingconflicttest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class HorizontalEx2 extends ViewGroup {
    //分别记录上次滑动的坐标
    private int lastX,lastY;
    //分别记录上次滑动的坐标（onInterceptTouchEvent）
    private int mLastXIntercept,mLastYIntercept;
    private int childIndex;
    private Scroller mScroller; //用于弹性滑动
    private VelocityTracker mVelocityTracker;   //速度追踪

    public HorizontalEx2(Context context) {
        super(context);
        init(context);
    }

    public HorizontalEx2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HorizontalEx2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
        mVelocityTracker = VelocityTracker.obtain();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int childCount = getChildCount();
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        if (childCount == 0) {              //如果没有子元素
            setMeasuredDimension(0, 0);
        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {  //宽/高是否采取了wrap_content
            height = getChildAt(0).getMeasuredHeight();
            width = childCount * getChildAt(0).getMeasuredWidth();
            setMeasuredDimension(width, height);
        } else if (widthMode == MeasureSpec.AT_MOST) {                          //宽是否采取了wrap_content，是的话 宽是所有子元素之和
            width = childCount * getChildAt(0).getMeasuredWidth();
            setMeasuredDimension(width, height);
        } else if(heightMode == MeasureSpec.AT_MOST){                            //高是否采取wrap_content， 是的话 高是第一个元素的高
            height = getChildAt(0).getMeasuredHeight();
            setMeasuredDimension(width, height);
        }else{
            setMeasuredDimension(width, height);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int leftOffset = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if(child.getVisibility() != View.GONE){
                child.layout(l + leftOffset, t, r + leftOffset, b);
                leftOffset += child.getMeasuredWidth();
            }

        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            return false;
        }else {
            return true;
        }

    }
    //private boolean isFirstTouch = true;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int)event.getX();  //相对当前View左上角的x和y坐标
        int y = (int)event.getY();

        mVelocityTracker.addMovement(event);
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                /*
                if(isFirstTouch){
                    isFirstTouch = false;      //在开始滑动时更新lastX,lastY
                    lastX = x;
                    lastY = y;
                }

                 */
                final int deltaX = x-lastX;
                scrollBy(-deltaX,0);
                break;
            case MotionEvent.ACTION_UP:
                //isFirstTouch = true;
                int scrollX = getScrollX();
                mVelocityTracker.computeCurrentVelocity(1000, configuration.getScaledMaximumFlingVelocity());
                float mVelocityX = mVelocityTracker.getXVelocity();     //水平速度

                if(Math.abs(mVelocityX) > configuration.getScaledMinimumFlingVelocity()){
                    childIndex = mVelocityX < 0  ? childIndex+1 : childIndex-1;
                }else{
                    childIndex = (scrollX + getChildAt(0).getWidth() / 2) / getChildAt(0).getWidth();
                }
                childIndex = Math.min(getChildCount() - 1, Math.max(0, childIndex));
                smoothScrollBy(childIndex*getChildAt(0).getWidth()-scrollX,0);
                mVelocityTracker.clear();
                break;
        }
        lastX = x;
        lastY = y;
        return true;
    }
    private void smoothScrollBy(int dx, int dy) {
        mScroller.startScroll(getScrollX(), getScrollY(), dx, dy,500);
        invalidate();
    }
    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mVelocityTracker.recycle();
    }


}
