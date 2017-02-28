package com.novler.quotes.util.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetworkChangeReceiver extends BroadcastReceiver {
  public NetworkChangeReceiver() {
  }

  @Override
  public void onReceive(final Context context, final Intent intent) {
    ObservableObject.getInstance().updateValue(intent);
  }
}