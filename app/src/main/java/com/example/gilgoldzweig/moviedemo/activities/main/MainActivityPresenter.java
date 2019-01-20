package com.example.gilgoldzweig.moviedemo.activities.main;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;

import com.example.gilgoldzweig.moviedemo.base.BasePresenter;
import com.example.gilgoldzweig.moviedemo.models.Movie;
import com.example.gilgoldzweig.moviedemo.models.MoviesResponse;
import com.example.gilgoldzweig.moviedemo.models.livedata.StateData;
import com.example.gilgoldzweig.moviedemo.modules.viewmodels.MoviesViewModel;

import java.util.List;

public class MainActivityPresenter implements
        BasePresenter<MainActivityPresenterCallback> {

    @Nullable
    private MainActivityPresenterCallback callback;

    private MainActivity activity;

    private MoviesViewModel moviesViewModel;

    public MainActivityPresenter(@NonNull MainActivity activity) {
        this.activity = activity;
        callback = activity;
        moviesViewModel = ViewModelProviders
                .of(activity)
                .get(MoviesViewModel.class);

        activity.getLifecycle().addObserver(this);

        moviesViewModel.getSearchResultsLiveData()
                .observe(activity, this::handleSearchResults);
    }

    /**
     * Notifying the presenter that a click was made
     * Makes it easier to add tests and analytics events
     *
     * @param movie the element that was clicked
     */
    public void onMovieClicked(Movie movie) {
        if (callback != null) {
            callback.onMovieClicked(movie);
        }
    }

    /**
     * Performs search on the provided query are returns the result
     * on the ui thread
     *
     * @param query
     * @see MainActivityPresenter#handleSearchResults
     * @see MoviesViewModel#search(String)
     */
    public void search(String query) {
        moviesViewModel.performSearch(query);
    }

    @UiThread
    private void handleSearchResults(@NonNull StateData<MoviesResponse> moviesState) {
        if (callback == null) return;
        switch (moviesState.getStatus()) {
            case SUCCESS:
                MoviesResponse moviesResponse = moviesState.getData();
                if (moviesResponse != null) {
                    List<Movie> movies = moviesResponse.getMovies();
                    if (!movies.isEmpty()) {
                        callback.onSearchResultFound(movies);
                    } else {
                        callback.onNoSearchResultFound();
                    }
                }
                break;

            case ERROR:
                callback.onSearchFailed(moviesState.getError());
                break;

            case LOADING:
                callback.onLoadingMovies();
                break;

            case COMPLETE:
                break;
        }
    }

    @Nullable
    @Override
    public MainActivityPresenterCallback getCallback() {
        return callback;
    }

    //region lifecycle
    @Override
    public void onStart() {
        callback = activity;
    }

    @Override
    public void onStop() {
        callback = null;
    }

    @Override
    public void onDestroy() {
        callback = null;
        moviesViewModel = null;
        activity = null;
    }
    //endregion lifecycle
}
