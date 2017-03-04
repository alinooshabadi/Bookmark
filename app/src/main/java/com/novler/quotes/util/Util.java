package com.novler.quotes.util;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.novler.quotes.R;

public class Util {

  private static String[] persianNumbers = new String[]{"۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹"};

  public static void applyFontedTab(Activity activity, ViewPager viewPager, TabLayout tabLayout) {
    for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
      TextView tv = (TextView) activity.getLayoutInflater().inflate(R.layout.activity_main, null);
      if (i == viewPager.getCurrentItem()) tv.setSelected(true);
      tv.setText(viewPager.getAdapter().getPageTitle(i));
      tabLayout.getTabAt(i).setCustomView(tv);
    }
  }

  public static void loadPosterBackground(@NonNull ImageView imageView) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      imageView.setImageAlpha(30);
    } else {
      //noinspection deprecation
      imageView.setAlpha(30);
    }
  }

  public static String clearText(String text) {
    return text.replace("&171;", "«").replace("&187;", "»");
  }

  public static String toPersianNumber(String text) {
    if (text.isEmpty())
      return "";
    String out = "";
    int length = text.length();
    for (int i = 0; i < length; i++) {
      char c = text.charAt(i);
      if ('0' <= c && c <= '9') {
        int number = Integer.parseInt(String.valueOf(c));
        out += persianNumbers[number];
      } else if (c == '٫') {
        out += '،';
      } else {
        out += c;
      }
    }
    return out;
  }
}
