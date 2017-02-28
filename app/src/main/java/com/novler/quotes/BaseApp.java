package com.novler.quotes;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.novler.quotes.deps.DaggerDeps;
import com.novler.quotes.deps.Deps;
import com.novler.quotes.networking.NetworkModule;
import com.novler.quotes.util.network.NetworkUtil;
import com.novler.quotes.util.network.ObservableObject;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BaseApp extends AppCompatActivity implements Observer {
  Deps deps;
  FirebaseAnalytics mFirebaseAnalytics;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Fabric.with(this, new Crashlytics());
    mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    File cacheFile = new File(getCacheDir(), "responses");
    deps = DaggerDeps.builder().networkModule(new NetworkModule(cacheFile)).build();

    CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
      .setDefaultFontPath("fonts/IRANSansMobile_Light.ttf")
      .setFontAttrId(R.attr.fontPath).build());

    ObservableObject.getInstance().addObserver(this);
  }

  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }

  public Deps getDeps() {
    return deps;
  }

  @Override public void update(Observable o, Object arg) {
    String status = NetworkUtil.getConnectivityStatusString(getApplicationContext());
    //Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
  }
}