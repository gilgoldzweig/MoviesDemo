package com.example.gilgoldzweig.moviedemo.activities.main;

import com.example.gilgoldzweig.moviedemo.base.BasePresenterCallBack;
import com.example.gilgoldzweig.moviedemo.models.Movie;

import java.util.List;

public interface MainActivityPresenterCallback extends BasePresenterCallBack {
    void onSearchResultFound(List<Movie> movies);
    void onNoSearchResultFound();
    void onLoadingMovies();
    void onSearchFailed(Throwable throwable);
    void onMovieClicked(Movie movie);

}
