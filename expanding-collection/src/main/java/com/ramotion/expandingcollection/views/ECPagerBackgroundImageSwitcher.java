package com.ramotion.expandingcollection.views;

import android.content.Context;
import android.widget.ImageSwitcher;

public class ECPagerBackgroundImageSwitcher extends ImageSwitcher {

    private final int[] REVERSE_ORDER = new int[]{1, 0};
    private final int[] NORMAL_ORDER = new int[]{0, 1};

    private boolean reverseDrawOrder;

    public ECPagerBackgroundImageSwitcher(Context context) {
        super(context);
        setChildrenDrawingOrderEnabled(true);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        if (reverseDrawOrder)
            return REVERSE_ORDER[i];
        else
            return NORMAL_ORDER[i];
    }

    public boolean isReverseDrawOrder() {
        return reverseDrawOrder;
    }

    public void setReverseDrawOrder(boolean reverseDrawOrder) {
        this.reverseDrawOrder = reverseDrawOrder;
    }
}
