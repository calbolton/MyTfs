package com.x8.mobile.mytfs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.x8.mobile.mytfs.R;

/**
 * Created by calbo_000 on 2/20/2016.
 */
public class WorkItemView extends View {

    public WorkItemView(Context context){
        super(context);
        this.setBackgroundColor(0xFF00FF00);
    }

    public WorkItemView(Context context, AttributeSet attrs){
        super(context);
//        Drawable drawable;
//        drawable = getResources().getDrawable(R.drawable.swimlaneborder);
//
//        this.setBackgroundDrawable(drawable);

        this.setBackgroundColor(0xFF00FF00);
    }
}
