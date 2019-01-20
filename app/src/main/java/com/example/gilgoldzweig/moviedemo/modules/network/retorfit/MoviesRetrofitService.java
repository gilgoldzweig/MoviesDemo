package com.example.gilgoldzweig.moviedemo.modules.network.retorfit;

import com.example.gilgoldzweig.moviedemo.models.MoviesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesRetrofitService {

    @GET("search/movie")
    Observable<MoviesResponse> searchMovies(
            @Query("query") String query,
            @Query("api_key") String apiKey);

}
