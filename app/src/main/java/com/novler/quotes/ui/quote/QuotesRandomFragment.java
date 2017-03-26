package com.novler.quotes.ui.quote;

import android.support.v4.widget.SwipeRefreshLayout;

import com.novler.quotes.presenter.HomePresenter;
import com.novler.quotes.ui.home.HomeActivity;


public class QuotesRandomFragment extends BaseQuotesFragment {
  @Override
  public void getItems() {
    final HomePresenter presenter = new HomePresenter(((HomeActivity) getActivity()).service, this);
    presenter.getQuoteRandomList();
    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        presenter.getQuoteRandomList();
      }
    });
  }
}
