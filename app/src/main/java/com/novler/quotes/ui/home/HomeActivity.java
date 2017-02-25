package com.novler.quotes.ui.home;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.novler.quotes.BaseApp;
import com.novler.quotes.R;
import com.novler.quotes.models.ResponseData;
import com.novler.quotes.networking.Service;
import com.novler.quotes.ui.quote.PagerAdapter;
import com.novler.quotes.util.PersianTabLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.novler.quotes.R.id.pager;


public class HomeActivity extends BaseApp implements BaseView {
  @Inject
  public Service service;
  boolean doubleBackToExitPressedOnce = false;

  @BindView(R.id.bottom_navigation)
  BottomNavigationView mBottomNav;
  @BindView(R.id.snackBarParent)
  CoordinatorLayout snackBarParent;
  @BindView(R.id.layoutLinear)
  RelativeLayout linearLayout;
  @BindView(R.id.logo)
  TextView logo;
  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(pager)
  ViewPager viewPager;
  @BindView(R.id.tab_layout)
  PersianTabLayout tabLayout;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getDeps().inject(this);

    renderView();
    init();

    Typeface mTypeface = Typeface.createFromAsset(getAssets(), "fonts/AGhasem.ttf");
    logo.setTypeface(mTypeface);
    logo.setText("بوکمارکـ");

    HomePresenter presenter = new HomePresenter(service, this);
    presenter.getQuoteList();
  }


  private void updateToolbarText(CharSequence text) {
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      // actionBar.setTitle(text);
    }
  }

  private int getColorFromRes(@ColorRes int resId) {
    return ContextCompat.getColor(this, resId);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  public void renderView() {
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

  }

  public void init() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);

    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayShowTitleEnabled(false);


    tabLayout.addTab(tabLayout.newTab().setText("داغ"));
    tabLayout.addTab(tabLayout.newTab().setText("بهترین‌ها"));
    tabLayout.addTab(tabLayout.newTab().setText("تصادفی"));
    tabLayout.addTab(tabLayout.newTab().setText("جدید"));
    tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


    PagerAdapter adapter = new PagerAdapter
      (getSupportFragmentManager(), tabLayout.getTabCount());
    viewPager.setAdapter(adapter);
    viewPager.setCurrentItem(tabLayout.getTabCount(), false);
    viewPager.setOffscreenPageLimit(4);
    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });

    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
  }

  @Override
  public void showWait() {

  }

  @Override
  public void removeWait() {

  }

  @Override
  public void onFailure(String appErrorMessage) {
    Snackbar
      .make(snackBarParent, appErrorMessage, Snackbar.LENGTH_LONG).show();

  }

  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }

  @Override
  public void getListSuccess(ResponseData cityListResponse) {


  }

  @Override
  public void onBackPressed() {

    if (doubleBackToExitPressedOnce) {
      super.onBackPressed();
      return;
    }

    this.doubleBackToExitPressedOnce = true;
    Snackbar snackbar = Snackbar
      .make(snackBarParent, "برای خروج دوباره بازگشت را بزنید", Snackbar.LENGTH_LONG);

    snackbar.show();


    new Handler().postDelayed(new Runnable() {

      @Override
      public void run() {
        doubleBackToExitPressedOnce = false;
      }
    }, 2000);
  }


}
