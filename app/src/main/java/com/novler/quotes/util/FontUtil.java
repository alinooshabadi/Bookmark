package com.novler.quotes.util;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by P on 2/22/2017.
 */

public class FontUtil {
  private static Typeface mGhasemFont;
  private static Typeface mIranLightFont;
  private static Typeface mIranBoldFont;
  private static Typeface mYekan;

  /**
   * @return Typeface Instance with the font passed as parameter
   */
  public static Typeface getTypeface(Context context, String typefaceName) {
    Typeface typeFace = null;

    try {
      if (typefaceName.equals(FontType.AGhasem.toString())) {
        if (mGhasemFont == null) {
          mGhasemFont = Typeface.createFromAsset(context.getAssets(), typefaceName);
        }
        typeFace = mGhasemFont;
      } else if (typefaceName.equals(FontType.IranSansLight.toString())) {
        if (mIranLightFont == null) {
          mIranLightFont = Typeface.createFromAsset(context.getAssets(), typefaceName);
        }
        typeFace = mIranLightFont;
      } else if (typefaceName.equals(FontType.IranSansBold.toString())) {
        if (mIranBoldFont == null) {
          mIranBoldFont = Typeface.createFromAsset(context.getAssets(), typefaceName);
        }
        typeFace = mIranBoldFont;
      }

    } catch (Exception ex) {
      typeFace = Typeface.DEFAULT;
    }

    return typeFace;
  }

  public static Typeface getTypeface(Context context, FontType typefaceName) {
    return getTypeface(context, typefaceName.toString());
  }

  public enum FontType {

    AGhasem {
      public String toString() {
        return "fonts/AGhasem.ttf";
      }
    },

    IranSansLight {
      public String toString() {
        return "fonts/IRANSansMobile.ttf";
      }
    },

    IranSansBold {
      public String toString() {
        return "fonts/IRANSansMobile_Bold.ttf";
      }
    },


  }
}
