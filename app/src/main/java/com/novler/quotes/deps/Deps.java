package com.novler.quotes.deps;

import com.novler.quotes.networking.NetworkModule;
import com.novler.quotes.ui.author.AuthorActivity;
import com.novler.quotes.ui.home.HomeActivity;
import com.novler.quotes.ui.novel.NovelActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class,})
public interface Deps {
  void inject(HomeActivity activity);
  void inject(NovelActivity activity);
  void inject(AuthorActivity activity);
  //void inject(FeaturedNovelsActivity activity);
  //void inject(FeaturedAuthorsActivity activity);
}