package com.novler.quotes.util.customLayout;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

public class RecyclerViewAnimator {

  private static final int INIT_DELAY = 1000;

  private static final int INIT_TENSION = 250;

  private static final int INIT_FRICTION = 18;

  private static final int SCROLL_TENSION = 300;

  private static final int SCROLL_FRICTION = 40;

  private int mHeight;
  private RecyclerView mRecyclerView;
  private SpringSystem mSpringSystem;

  private boolean mFirstViewInit = true;
  private int mLastPosition = -1;
  private int mStartDelay;

  public RecyclerViewAnimator(RecyclerView recyclerView) {
    mRecyclerView = recyclerView;
    mSpringSystem = SpringSystem.create();

    // Use height of RecyclerView to slide-in items from bottom.
    mHeight = mRecyclerView.getResources().getDisplayMetrics().heightPixels;

    mStartDelay = INIT_DELAY;
  }

  public void onCreateViewHolder(View item) {

    if (mFirstViewInit) {
      slideInBottom(item, mStartDelay, INIT_TENSION, INIT_FRICTION);
      mStartDelay += 70;
    }
  }

  public void onBindViewHolder(View item, int position) {

    if (!mFirstViewInit && position > mLastPosition) {
      slideInBottom(item, 0, SCROLL_TENSION, SCROLL_FRICTION);
      mLastPosition = position;
    }
  }

  private void slideInBottom(final View item,
                             final int delay,
                             final int tension,
                             final int friction) {
    // Move item far outside the RecyclerView
    item.setTranslationY(mHeight);

    Runnable startAnimation = new Runnable() {
      @Override
      public void run() {
        SpringConfig config = new SpringConfig(tension, friction);
        Spring spring = mSpringSystem.createSpring();
        spring.setSpringConfig(config);
        spring.addListener(new SimpleSpringListener() {
          @Override
          public void onSpringUpdate(Spring spring) {
            /**
             * Decrease translationY until 0.
             */
            float val = (float) (mHeight - spring.getCurrentValue());
            item.setTranslationY(val);
          }

          @Override
          public void onSpringEndStateChange(Spring spring) {
            mFirstViewInit = false;
          }
        });

        // Set the spring in motion; moving from 0 to height
        spring.setEndValue(mHeight);
      }
    };

    mRecyclerView.postDelayed(startAnimation, delay);
  }
}