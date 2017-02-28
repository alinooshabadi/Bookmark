package com.novler.quotes.util.customLayout;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by P on 2/28/2017.
 */

public class EmptySwipeRefreshLayout extends SwipeRefreshLayout {
  public EmptySwipeRefreshLayout(Context context) {
    super(context);
  }

  public EmptySwipeRefreshLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public boolean canChildScrollUp() {
    ViewGroup target = (ViewGroup) getChildAt(0);
    // check if adapter view is visible
    View scrollableView = target.getChildAt(1);
    if (scrollableView.getVisibility() == GONE) {
      // use empty view layout instead
      scrollableView = target.getChildAt(0);
    }

    return ViewCompat.canScrollVertically(scrollableView, -1);
  }
}
