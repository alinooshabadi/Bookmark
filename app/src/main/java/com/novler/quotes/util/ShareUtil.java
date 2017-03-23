package com.novler.quotes.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import java.io.File;

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

  public static void intentMessage(Activity activity, String msg) {
    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
    sharingIntent.setType("text/plain");
    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, msg);
    activity.startActivity(Intent.createChooser(sharingIntent, "اشتراک با ..."));
  }

  public static void createInstagramIntent(Activity activity, String type, String mediaPath) {
    // Create the new Intent using the 'Send' action.
    Intent share = new Intent(Intent.ACTION_SEND);

    // Set the MIME type
    share.setType(type);

    // Create the URI from the media
    File media = new File(mediaPath);
    Uri uri = Uri.fromFile(media);

    // Add the URI to the Intent.
    share.putExtra(Intent.EXTRA_STREAM, uri);

    // Broadcast the Intent.
    activity.startActivity(Intent.createChooser(share, "Share to"));
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
