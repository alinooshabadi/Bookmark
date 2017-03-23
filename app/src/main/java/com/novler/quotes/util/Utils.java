package com.novler.quotes.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;

import com.novler.quotes.util.customLayout.FancyButton;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Utils {

  private static String[] persianNumbers = new String[]{"۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹"};


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

  private static Map<String, Typeface> cachedFontMap = new HashMap<String, Typeface>();

  public static int pxToSp(final Context context, final float px) {
    return Math.round(px / context.getResources().getDisplayMetrics().scaledDensity);
  }

  public static int spToPx(final Context context, final float sp) {
    return Math.round(sp * context.getResources().getDisplayMetrics().scaledDensity);
  }

  public static int dpToPx(final Context context,float dps){
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dps * scale + 0.5f);
  }


  public static Typeface findFont(Context context, String fontPath, String defaultFontPath){

    if (fontPath == null){
      return Typeface.DEFAULT;
    }

    String fontName = new File(fontPath).getName();
    String defaultFontName = "";
    if (!TextUtils.isEmpty(defaultFontPath)){
      defaultFontName = new File(defaultFontPath).getName();
    }

    if (cachedFontMap.containsKey(fontName)){
      return cachedFontMap.get(fontName);
    }else{
      try{
        AssetManager assets = context.getResources().getAssets();

        if (Arrays.asList(assets.list("")).contains(fontPath)){
          Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontName);
          cachedFontMap.put(fontName, typeface);
          return typeface;
        }else if (Arrays.asList(assets.list("fonts")).contains(fontName)){
          Typeface typeface = Typeface.createFromAsset(context.getAssets(), String.format("fonts/%s",fontName));
          cachedFontMap.put(fontName, typeface);
          return typeface;
        }else if (Arrays.asList(assets.list("iconfonts")).contains(fontName)){
          Typeface typeface = Typeface.createFromAsset(context.getAssets(), String.format("iconfonts/%s",fontName));
          cachedFontMap.put(fontName, typeface);
          return typeface;
        }else if (!TextUtils.isEmpty(defaultFontPath) && Arrays.asList(assets.list("")).contains(defaultFontPath)){
          Typeface typeface = Typeface.createFromAsset(context.getAssets(), defaultFontPath);
          cachedFontMap.put(defaultFontName, typeface);
          return typeface;
        } else {
          throw new Exception("Font not Found");
        }

      }catch (Exception e){
        Log.e(FancyButton.TAG, String.format("Unable to find %s font. Using Typeface.DEFAULT instead.", fontName));
        cachedFontMap.put(fontName, Typeface.DEFAULT);
        return Typeface.DEFAULT;
      }
    }
  }
}
