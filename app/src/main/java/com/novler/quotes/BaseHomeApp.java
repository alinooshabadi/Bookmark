package com.novler.quotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.novler.quotes.ui.author.FeaturedAuthorsActivity;
import com.novler.quotes.ui.home.HomeActivity;
import com.novler.quotes.ui.novel.FeaturedNovelsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.adad.client.Adad;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

/**
 * Created by P on 3/6/2017.
 */

public class BaseHomeApp extends BaseApp {
  @BindView(R.id.bottom_navigation)
  BottomNavigationView mBottomNav;

  private FirebaseRemoteConfig mFirebaseRemoteConfig;


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);


  }

  public void renderView() {
    ButterKnife.bind(this);
    remoteConfig();
    mBottomNav.setOnNavigationItemSelectedListener(
      new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
          switch (item.getItemId()) {
            case R.id.action_quotes:
              Intent intentHome = new Intent(getApplicationContext(), HomeActivity.class);
              intentHome.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
              startActivity(intentHome);
              break;

            case R.id.action_novels:
              Intent intent = new Intent(getApplicationContext(), FeaturedNovelsActivity.class);
              intent.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
              startActivity(intent);
              break;

            case R.id.action_authors:
              Intent intentAuthors = new Intent(getApplicationContext(), FeaturedAuthorsActivity.class);
              intentAuthors.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
              startActivity(intentAuthors);
              break;

          }
          return false;
        }
      });
  }

  private void remoteConfig() {
    mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
    FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
      .setDeveloperModeEnabled(BuildConfig.DEBUG)
      .build();
    mFirebaseRemoteConfig.setConfigSettings(configSettings);
    final Task<Void> fetch = mFirebaseRemoteConfig.fetch(
      BuildConfig.DEBUG?
        0: java.util.concurrent.TimeUnit.HOURS.toSeconds(12));

    fetch.addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
          if (task.isSuccessful()) {
            mFirebaseRemoteConfig.activateFetched();
          }

          boolean enableAddad = mFirebaseRemoteConfig.getBoolean("show_ads");
          if(enableAddad)
          {
            Adad.initialize(getApplicationContext());
          }
        }
      });
  }
}
