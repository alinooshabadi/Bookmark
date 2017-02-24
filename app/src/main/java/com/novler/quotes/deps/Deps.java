package com.novler.quotes.deps;

import com.novler.quotes.home.HomeActivity;
import com.novler.quotes.networking.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by P on 2/14/2017.
 */

@Singleton
@Component(modules = {NetworkModule.class,})
public interface Deps {
    void inject(HomeActivity homeActivity);
}