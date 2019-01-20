package com.example.gilgoldzweig.moviedemo.modules.di.application;

import com.example.gilgoldzweig.moviedemo.modules.di.threads.ThreadsModule;

import dagger.Module;

@Module(includes = {ThreadsModule.class})
public class ApplicationModule {

}
