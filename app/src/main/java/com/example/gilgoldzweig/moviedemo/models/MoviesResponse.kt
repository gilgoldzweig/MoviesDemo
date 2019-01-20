package com.example.gilgoldzweig.moviedemo.models

import com.fasterxml.jackson.annotation.JsonProperty

data class MoviesResponse(
    @JsonProperty("page")
    val page: Int = 0,

    @JsonProperty("total_pages")
    val totalPages: Int = 0,

    @JsonProperty("results")
    val movies: List<Movie> = emptyList(),

    @JsonProperty("total_results")
    val totalResults: Int = 0
)