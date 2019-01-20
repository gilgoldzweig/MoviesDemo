package com.example.gilgoldzweig.moviedemo.modules.di.threads;

import com.example.gilgoldzweig.moviedemo.models.threads.RxSchedulers;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class ThreadsModule {

    @Provides
    @Singleton
    RxSchedulers provideRxSchedulers() {
        return new RxSchedulers(
                Schedulers.single(),
                Schedulers.io(),
                Schedulers.io(),
                AndroidSchedulers.mainThread());
    }
}
