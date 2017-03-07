package com.novler.quotes.presenter;

import com.novler.quotes.models.ResponseData;
import com.novler.quotes.networking.NetworkError;
import com.novler.quotes.networking.Service;
import com.novler.quotes.ui.home.BaseView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class HomePresenter {

  private final Service service;
  private final BaseView view;
  private CompositeSubscription subscriptions;

  public HomePresenter(Service service, BaseView view) {
    this.service = service;
    this.view = view;
    this.subscriptions = new CompositeSubscription();
  }

  public void getQuoteList() {
    view.showWait();

    Subscription subscription = service.getQuoteList(new Service.GetQuoteListCallback() {
      @Override
      public void onSuccess(ResponseData quoteListResponse) {
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
      public void onSuccess(ResponseData quoteListResponse) {
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
      public void onSuccess(ResponseData quoteListResponse) {
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
      public void onSuccess(ResponseData quoteListResponse) {
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

  public void getNovel(String id) {
    view.showWait();

    Subscription subscription = service.getNovel(new Service.GetQuoteListCallback() {
      @Override
      public void onSuccess(ResponseData quoteListResponse) {
        view.removeWait();
        view.getListSuccess(quoteListResponse);
      }

      @Override
      public void onError(NetworkError networkError) {
        view.removeWait();
        view.onFailure(networkError.getAppErrorMessage());
      }
    }, id);
    subscriptions.add(subscription);
  }

  public void getNovelFeaturedList() {
    view.showWait();

    Subscription subscription = service.getNovelFeaturedList(new Service.GetQuoteListCallback() {
      @Override
      public void onSuccess(ResponseData quoteListResponse) {
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

  public void getAuthorsFeaturedList() {
    view.showWait();

    Subscription subscription = service.getAuthorsFeaturedList(new Service.GetQuoteListCallback() {
      @Override
      public void onSuccess(ResponseData quoteListResponse) {
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
