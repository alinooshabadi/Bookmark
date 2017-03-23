package com.novler.quotes;

import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

public class BaseHomeApp extends BaseApp {
  @BindView(R.id.bottom_navigation)
  BottomNavigation mBottomNav;



  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  public void renderView(Bundle savedInstanceState) {
    ButterKnife.bind(this);

  }

}
