package com.novler.quotes.util;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.novler.quotes.R;

/**
 * Created by P on 2/14/2017.
 */

public class Util {
    public static void applyFontedTab(Activity activity, ViewPager viewPager, TabLayout tabLayout) {
        for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
            TextView tv = (TextView) activity.getLayoutInflater().inflate(R.layout.activity_main, null);
            if (i == viewPager.getCurrentItem()) tv.setSelected(true);
            tv.setText(viewPager.getAdapter().getPageTitle(i));
            tabLayout.getTabAt(i).setCustomView(tv);
        }
    }
}
