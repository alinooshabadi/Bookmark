package com.novler.quotes.ui.quote;

import android.support.v4.widget.SwipeRefreshLayout;

import com.novler.quotes.presenter.HomePresenter;
import com.novler.quotes.ui.home.HomeActivity;

public class QuotesBestFragment extends BaseQuotesFragment {
  @Override
  public void getItems() {
    super.getItems();
    final HomePresenter presenter = new HomePresenter(((HomeActivity) getActivity()).service, this);

    presenter.getQuoteBestList();

    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        presenter.getQuoteBestList();
      }
    });
  }
}
