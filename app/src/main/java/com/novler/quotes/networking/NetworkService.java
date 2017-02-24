package com.novler.quotes.networking;

import com.novler.quotes.models.QuoteListResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by P on 2/14/2017.
 */

public interface NetworkService {
    @GET("quotes")
    Observable<QuoteListResponse> getQuoteList();

    @GET("quotes/getbest")
    Observable<QuoteListResponse> getQuoteBestList();

    @GET("quotes/getrandom")
    Observable<QuoteListResponse> getQuoteRandomList();

    @GET("quotes/gettrending")
    Observable<QuoteListResponse> getQuoteTrendingList();
}
