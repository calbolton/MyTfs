package com.x8.mobile.mytfs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by calbo_000 on 2/15/2016.
 */
public class SwimlaneView extends ViewGroup {

    public SwimlaneView(Context context, AttributeSet attrs) {
        super(context);

        Drawable drawable = getResources().getDrawable(R.drawable.swimlaneborder);

        this.setBackgroundDrawable(drawable);

        final ViewGroup.MarginLayoutParams lpt =(MarginLayoutParams)getLayoutParams();
        lpt.setMargins(20,lpt.topMargin,lpt.rightMargin,lpt.bottomMargin);

        setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        layout(l,t,r, b);
    }
}
