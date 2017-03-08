package com.novler.quotes.networking;

import com.novler.quotes.models.ResponseData;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class Service {
  private final NetworkService networkService;

  public Service(NetworkService networkService) {
    this.networkService = networkService;
  }

  public Subscription getQuoteList(final GetListCallback callback) {
    return networkService.getQuoteList()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .onErrorResumeNext(new Func1<Throwable, Observable<? extends ResponseData>>() {
        @Override
        public Observable<? extends ResponseData> call(Throwable throwable) {
          return Observable.error(throwable);
        }
      })
      .subscribe(new Subscriber<ResponseData>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
          callback.onError(new NetworkError(e));

        }

        @Override
        public void onNext(ResponseData listResponse) {
          callback.onSuccess(listResponse);
        }
      });
  }

  public Subscription getQuoteBestList(final GetListCallback callback) {
    return networkService.getQuoteBestList()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .onErrorResumeNext(new Func1<Throwable, Observable<? extends ResponseData>>() {
        @Override
        public Observable<? extends ResponseData> call(Throwable throwable) {
          return Observable.error(throwable);
        }
      })
      .subscribe(new Subscriber<ResponseData>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
          callback.onError(new NetworkError(e));

        }

        @Override
        public void onNext(ResponseData listResponse) {
          callback.onSuccess(listResponse);
        }
      });
  }

  public Subscription getQuoteRandomList(final GetListCallback callback) {
    return networkService.getQuoteRandomList()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .onErrorResumeNext(new Func1<Throwable, Observable<? extends ResponseData>>() {
        @Override
        public Observable<? extends ResponseData> call(Throwable throwable) {
          return Observable.error(throwable);
        }
      })
      .subscribe(new Subscriber<ResponseData>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
          callback.onError(new NetworkError(e));

        }

        @Override
        public void onNext(ResponseData listResponse) {
          callback.onSuccess(listResponse);
        }
      });
  }

  public Subscription getQuoteTrendingList(final GetListCallback callback) {
    return networkService.getQuoteTrendingList()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .onErrorResumeNext(new Func1<Throwable, Observable<? extends ResponseData>>() {
        @Override
        public Observable<? extends ResponseData> call(Throwable throwable) {
          return Observable.error(throwable);
        }
      })
      .subscribe(new Subscriber<ResponseData>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
          callback.onError(new NetworkError(e));

        }

        @Override
        public void onNext(ResponseData listResponse) {
          callback.onSuccess(listResponse);
        }
      });
  }

  public Subscription getNovel(final GetListCallback callback, String id) {
    return networkService.getNovel(id)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .onErrorResumeNext(new Func1<Throwable, Observable<? extends ResponseData>>() {
        @Override
        public Observable<? extends ResponseData> call(Throwable throwable) {
          return Observable.error(throwable);
        }
      })
      .subscribe(new Subscriber<ResponseData>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
          callback.onError(new NetworkError(e));

        }

        @Override
        public void onNext(ResponseData listResponse) {
          callback.onSuccess(listResponse);
        }
      });
  }

  public Subscription getNovelFeaturedList(final GetListCallback callback) {
    return networkService.getNovelFeatured()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .onErrorResumeNext(new Func1<Throwable, Observable<? extends ResponseData>>() {
        @Override
        public Observable<? extends ResponseData> call(Throwable throwable) {
          return Observable.error(throwable);
        }
      })
      .subscribe(new Subscriber<ResponseData>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
          callback.onError(new NetworkError(e));

        }

        @Override
        public void onNext(ResponseData listResponse) {
          callback.onSuccess(listResponse);
        }
      });
  }

  public Subscription getAuthor(final GetListCallback callback, String id) {
    return networkService.getAuthor(id)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .onErrorResumeNext(new Func1<Throwable, Observable<? extends ResponseData>>() {
        @Override
        public Observable<? extends ResponseData> call(Throwable throwable) {
          return Observable.error(throwable);
        }
      })
      .subscribe(new Subscriber<ResponseData>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
          callback.onError(new NetworkError(e));

        }

        @Override
        public void onNext(ResponseData listResponse) {
          callback.onSuccess(listResponse);
        }
      });
  }

  public Subscription getAuthorsFeaturedList(final GetListCallback callback) {
    return networkService.getAuthorsFeatured()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .onErrorResumeNext(new Func1<Throwable, Observable<? extends ResponseData>>() {
        @Override
        public Observable<? extends ResponseData> call(Throwable throwable) {
          return Observable.error(throwable);
        }
      })
      .subscribe(new Subscriber<ResponseData>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
          callback.onError(new NetworkError(e));

        }

        @Override
        public void onNext(ResponseData listResponse) {
          callback.onSuccess(listResponse);
        }
      });
  }

  public interface GetListCallback {
    void onSuccess(ResponseData cityListResponse);

    void onError(NetworkError networkError);
  }
}
