package com.novler.quotes.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.novler.quotes.BaseHomeApp;
import com.novler.quotes.R;
import com.novler.quotes.networking.Service;
import com.novler.quotes.ui.author.FeaturedAuthorsFragment;
import com.novler.quotes.ui.novel.FeaturedNovelsFragment;
import com.novler.quotes.util.FontUtil;

import javax.inject.Inject;

import butterknife.BindView;
import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

public class HomeActivity extends BaseHomeApp {
  @Inject
  public Service service;
  boolean doubleBackToExitPressedOnce = false;

  MainFragment mainFragment = new MainFragment();
  FeaturedAuthorsFragment featuredAuthorsFragment = new FeaturedAuthorsFragment();
  FeaturedNovelsFragment featuredNovelsFragment = new FeaturedNovelsFragment();
  @Nullable
  @BindView(R.id.toolbar)
  Toolbar toolbar;

  @BindView(R.id.layoutLinear)
  RelativeLayout linearLayout;

  @BindView(R.id.bottom_navigation)
  BottomNavigation mBottomNav;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getDeps().inject(this);

    renderView(savedInstanceState);
    setSupportActionBar(toolbar);



    AppUpdater appUpdater = new AppUpdater(this)
      .setUpdateFrom(UpdateFrom.JSON)
      .setDisplay(Display.DIALOG)
      .setUpdateJSON("https://novler.com/bookmark-changelog.json")
      .setTitleOnUpdateAvailable("نسخه جدید از برنامه موجود است")
      .setContentOnUpdateAvailable(" ")
      .setTitleOnUpdateNotAvailable("Update not available")
      .setContentOnUpdateNotAvailable("No update available. Check for updates again later!")
      .setButtonUpdate("دریافت نسخه جدید")
      .setButtonDismiss("بعدا")
      .setButtonDoNotShowAgain("دیگه نشون نده");

    //appUpdater.start();

  }

  @Override
  public void renderView(Bundle savedInstanceState) {
    setContentView(R.layout.activity_main);
    super.renderView(savedInstanceState);
    initializeBottomNavigation(savedInstanceState);
    if (savedInstanceState == null) {
      if (getSupportFragmentManager().findFragmentByTag("mainFragment") == null)
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, mainFragment, "mainFragment").commit();
    }
  }

  protected void initializeBottomNavigation(final Bundle savedInstanceState) {
    mBottomNav.setSelectedIndex(2, false);
    mBottomNav.setDefaultTypeface(FontUtil.getTypeface(getApplicationContext(), FontUtil.FontType.IranSansLight));
    mBottomNav.setOnMenuItemClickListener(new BottomNavigation.OnMenuItemSelectionListener() {
      @Override
      public void onMenuItemSelect(final int itemId, final int position, final boolean fromUser) {
        switch (itemId) {
          case R.id.action_quotes: {
            if (getSupportFragmentManager().findFragmentByTag("mainFragment") == null)
              getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, mainFragment, "mainFragment").commit();
            getSupportFragmentManager().beginTransaction().hide(featuredAuthorsFragment).commit();
            getSupportFragmentManager().beginTransaction().hide(featuredNovelsFragment).commit();
            getSupportFragmentManager().beginTransaction().show(mainFragment).commit();
            break;
          }
          case R.id.action_novels: {
            if (getSupportFragmentManager().findFragmentByTag("featuredNovelsFragment") == null)
              getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, featuredNovelsFragment, "featuredNovelsFragment").commit();
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FeaturedAuthorsFragment()).commit();
            getSupportFragmentManager().beginTransaction().hide(featuredAuthorsFragment).commit();
            getSupportFragmentManager().beginTransaction().hide(mainFragment).commit();
            getSupportFragmentManager().beginTransaction().show(featuredNovelsFragment).commit();
            break;
          }
          case R.id.action_authors: {
            if (getSupportFragmentManager().findFragmentByTag("featuredAuthorsFragment") == null)
              getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, featuredAuthorsFragment, "featuredAuthorsFragment").commit();
            getSupportFragmentManager().beginTransaction().hide(mainFragment).commit();
            getSupportFragmentManager().beginTransaction().hide(featuredNovelsFragment).commit();
            getSupportFragmentManager().beginTransaction().show(featuredAuthorsFragment).commit();
            break;
          }
        }

      }

      @Override public void onMenuItemReselect(@IdRes int i, int i1, boolean b) {

      }
    });
  }

  @Override
  public void onBackPressed() {

    if (doubleBackToExitPressedOnce) {
      super.onBackPressed();
      return;
    }

    this.doubleBackToExitPressedOnce = true;

    Toast.makeText(this, "برای خروج دوباره بازگشت را بزنید", Toast.LENGTH_SHORT).show();

    new Handler().postDelayed(new Runnable() {

      @Override
      public void run() {
        doubleBackToExitPressedOnce = false;
      }
    }, 2000);
  }

}
