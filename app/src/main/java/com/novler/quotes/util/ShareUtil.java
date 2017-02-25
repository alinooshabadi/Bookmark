package com.novler.quotes.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

/**
 * Created by P on 2/23/2017.
 */

public class ShareUtil {
  public static void intentMessageTelegram(Activity activity, String msg) {
    final String appName = "org.telegram.messenger";
    final boolean isAppInstalled = isAppAvailable(activity.getApplicationContext(), appName);
    if (isAppInstalled) {
      Intent myIntent = new Intent(Intent.ACTION_SEND);
      myIntent.setType("text/plain");
      myIntent.setPackage(appName);
      myIntent.putExtra(Intent.EXTRA_TEXT, msg);//
      activity.startActivity(Intent.createChooser(myIntent, "Share with"));
    } else {
      Toast.makeText(activity, "Telegram not Installed", Toast.LENGTH_SHORT).show();
    }
  }

  private static boolean isAppAvailable(Context context, String appName) {
    PackageManager pm = context.getPackageManager();
    try {
      pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES);
      return true;
    } catch (PackageManager.NameNotFoundException e) {
      return false;
    }
  }
}
