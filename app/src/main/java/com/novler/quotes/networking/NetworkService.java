package com.novler.quotes.networking;

import com.novler.quotes.models.ResponseData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by P on 2/14/2017.
 */

public interface NetworkService {
  @GET("quotes")
  Observable<ResponseData> getQuoteList();

  @GET("quotes/getbest")
  Observable<ResponseData> getQuoteBestList();

  @GET("quotes/getrandom")
  Observable<ResponseData> getQuoteRandomList();

  @GET("quotes/gettrending")
  Observable<ResponseData> getQuoteTrendingList();

  @GET("novels/getnovel/{id}")
  Observable<ResponseData> getNovel(@Path("id") String id);

  @GET("novels/getfeatured")
  Observable<ResponseData> getNovelFeatured();
}
