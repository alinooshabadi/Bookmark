package com.novler.quotes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.novler.quotes.deps.DaggerDeps;
import com.novler.quotes.deps.Deps;
import com.novler.quotes.networking.NetworkModule;
import com.novler.quotes.util.network.NetworkUtil;
import com.novler.quotes.util.network.ObservableObject;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import io.fabric.sdk.android.Fabric;
import ir.adad.client.Adad;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BaseApp extends AppCompatActivity implements Observer {
  public static int mNetworkStatus;
  Deps deps;
  FirebaseAnalytics mFirebaseAnalytics;
  View.OnClickListener mOnClickListenerWifi;
  @Nullable
  @BindView(R.id.snackBarParent)
  CoordinatorLayout snackBarParent;
  private FirebaseRemoteConfig mFirebaseRemoteConfig;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Fabric.with(this, new Crashlytics());
    mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

    File cacheFile = new File(getCacheDir(), "responses");
    deps = DaggerDeps.builder().networkModule(new NetworkModule(cacheFile)).build();

    CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
      .setDefaultFontPath("fonts/IRANSansMobile.ttf")
      .setFontAttrId(R.attr.fontPath).build());

    ObservableObject.getInstance().addObserver(this);
    remoteConfig();
  }

  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }

  public Deps getDeps() {
    return deps;
  }

  @Override
  public void update(Observable o, Object arg) {
    mNetworkStatus = NetworkUtil.getConnectivityStatus(getApplicationContext());
    if (snackBarParent != null) {
      if (mNetworkStatus == NetworkUtil.TYPE_NOT_CONNECTED) {

       /* Snackbar snackbar = Snackbar.make(snackBarParent, NetworkUtil.getConnectivityStatusString(getApplicationContext()), Snackbar.LENGTH_INDEFINITE)
          .setAction("اینترنت WIFI", mOnClickListenerWifi)
          .setAction("اینترنت موبایل", mOnClickListenerWifi);

        snackbar.show();*/
      }
    }

    mOnClickListenerWifi = new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        startActivity(intent);
      }
    };
  }

  private void remoteConfig() {
    mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
    FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
      .setDeveloperModeEnabled(BuildConfig.DEBUG)
      .build();
    mFirebaseRemoteConfig.setConfigSettings(configSettings);
    final Task<Void> fetch = mFirebaseRemoteConfig.fetch(
      BuildConfig.DEBUG ?
        0 : java.util.concurrent.TimeUnit.HOURS.toSeconds(12));

    fetch.addOnCompleteListener(new OnCompleteListener<Void>() {
      @Override
      public void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful()) {
          mFirebaseRemoteConfig.activateFetched();
        }

        boolean enableAddad = mFirebaseRemoteConfig.getBoolean("show_ads");
        if (enableAddad) {
          Adad.initialize(getApplicationContext());
        }
        String landingPageUrl = mFirebaseRemoteConfig.getString("landing_page_url");
        if (landingPageUrl != null) {
          ((BookmarkApplication) getApplicationContext()).setLandingPageUrl(landingPageUrl);
        }
      }
    });
  }
}