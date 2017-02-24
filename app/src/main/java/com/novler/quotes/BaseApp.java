package com.novler.quotes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.novler.quotes.deps.DaggerDeps;
import com.novler.quotes.deps.Deps;
import com.novler.quotes.networking.NetworkModule;

import java.io.File;

public class BaseApp  extends AppCompatActivity {
    Deps deps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File cacheFile = new File(getCacheDir(), "responses");
        deps = DaggerDeps.builder().networkModule(new NetworkModule(cacheFile)).build();

    }

    public Deps getDeps() {
        return deps;
    }
}