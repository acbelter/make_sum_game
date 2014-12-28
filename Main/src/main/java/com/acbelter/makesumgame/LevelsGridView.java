package com.acbelter.makesumgame;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

public class LevelsGridView extends GridView {
    public LevelsGridView(Context context) {
        super(context);
    }

    public LevelsGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public LevelsGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        View child;
        for (int i = 0; i < getChildCount(); i++) {
            child = getChildAt(i);
            child.setMinimumHeight(child.getWidth());
        }
    }
}
