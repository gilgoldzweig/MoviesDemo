package com.example.gilgoldzweig.moviedemo.modules.di.application;

import com.example.gilgoldzweig.moviedemo.modules.network.NetworkModule;
import com.example.gilgoldzweig.moviedemo.modules.viewmodels.MoviesViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {ApplicationModule.class, NetworkModule.class})
@Singleton
public interface ApplicationComponent {

    void inject(MoviesViewModel moviesViewModel);
}
