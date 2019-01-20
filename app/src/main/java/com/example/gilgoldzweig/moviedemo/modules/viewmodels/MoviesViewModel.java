package com.example.gilgoldzweig.moviedemo.modules.viewmodels;

import android.arch.lifecycle.ViewModel;

import com.example.gilgoldzweig.moviedemo.BuildConfig;
import com.example.gilgoldzweig.moviedemo.application.MoviesApplication;
import com.example.gilgoldzweig.moviedemo.models.MoviesResponse;
import com.example.gilgoldzweig.moviedemo.models.livedata.StateLiveData;
import com.example.gilgoldzweig.moviedemo.models.threads.RxSchedulers;
import com.example.gilgoldzweig.moviedemo.modules.network.retorfit.MoviesRetrofitService;
import com.jakewharton.rxrelay2.PublishRelay;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class MoviesViewModel extends ViewModel {

    @Inject
    RxSchedulers rxSchedulers;

    @Inject
    MoviesRetrofitService moviesRetrofitService;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final PublishRelay<String> searchPublishSubject = PublishRelay.create();
    private StateLiveData<MoviesResponse> searchResultsLiveData = new StateLiveData<>();


    public MoviesViewModel() {
        MoviesApplication.getApplicationComponent().inject(this);
        configureSearch();
    }

    /**
     * Sends the query to the rx relay
     * @param query
     */
    public void performSearch(String query) {
        searchPublishSubject.accept(query.trim());
    }

    /**
     * Configures the search functionality
     *
     * requesting publishing updates from the [searchPublishSubject]
     * debouncing: the request by 300 milliseconds that way I don't need to search on every change,
     * only when they have kinda wanted to search
     *
     * distinctUntilChanged: Checks if the last value is equals to the current value
     *
     * switchMap: Performs the actual search using the search function
     *
     *
     * @see MoviesViewModel#search(String)
     */
    private void configureSearch() {
        compositeDisposable.add(searchPublishSubject
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMap(this::search)
                .subscribeOn(rxSchedulers.getNetwork())
                .observeOn(rxSchedulers.getMain())
                .subscribe(
                        searchResultsLiveData::postSuccess,
                        searchResultsLiveData::postError));
    }

    /**
     * Performs a search using retrofit
     * We know that the value here is valid and debounced
     * Notifies that we started loading
     *
     * @param query the search query
     * @return the result of the search query from the module
     * @see MoviesRetrofitService#searchMovies(String, String)
     */
    private Observable<MoviesResponse> search(String query) {
        if (query.isEmpty()) {
            return Observable.just(new MoviesResponse());
        }

        searchResultsLiveData.postLoading();

        return moviesRetrofitService.searchMovies(query, BuildConfig.TMDB_API_KEY)
                .onErrorReturn(throwable -> new MoviesResponse());
    }


    public StateLiveData<MoviesResponse> getSearchResultsLiveData() {
        return searchResultsLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
