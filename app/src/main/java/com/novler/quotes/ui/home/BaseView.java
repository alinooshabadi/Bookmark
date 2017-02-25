package com.novler.quotes.ui.home;

import com.novler.quotes.models.ResponseData;

/**
 * Created by P on 2/14/2017.
 */

public interface BaseView {
  void showWait();

  void removeWait();

  void onFailure(String appErrorMessage);

  void getListSuccess(ResponseData cityListResponse);

}
