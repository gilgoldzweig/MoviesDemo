package com.example.gilgoldzweig.moviedemo.activities.main.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gilgoldzweig.moviedemo.R;
import com.example.gilgoldzweig.moviedemo.activities.main.adapters.diffutil.MoviesDiffCallback;
import com.example.gilgoldzweig.moviedemo.consts.Consts;
import com.example.gilgoldzweig.moviedemo.consts.Genre;
import com.example.gilgoldzweig.moviedemo.models.Movie;
import com.example.gilgoldzweig.moviedemo.ui.GlideApp;
import com.example.gilgoldzweig.moviedemo.ui.GlideRequests;
import com.example.gilgoldzweig.moviedemo.utils.GenericOnItemClickedListener;

import java.util.ArrayList;
import java.util.List;

public class MoviesRecyclerAdapter extends
        RecyclerView.Adapter<MoviesRecyclerAdapter.MovieItemViewHolder> {

    private List<Movie> movies = new ArrayList<>();

    private final LayoutInflater inflater;
    private final GlideRequests glide;
    private GenericOnItemClickedListener<Movie> onItemClickedListener;


    public MoviesRecyclerAdapter(Context context,
                                 GenericOnItemClickedListener<Movie> onItemClickedListener) {

        inflater = LayoutInflater.from(context);
        glide = GlideApp.with(context);
        this.onItemClickedListener = onItemClickedListener;
    }

    @NonNull
    @Override
    public MovieItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MovieItemViewHolder(inflater.inflate(R.layout.item_movie, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieItemViewHolder movieItemViewHolder, int position) {
        Movie movie = movies.get(position);
        movieItemViewHolder.bind(movie);
    }

    @Override
    public void onViewRecycled(@NonNull MovieItemViewHolder holder) {
        super.onViewRecycled(holder);
        holder.recycle();
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void updateList(List<Movie> newList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MoviesDiffCallback(this.movies, newList));
        this.movies = newList;
        diffResult.dispatchUpdatesTo(this);
    }

    public List<Movie> getMovies() {
        return movies;
    }

    class MovieItemViewHolder extends RecyclerView.ViewHolder {

        MovieItemViewHolder(View view) {
            super(view);
        }

        private AppCompatImageView moviePosterImageView =
                itemView.findViewById(R.id.movie_item_poster_image);

        private AppCompatTextView movieTitleTextView =
                itemView.findViewById(R.id.movie_item_title_text);

        private AppCompatTextView movieGenreTextView =
                itemView.findViewById(R.id.movie_item_genre_text);


        void bind(@NonNull Movie movie) {

            if (movie.getPosterPath() != null) {
                glide.load(Consts.IMAGE_LOW_RES_BASE_URL + movie.getPosterPath())
                        .centerCrop()
                        .into(moviePosterImageView);
            }

           movieTitleTextView.setText(movie.getTitle());

            movieGenreTextView.setText(Genre.generateGenresString(movie.getGenreIds()));

            itemView.setOnClickListener(v -> onItemClickedListener.onItemClicked(movie));
        }

        void recycle() {
            glide.clear(moviePosterImageView);
            movieTitleTextView.setText(null);
            movieGenreTextView.setText(null);
        }
    }
}
