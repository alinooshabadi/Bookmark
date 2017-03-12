package com.novler.quotes;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.novler.quotes.ui.home.HomeActivity;
import com.novler.quotes.util.FontUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseApp {

  @BindView(R.id.splash_bookmark)
  TextView bookmark;
  @BindView(R.id.splash_novler_link)
  TextView novlerLink;
  @BindView(R.id.splash_novler_text)
  TextView novlerText;
  @BindView(R.id.banner)
  TextView mBanner;
  @BindView(R.id.banner2)
  TextView mBanner2;
  @BindView(R.id.version)
  TextView tvVersion;



  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    ButterKnife.bind(this);
    bookmark.setTypeface(FontUtil.getTypeface(getApplicationContext(), FontUtil.FontType.AGhasem));
    novlerLink.setTypeface(FontUtil.getTypeface(getApplicationContext(), FontUtil.FontType.IranSansBold));
    novlerText.setTypeface(FontUtil.getTypeface(getApplicationContext(), FontUtil.FontType.IranSansLight));
    mBanner.setTypeface(FontUtil.getTypeface(getApplicationContext(), FontUtil.FontType.IranSansBold));
    mBanner2.setTypeface(FontUtil.getTypeface(getApplicationContext(), FontUtil.FontType.IranSansBold));


    String versionName = "";
    try {
      versionName = getApplicationContext()
        .getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).versionName;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }

    tvVersion.setText("v "+versionName);

    int SPLASH_DISPLAY_LENGTH = BuildConfig.DEBUG?0: 2500;
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
                /* Create an Intent that will start the Menu-Activity. */
        Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
        SplashActivity.this.startActivity(mainIntent);
        SplashActivity.this.finish();
      }
    }, SPLASH_DISPLAY_LENGTH);
  }


}
