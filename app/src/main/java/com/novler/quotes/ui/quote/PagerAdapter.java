package com.novler.quotes.ui.quote;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

public class PagerAdapter extends FragmentStatePagerAdapter {
  private int mNumOfTabs;

  public PagerAdapter(FragmentManager mgr, int NumOfTabs) {
    super(mgr);
    this.mNumOfTabs = NumOfTabs;
  }

  @Override
  public Fragment getItem(int position) {
    switch (position) {
      case 3:
        return new QuotesTrendingFragment();
      case 0:
        return new QuotesBestFragment();
      case 1:
        return new QuotesRandomFragment();
      case 2:
        return new QuotesNewFragment();
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
