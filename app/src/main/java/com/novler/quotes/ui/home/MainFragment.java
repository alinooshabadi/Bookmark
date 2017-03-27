package com.novler.quotes.ui.home;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.novler.quotes.R;
import com.novler.quotes.ui.quote.PagerAdapter;
import com.novler.quotes.util.customLayout.PersianTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.novler.quotes.R.id.pager;

public class MainFragment extends Fragment {
  @Nullable
  @BindView(R.id.logo)
  TextView logo;
  @Nullable
  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @Nullable
  @BindView(pager)
  ViewPager viewPager;

  @BindView(R.id.tab_layout)
  PersianTabLayout tabLayout;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    setRetainInstance(true);
    View view = inflater.inflate(R.layout.fragment_main, container, false);
    ButterKnife.bind(this, view);

    init();

    /*swipeRefreshLayout.setColorSchemeResources(R.color.lineRed,
      R.color.lineBlue,
      R.color.lineOrange,
      R.color.linePurple);*/

    Typeface mTypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/AGhasem.ttf");
    logo.setTypeface(mTypeface);
    logo.setText("بوکمارکـ");

    return view;
  }

  public void init() {
    if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
      ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
      ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    tabLayout.addTab(tabLayout.newTab().setText("بهترین‌ها"));
    tabLayout.addTab(tabLayout.newTab().setText("تصادفی"));
    tabLayout.addTab(tabLayout.newTab().setText("جدید"));
    tabLayout.addTab(tabLayout.newTab().setText("منتخب"));

    tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

    PagerAdapter adapter = buildAdapter();
    viewPager.setAdapter(adapter);
    viewPager.setCurrentItem(tabLayout.getTabCount(), false);
    viewPager.setOffscreenPageLimit(4);
    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });

  }

  private PagerAdapter buildAdapter() {
    return (new PagerAdapter(getChildFragmentManager(), tabLayout.getTabCount()));
  }

}