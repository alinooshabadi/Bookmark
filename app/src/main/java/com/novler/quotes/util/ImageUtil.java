package com.novler.quotes.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;

/**
 * Created by P on 3/5/2017.
 */

public class ImageUtil {

  public static Bitmap createBlurBitmap(View viewContainer) {
    Bitmap bitmap = captureView(viewContainer);
    if (bitmap != null) {
      blurBitmapWithRenderscript(
        RenderScript.create(viewContainer.getContext()),
        bitmap);
    }
    return bitmap;
  }
  public static Bitmap captureView(View view) {
    //Create a Bitmap with the same dimensions as the View
    Bitmap image = Bitmap.createBitmap(view.getMeasuredWidth(),
      view.getMeasuredHeight(),
      Bitmap.Config.ARGB_4444); //reduce quality
    //Draw the view inside the Bitmap
    Canvas canvas = new Canvas(image);
    view.draw(canvas);

    //Make it frosty
    Paint paint = new Paint();
    paint.setXfermode(
      new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    ColorFilter filter =
      new LightingColorFilter(0xFFFFFFFF, 0x00222222); // lighten
    //ColorFilter filter =
    //   new LightingColorFilter(0xFF7F7F7F, 0x00000000); // darken
    paint.setColorFilter(filter);
    canvas.drawBitmap(image, 0, 0, paint);
    return image;
  }

  public static void blurBitmapWithRenderscript(
    RenderScript rs, Bitmap bitmap2) {
    // this will blur the bitmapOriginal with a radius of 25
    // and save it in bitmapOriginal
    // use this constructor for best performance, because it uses
    // USAGE_SHARED mode which reuses memory
    final Allocation input =
      Allocation.createFromBitmap(rs, bitmap2);
    final Allocation output = Allocation.createTyped(rs,
      input.getType());
    final ScriptIntrinsicBlur script =
      ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
    // must be >0 and <= 25
    script.setRadius(25f);
    script.setInput(input);
    script.forEach(output);
    output.copyTo(bitmap2);
  }
}
