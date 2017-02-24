package com.novler.quotes.home.QuoteTabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

/**
 * Created by P on 2/21/2017.
 */

public class PagerAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                QuotesTrendingFragment tab0 = new QuotesTrendingFragment();
                return tab0;
            case 1:
                QuotesBestFragment tab1 = new QuotesBestFragment();
                return tab1;
            case 2:
                QuotesRandomFragment tab2 = new QuotesRandomFragment();
                return tab2;
            case 3:
                QuotesNewFragment tab3 = new QuotesNewFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
