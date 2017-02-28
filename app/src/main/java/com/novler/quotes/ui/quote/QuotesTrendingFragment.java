package com.novler.quotes.ui.quote;


import android.support.v4.widget.SwipeRefreshLayout;

import com.novler.quotes.ui.home.HomeActivity;
import com.novler.quotes.presenter.HomePresenter;

/**
 * Created by P on 2/21/2017.
 */

public class QuotesTrendingFragment extends BaseQuotesFragment {
  @Override
  public void getItems() {
    final HomePresenter presenter = new HomePresenter(((HomeActivity) getActivity()).service, this);
    presenter.getQuoteTrendingList();
    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {

        presenter.getQuoteTrendingList();
      }
    });
  }
}
