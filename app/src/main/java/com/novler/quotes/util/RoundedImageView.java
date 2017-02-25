package com.novler.quotes.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.novler.quotes.R;

public class RoundedImageView extends ImageView {

  public float mRadius;

  public RoundedImageView(Context context) {
    super(context);
  }

  public RoundedImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
    TypedArray a = context.getTheme().obtainStyledAttributes(
      attrs,
      R.styleable.RoundedImageView,
      0, 0);

    try {
      mRadius = a.getFloat(R.styleable.RoundedImageView_radius, 0);
    } finally {
      a.recycle();
    }
  }

  public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    float radius = mRadius; // angle of round corners
    Path clipPath = new Path();
    RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
    clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
    canvas.clipPath(clipPath);
    super.onDraw(canvas);
  }
}