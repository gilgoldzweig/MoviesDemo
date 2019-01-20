package com.example.gilgoldzweig.moviedemo.activities.main.adapters.diffutil;

import android.support.v7.util.DiffUtil;

import com.example.gilgoldzweig.moviedemo.models.Movie;

import java.util.List;

public class MoviesDiffCallback extends DiffUtil.Callback {

    private List<Movie> oldMovies;
    private List<Movie> newMovies;

    public MoviesDiffCallback(List<Movie> oldMovies, List<Movie> newMovies) {
        this.oldMovies = oldMovies;
        this.newMovies = newMovies;
    }
    
    @Override
    public int getOldListSize() {
        return oldMovies.size();
    }

    @Override
    public int getNewListSize() {
        return newMovies.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldMovies.get(oldItemPosition).getId() == newMovies.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldMovies.get(oldItemPosition).equals(newMovies.get(newItemPosition));
    }
}
