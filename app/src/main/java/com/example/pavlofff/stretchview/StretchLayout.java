package com.example.pavlofff.stretchview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

public class StretchLayout extends FrameLayout {
    final float DEFAULT_RATIO = 0.5F;
    float mTouchY;
    float mTranslateY;
    float mMargin;
    float mRatio;
    View mStretchView;
    View mFrontView;

    public StretchLayout(Context context) {
        super(context);
        init();
    }

    public StretchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StretchLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mRatio = DEFAULT_RATIO;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mTouchY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mTranslateY = event.getY() - mTouchY;
                if (mTranslateY >= mMargin) mTranslateY = mMargin;
                if (mTranslateY < 0) mTranslateY = 0;
                mFrontView.setTranslationY(mTranslateY);
                mStretchView.setTranslationY(mTranslateY / 2);
                break;
            case MotionEvent.ACTION_UP:
                mTranslateY = 0;
                mFrontView.animate().translationY(mTranslateY).setInterpolator(new DecelerateInterpolator());
                mStretchView.animate().translationY(mTranslateY).setInterpolator(new DecelerateInterpolator());
                break;
        }
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mStretchView = getChildAt(0);
        mFrontView = getChildAt(1);
        mMargin = mStretchView.getMeasuredHeight() * mRatio;
        mFrontView.setTop((int) (mMargin));
        mStretchView.setTop((int) (-mMargin / 2));
    }

    protected void setRatio(float ratio) {
        mRatio = ratio;
    }
}
