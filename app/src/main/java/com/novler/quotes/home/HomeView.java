package com.novler.quotes.home;

import com.novler.quotes.models.QuoteListResponse;

/**
 * Created by P on 2/14/2017.
 */

public interface HomeView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getListSuccess(QuoteListResponse cityListResponse);

}
