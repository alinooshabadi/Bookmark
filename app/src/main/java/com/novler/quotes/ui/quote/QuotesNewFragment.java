package com.novler.quotes.ui.quote;

import android.support.v4.widget.SwipeRefreshLayout;

import com.novler.quotes.presenter.HomePresenter;
import com.novler.quotes.ui.home.HomeActivity;

/**
 * Created by P on 2/21/2017.
 */

public class QuotesNewFragment extends BaseQuotesFragment {
  @Override
  public void getItems() {
    super.getItems();
    final HomePresenter presenter = new HomePresenter(((HomeActivity) getActivity()).service, this);
    presenter.getQuoteList();
    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {

        presenter.getQuoteList();
      }
    });
  }

}
