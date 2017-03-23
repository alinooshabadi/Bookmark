package com.novler.quotes;

import android.app.Application;

/**
 * Created by P on 3/15/2017.
 */

public class BookmarkApplication extends Application {
  public String getLandingPageUrl() {
    return mLandingPageUrl;
  }

  public void setLandingPageUrl(String mLandingPageUrl) {
    this.mLandingPageUrl = mLandingPageUrl;
  }

  String mLandingPageUrl = "https://cafebazaar.ir/app/com.novler.quotes/";

}
