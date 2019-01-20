package com.example.gilgoldzweig.moviedemo.modules.network;

import com.example.gilgoldzweig.moviedemo.modules.network.retorfit.MoviesRetrofitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.kotlin.KotlinModule;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class NetworkModule {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    @Provides
    @Singleton
    public JacksonConverterFactory provideJacksonConverterFactory() {
        return JacksonConverterFactory.create(new ObjectMapper().registerModule(new KotlinModule()));
    }

    @Provides
    @Singleton
    public RxJava2CallAdapterFactory provideRxJava2CallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }


    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(JacksonConverterFactory converterFactory,
                                    RxJava2CallAdapterFactory callAdapterFactory,
                                    OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .build();
    }


    @Provides
    @Singleton
    public MoviesRetrofitService provideMoviesRetrofitService(Retrofit retrofit) {
        return retrofit.create(MoviesRetrofitService.class);
    }
}
