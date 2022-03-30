package com.example.moviedpapp.repositories;

import androidx.lifecycle.LiveData;

import com.example.moviedpapp.Model.MovieModel;
import com.example.moviedpapp.request.MovieApiClient;

import java.util.List;

public class MovieRepository {

    private static MovieRepository movieRepository;

    private MovieApiClient movieApiClient;

    private String repQuery;
    private int repPageNumber;

    //==============================================================

    public static MovieRepository getInstance(){
        if(movieRepository==null)
        {
            movieRepository= new MovieRepository();
        }
        return movieRepository;

    }

    private MovieRepository(){
        movieApiClient = movieApiClient.getInstance();
    }

    //==============================================================

    public LiveData<List<MovieModel>> getLatestMovies(){
        return movieApiClient.getLatesMovies();}
    public LiveData<List<MovieModel>> getPopMovies(){
        return movieApiClient.getPopMovies();}
    public LiveData<List<MovieModel>> getFevMovies(){
        return movieApiClient.getFevMovies();}


    //==============================================================

    public void searchLatestMovies(String query , int pageNumber) {
        repQuery=query;
        repPageNumber = pageNumber;
        movieApiClient.searchLatestMovies(pageNumber);
    }

    public void searchNexPage(){
        searchLatestMovies(repQuery,repPageNumber+1);
    }

    //==============================================================

    public void searchPopMovie(int pageNumber) {
        repPageNumber = pageNumber;
        movieApiClient.searchPopMovie(pageNumber);
    }

    public void searchLatestMovies(int pageNumber) {
        repPageNumber = pageNumber;
        movieApiClient.searchLatestMovies(pageNumber);
    }

    public void searchFevMovie(int pageNumber) {
        repPageNumber = pageNumber;
        movieApiClient.searchFevMovie(pageNumber);
    }

    //==============================================================

    public void searchNextLatestPage(){
        repPageNumber++;
        movieApiClient.searchLatestMovies(repPageNumber);
    }

    public void searchNextPopPage(){
        repPageNumber++;
        movieApiClient.searchPopMovie(repPageNumber);
    }

    public void searchNextFevPage(){
        repPageNumber++;
        movieApiClient.searchFevMovie(repPageNumber);
    }

}
