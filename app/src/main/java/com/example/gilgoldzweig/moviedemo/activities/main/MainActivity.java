package com.example.gilgoldzweig.moviedemo.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.example.gilgoldzweig.moviedemo.R;
import com.example.gilgoldzweig.moviedemo.activities.details.DetailsActivity;
import com.example.gilgoldzweig.moviedemo.activities.main.adapters.MoviesRecyclerAdapter;
import com.example.gilgoldzweig.moviedemo.models.Movie;
import com.example.gilgoldzweig.moviedemo.ui.views.simplesearchview.SimpleSearchView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityPresenterCallback {


    private static final String SAVED_RECYCLER_VIEW_STATUS_ID = "saved_recycler_view_status_id";
    private static final String SAVED_RECYCLER_VIEW_DATA_SET_ID = "saved_recycler_view_data_set_id";
    private static final String SAVED_SEARCH_VIEW_ID = "saved_search_view_id";
    private static final String SAVED_TOOLBAR_VIEW_TITLE_ID = "saved_toolbar_view_title_id";

    private String lastQuery = "";

    private View rootView;
    private Toolbar toolbar;
    private SimpleSearchView moviesSearchView;
    private RecyclerView moviesRecyclerView;
    private ProgressBar moviesLoadingProgressBar;
    private AppCompatImageView moviesEmptyStateImageView;
    private MainActivityPresenter presenter;
    private MoviesRecyclerAdapter moviesRecyclerAdapter;
    private GridLayoutManager gridLayoutManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        attachPresenter();

        rootView = findViewById(R.id.main_activity_root);
        toolbar = findViewById(R.id.main_activity_toolbar);
        moviesSearchView = findViewById(R.id.main_activity_search_bar);
        moviesRecyclerView = findViewById(R.id.main_activity_movies_rcv);
        moviesLoadingProgressBar = findViewById(R.id.main_activity_movies_progress);
        moviesEmptyStateImageView = findViewById(R.id.main_activity_movies_state_image);

        toolbar.inflateMenu(R.menu.main_activity_search_menu);

        toolbar.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.main_activity_menu_search_item) {
                moviesSearchView.showSearch(true);
                if (!lastQuery.isEmpty()) {
                    moviesSearchView.setQuery(lastQuery, false);
                }
            }
            return true;
        });


        moviesSearchView.enableVoiceSearch(true);
        moviesSearchView.setOnQueryTextListener(new SimpleSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (!query.isEmpty()) {
                    presenter.search(query);
                    lastQuery = query;
                }
                return false;
            }

            @Override
            public boolean onQueryTextCleared() {
                return false;
            }
        });

        moviesSearchView.setOnSearchViewListener(new SimpleSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
            }

            @Override
            public void onSearchViewClosed() {
                if (lastQuery.isEmpty()) {
                    toolbar.setTitle(R.string.app_name);
                } else {
                    toolbar.setTitle(lastQuery);
                }
            }

            @Override
            public void onSearchViewShownAnimation() {

            }

            @Override
            public void onSearchViewClosedAnimation() {
            }
        });


        moviesRecyclerAdapter = new MoviesRecyclerAdapter(this, presenter::onMovieClicked);
        gridLayoutManager = new GridLayoutManager(this, 2);
        moviesRecyclerView.setLayoutManager(gridLayoutManager);
        moviesRecyclerView.setAdapter(moviesRecyclerAdapter);

        if (savedInstanceState != null) {
            restorePreviousState(savedInstanceState);
        }
    }

    @Override
    public void onSearchResultFound(List<Movie> movies) {
        show(moviesRecyclerView);
        hide(moviesEmptyStateImageView);
        hide(moviesLoadingProgressBar);
        moviesRecyclerAdapter.updateList(movies);

    }

    @Override
    public void onNoSearchResultFound() {
        show(moviesEmptyStateImageView);
        hide(moviesRecyclerView);
        hide(moviesLoadingProgressBar);
    }

    @Override
    public void onLoadingMovies() {
        show(moviesLoadingProgressBar);
        hide(moviesRecyclerView);
        hide(moviesEmptyStateImageView);
    }

    @Override
    public void onSearchFailed(Throwable throwable) {
        Snackbar.make(rootView, R.string.search_failed, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onMovieClicked(Movie movie) {
        Intent movieDetailsIntent =
                new Intent(MainActivity.this, DetailsActivity.class);

        movieDetailsIntent.putExtra(DetailsActivity.SELECTED_MOVIE_INTENT_KEY, movie);

        startActivity(movieDetailsIntent);
    }

    private void hide(View view) {
        view.setVisibility(View.GONE);
    }

    private void show(View view) {
        view.setVisibility(View.VISIBLE);
    }

    private void attachPresenter() {
        presenter = (MainActivityPresenter) getLastCustomNonConfigurationInstance();
        if (presenter == null) {
            presenter = new MainActivityPresenter(this);
        }
    }

    /**
     * Restores the previous state after orientation change occurred
     *
     * @param savedInstance
     */
    private void restorePreviousState(@NonNull Bundle savedInstance) {

        // Restoring Toolbar title
        toolbar.setTitle(savedInstance.getCharSequence(SAVED_TOOLBAR_VIEW_TITLE_ID));

        // Restoring SearchView instance
        moviesSearchView
                .onRestoreInstanceState(savedInstance.getParcelable(SAVED_SEARCH_VIEW_ID));

        // Restoring adapter items
        moviesRecyclerAdapter
                .updateList(savedInstance.getParcelableArrayList(SAVED_RECYCLER_VIEW_DATA_SET_ID));

        // Restoring RecyclerView position
        gridLayoutManager
                .onRestoreInstanceState(savedInstance.getParcelable(SAVED_RECYCLER_VIEW_STATUS_ID));

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        // putting ToolBar title
        outState.putCharSequence(SAVED_TOOLBAR_VIEW_TITLE_ID, toolbar.getTitle());

        // putting SearchView state
        outState.putParcelable(SAVED_SEARCH_VIEW_ID, moviesSearchView.onSaveInstanceState());

        // putting RecyclerView position
        outState.putParcelable(SAVED_RECYCLER_VIEW_STATUS_ID,
                gridLayoutManager.onSaveInstanceState());

        // putting RecyclerView items
        outState.putParcelableArrayList(SAVED_RECYCLER_VIEW_DATA_SET_ID,
                new ArrayList<>(moviesRecyclerAdapter.getMovies()));


        super.onSaveInstanceState(outState);
    }

    /**
     * Saving my presenter so that I won't to create a new one every time the orientation changes
     *
     * @return the current presenter
     */
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    @Override
    public void onBackPressed() {
        if (!moviesSearchView.onBackPressed()) {
            super.onBackPressed();
        }
    }

}
