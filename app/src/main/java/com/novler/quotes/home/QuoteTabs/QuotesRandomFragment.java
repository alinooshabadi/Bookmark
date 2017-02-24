package com.novler.quotes.home.QuoteTabs;


import android.support.v4.widget.SwipeRefreshLayout;

import com.novler.quotes.home.HomeActivity;
import com.novler.quotes.home.HomePresenter;

/**
 * Created by P on 2/21/2017.
 */

public class QuotesRandomFragment extends BaseQuotesFragment{
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
