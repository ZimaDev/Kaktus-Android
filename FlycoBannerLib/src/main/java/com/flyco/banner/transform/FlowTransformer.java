package com.flyco.banner.transform;

import android.view.View;
import androidx.viewpager.widget.ViewPager;

public class FlowTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View page, float position) {
        page.setRotationY(position * -30f);
    }
}
