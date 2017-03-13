package com.ramotion.expandingcollection;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

public class ECPagerListCard extends ListView {

    private boolean scrollDisabled;

    public ECPagerListCard(Context context) {
        super(context);
    }

    public ECPagerListCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ECPagerListCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void addHeaderView(View v, Object data, boolean isSelectable) {
        super.addHeaderView(v, data, isSelectable);
    }

    @Override
    public void addHeaderView(View v) {
        super.addHeaderView(v);
    }

    public void expand() {

    }

    public void collapse() {

    }


}
