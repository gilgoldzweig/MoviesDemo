package com.example.gilgoldzweig.moviedemo.consts;

import android.support.annotation.NonNull;
import android.util.SparseArray;

import java.util.Iterator;

/**
 * Handle converting the genres id's from TheMovieDB to actually readable content
 */
public class Genre {

    //On android it's better to use sparse array for this kind of action
    private static SparseArray<String> genresSparseArray = new SparseArray<>();

    static {
        genresSparseArray.put(28, "Action");
        genresSparseArray.put(12, "Adventure");
        genresSparseArray.put(16, "Animation");
        genresSparseArray.put(35, "Comedy");
        genresSparseArray.put(80, "Crime");
        genresSparseArray.put(99, "Documentary");
        genresSparseArray.put(18, "Drama");
        genresSparseArray.put(10402, "Music");
        genresSparseArray.put(9648, "Mystery");
        genresSparseArray.put(10749, "Romance");
        genresSparseArray.put(878, "Science Fiction");
        genresSparseArray.put(10770, "TV Movie");
        genresSparseArray.put(53, "Thriller");
        genresSparseArray.put(10752, "War");
        genresSparseArray.put(37, "Western");
        genresSparseArray.put(10751, "Family");
        genresSparseArray.put(14, "Fantasy");
        genresSparseArray.put(10769, "Foreign");
        genresSparseArray.put(36, "History");
        genresSparseArray.put(27, "Horror");
    }

    public static String getGenreName(int genreId) {
        String genre = genresSparseArray.get(genreId);
        return genre == null ? "Unknown" : genre;
    }

    /**
     * Converts any Iterator of integers to a "prettify" string
     * @param genresId the genres id's most likely from remote repository
     * @return prettified string based on [genresSparseArray]
     *
     * Example:
     * [] -> ""
     * [99, 27, 10751] -> "Documentary, Horror, Family" || I know it's a weird example
     * [11, 2, 4, 10402] -> "Unknown, Unknown, Unknown, Music" || In case some are unknown
     * [99, 27, null, 10402] -> "Documentary, Horror, Family" || null are ignored
     *
     * Side note: I could have used just a simple list but the code would be pretty much
     * the same so I said why not support more data structures
     */
    public static String generateGenresString(@NonNull Iterable<Integer> genresId) {
        StringBuilder movieGenres = new StringBuilder();
        Iterator<Integer> ids = genresId.iterator();
        while (ids.hasNext()) {
            Integer genreId = ids.next();
            if (genreId == null) continue;

            movieGenres.append(getGenreName(genreId));

            if (ids.hasNext()) { //Not the last item, we add a comma
                movieGenres.append(", ");
            }
        }
        return movieGenres.toString();
    }

}
