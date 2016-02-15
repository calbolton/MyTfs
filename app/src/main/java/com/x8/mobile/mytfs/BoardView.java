package com.x8.mobile.mytfs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by calbo_000 on 2/15/2016.
 */
public class BoardView extends LinearLayout {

    public BoardView(Context context, AttributeSet attrs) {
        super(context);
        setOrientation(LinearLayout.HORIZONTAL);
    }

}
