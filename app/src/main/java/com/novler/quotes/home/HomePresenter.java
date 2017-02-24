package com.novler.quotes.home;

import com.novler.quotes.models.QuoteListResponse;
import com.novler.quotes.networking.NetworkError;
import com.novler.quotes.networking.Service;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by P on 2/14/2017.
 */

public class HomePresenter {

    private final Service service;
    private final HomeView view;
    private CompositeSubscription subscriptions;

    public HomePresenter(Service service, HomeView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getQuoteList() {
        view.showWait();

        Subscription subscription = service.getQuoteList(new Service.GetQuoteListCallback() {
            @Override
            public void onSuccess(QuoteListResponse quoteListResponse) {
                view.removeWait();
                view.getListSuccess(quoteListResponse);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getAppErrorMessage());
            }
        });
        subscriptions.add(subscription);
    }

    public void getQuoteBestList() {
        view.showWait();

        Subscription subscription = service.getQuoteBestList(new Service.GetQuoteListCallback() {
            @Override
            public void onSuccess(QuoteListResponse quoteListResponse) {
                view.removeWait();
                view.getListSuccess(quoteListResponse);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getAppErrorMessage());
            }
        });
        subscriptions.add(subscription);
    }

    public void getQuoteTrendingList() {
        view.showWait();

        Subscription subscription = service.getQuoteTrendingList(new Service.GetQuoteListCallback() {
            @Override
            public void onSuccess(QuoteListResponse quoteListResponse) {
                view.removeWait();
                view.getListSuccess(quoteListResponse);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getAppErrorMessage());
            }
        });
        subscriptions.add(subscription);
    }

    public void getQuoteRandomList() {
        view.showWait();

        Subscription subscription = service.getQuoteRandomList(new Service.GetQuoteListCallback() {
            @Override
            public void onSuccess(QuoteListResponse quoteListResponse) {
                view.removeWait();
                view.getListSuccess(quoteListResponse);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.removeWait();
                view.onFailure(networkError.getAppErrorMessage());
            }
        });
        subscriptions.add(subscription);
    }

    public void onStop() {
        subscriptions.unsubscribe();
    }
}
