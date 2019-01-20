package com.example.gilgoldzweig.moviedemo.application;

import android.app.Application;

import com.example.gilgoldzweig.moviedemo.BuildConfig;
import com.example.gilgoldzweig.moviedemo.modules.di.application.ApplicationComponent;
import com.example.gilgoldzweig.moviedemo.modules.di.application.DaggerApplicationComponent;
import com.example.gilgoldzweig.moviedemo.modules.logging.Timber;
import com.github.anrwatchdog.ANRWatchDog;
import com.squareup.leakcanary.LeakCanary;

public final class MoviesApplication extends Application {

    private static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) return;

        LeakCanary.install(this);


        setApplicationComponent(DaggerApplicationComponent.create());

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        new ANRWatchDog()
                .setIgnoreDebugger(true)
                .setANRListener(Timber::crash)
                .setIgnoreDebugger(true)
                .setInterruptionListener(Timber::crash)
                .start();
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    private static void setApplicationComponent(ApplicationComponent applicationComponent) {
        MoviesApplication.applicationComponent = applicationComponent;
    }
}
