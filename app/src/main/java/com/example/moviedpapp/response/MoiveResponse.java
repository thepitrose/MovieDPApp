package com.example.moviedpapp.response;

import com.example.moviedpapp.Model.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoiveResponse {
    @SerializedName("results")
    @Expose()
    private List<MovieModel> movies;

    public List<MovieModel> getMovies(){
        return movies;
    }
}
