package com.novler.quotes.util;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.novler.quotes.R;

/**
 * Created by P on 2/14/2017.
 */

public class Util {
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

  public static String clearText(String text)
  {
    return  text.replace("&171;", "«").replace("&187;", "»");
  }
}
