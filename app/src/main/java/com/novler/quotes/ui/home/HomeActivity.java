package com.novler.quotes.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.view.Menu;
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

  @BindView(R.id.snackBarParent)
  CoordinatorLayout snackBarParent;
  @BindView(R.id.layoutLinear)
  RelativeLayout linearLayout;

  @BindView(R.id.bottom_navigation)
  BottomNavigation mBottomNav;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getDeps().inject(this);

    renderView(savedInstanceState);

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
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public void renderView(Bundle savedInstanceState) {
    setContentView(R.layout.activity_main);
    super.renderView(savedInstanceState);
    initializeBottomNavigation(savedInstanceState);
    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new MainFragment()).commit();
    }
  }

  protected void initializeBottomNavigation(final Bundle savedInstanceState) {
    mBottomNav.setSelectedIndex(2,false);
    mBottomNav.setDefaultTypeface(FontUtil.getTypeface(getApplicationContext(), FontUtil.FontType.IranSansLight));
    mBottomNav.setOnMenuItemClickListener(new BottomNavigation.OnMenuItemSelectionListener() {
      @Override
      public void onMenuItemSelect(final int itemId, final int position, final boolean fromUser) {
        switch (itemId) {
          case R.id.action_quotes: {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new MainFragment()).addToBackStack("main").commit();
            break;
          }
          case R.id.action_authors: {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FeaturedAuthorsFragment()).commit();
            break;
          }
          case R.id.action_novels: {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new FeaturedNovelsFragment()).commit();
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
