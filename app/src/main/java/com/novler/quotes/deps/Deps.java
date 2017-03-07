package com.novler.quotes.deps;

import com.novler.quotes.networking.NetworkModule;
import com.novler.quotes.ui.novel.FeaturedNovelsActivity;
import com.novler.quotes.ui.home.HomeActivity;
import com.novler.quotes.ui.novel.NovelActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class,})
public interface Deps {
  void inject(HomeActivity homeActivity);

  void inject(NovelActivity novelActivity);

  void inject(FeaturedNovelsActivity activity);
}