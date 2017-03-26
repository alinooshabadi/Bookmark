package com.novler.quotes;

import android.app.Application;

public class BookmarkApplication extends Application {
  String mLandingPageUrl = "https://cafebazaar.ir/app/com.novler.quotes/";

  public String getLandingPageUrl() {
    return mLandingPageUrl;
  }

  public void setLandingPageUrl(String mLandingPageUrl) {
    this.mLandingPageUrl = mLandingPageUrl;
  }

}
