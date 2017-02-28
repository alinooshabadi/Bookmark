package com.novler.quotes.util.customLayout;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.novler.quotes.util.FontUtil;

public class PersianTabLayout extends TabLayout {

  private Typeface mTypeface;

  public PersianTabLayout(Context context) {
    super(context);
    init();
  }

  public PersianTabLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public PersianTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    mTypeface = FontUtil.getTypeface(getContext(), FontUtil.FontType.IranSansLight);
  }

  @Override
  public void addTab(Tab tab) {
    super.addTab(tab);

    ViewGroup mainView = (ViewGroup) getChildAt(0);
    ViewGroup tabView = (ViewGroup) mainView.getChildAt(tab.getPosition());

    int tabChildCount = tabView.getChildCount();
    for (int i = 0; i < tabChildCount; i++) {
      View tabViewChild = tabView.getChildAt(i);
      if (tabViewChild instanceof TextView) {
        ((TextView) tabViewChild).setTypeface(mTypeface, Typeface.NORMAL);
      }
    }
  }
}