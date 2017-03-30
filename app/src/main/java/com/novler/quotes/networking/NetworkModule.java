package com.novler.quotes.networking;

import com.novler.quotes.BaseApp;
import com.novler.quotes.BuildConfig;
import com.novler.quotes.util.network.NetworkUtil;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module
public class NetworkModule {
  File cacheFile;

  public NetworkModule(File cacheFile) {
    this.cacheFile = cacheFile;
  }

  @Provides
  @Singleton
  Retrofit provideCall() {
    Cache cache = null;
    try {
      cache = new Cache(cacheFile, 10 * 1024 * 1024);
    } catch (Exception e) {
      e.printStackTrace();
    }

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
      .addInterceptor(new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
          Request request = chain.request();
          // Customize the request
          if (BaseApp.mNetworkStatus != NetworkUtil.TYPE_NOT_CONNECTED) {
            request = request.newBuilder()
              .header("Content-Type", "application/json")
              .removeHeader("Pragma")
              .header("Cache-Control", String.format("max-age=%d", BuildConfig.CACHETIME))
              .build();
          } else {
            request = request.newBuilder()
              .header("Content-Type", "application/json")
              .removeHeader("Pragma")
              .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
              .build();
          }

          okhttp3.Response response = chain.proceed(request);
          if (response.cacheControl() != null && response.code() == 200) {
            response.cacheResponse();
          } else if (response.networkResponse() != null) {
            response.networkResponse();
          }

          // Customize or return the response
          return response;
        }
      })
      .cache(cache)

      .build();

    return new Retrofit.Builder()
      .baseUrl(BuildConfig.BASEURL)
      .client(okHttpClient)
      .addConverterFactory(GsonConverterFactory.create())
      .addConverterFactory(ScalarsConverterFactory.create())
      .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

      .build();
  }

  @Provides
  @Singleton
  @SuppressWarnings("unused")
  public NetworkService providesNetworkService(
    Retrofit retrofit) {
    return retrofit.create(NetworkService.class);
  }

  @Provides
  @Singleton
  @SuppressWarnings("unused")
  public Service providesService(
    NetworkService networkService) {
    return new Service(networkService);
  }
}
