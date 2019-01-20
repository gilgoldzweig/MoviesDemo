package com.example.gilgoldzweig.moviedemo.activities.details;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.example.gilgoldzweig.moviedemo.R;
import com.example.gilgoldzweig.moviedemo.consts.Consts;
import com.example.gilgoldzweig.moviedemo.models.Movie;
import com.example.gilgoldzweig.moviedemo.modules.logging.Timber;
import com.example.gilgoldzweig.moviedemo.ui.GlideApp;
import com.example.gilgoldzweig.moviedemo.ui.GlideRequests;

public class DetailsActivity extends AppCompatActivity {

    public static final String SELECTED_MOVIE_INTENT_KEY = "selected_movie_intent_key";
    private Movie selectedMovie;
    private GlideRequests glide;
    private CoordinatorLayout root;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppCompatImageView movieCollapsingImage;
    private Toolbar toolbar;
    private AppCompatTextView movieContentText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        selectedMovie = getIntent().getParcelableExtra(SELECTED_MOVIE_INTENT_KEY);
        glide = GlideApp.with(this);

        root = findViewById(R.id.details_activity_root);
        collapsingToolbar = findViewById(R.id.details_activity_collapsing_toolbar);
        movieCollapsingImage = findViewById(R.id.details_activity_collapsing_image);
        toolbar = findViewById(R.id.details_activity_toolbar);
        movieContentText = findViewById(R.id.details_activity_content_text);


        toolbar.inflateMenu(R.menu.details_activity_share_menu);
        toolbar.setOnMenuItemClickListener(menuItem -> {

            if (share(Consts.TMDB_MOVIE_BASE_URL + selectedMovie.getId())) {
                Snackbar.make(root, R.string.movie_share_successfull, BaseTransientBottomBar.LENGTH_SHORT)
                        .show();
            } else {
                Snackbar.make(root, R.string.movie_share_failed, BaseTransientBottomBar.LENGTH_SHORT)
                        .show();

            }
            return false;
        });

        toolbar.setNavigationOnClickListener(v -> onBackPressed());


        collapsingToolbar.setTitle(selectedMovie.getTitle());
        toolbar.setTitle(selectedMovie.getTitle());

        glide.load(Consts.IMAGE_BASE_URL + selectedMovie.getBackdropPath())
                .into(movieCollapsingImage);

        movieContentText.setText(selectedMovie.getOverview());

        //Setting the status bar to be invisible so the image can act as background
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


    }

    private boolean share(String text) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(shareIntent, "Share movie"));
            return true;
        } catch (ActivityNotFoundException e) {
            Timber.crash(e);
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        glide.clear(movieCollapsingImage);
    }
}
