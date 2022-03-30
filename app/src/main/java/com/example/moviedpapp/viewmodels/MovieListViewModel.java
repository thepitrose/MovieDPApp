package com.example.moviedpapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviedpapp.Model.MovieModel;
import com.example.moviedpapp.repositories.MovieRepository;

import java.util.List;

public class MovieListViewModel  extends ViewModel {

    private MovieRepository movieRepository;


    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getPopMovies(){
        return movieRepository.getPopMovies();
    }
    public LiveData<List<MovieModel>> getLatestMovies(){return movieRepository.getLatestMovies();}
    public LiveData<List<MovieModel>> getFevMovies(){return movieRepository.getFevMovies();}


    public void searchPopMovies(int pageNumber){
        movieRepository.searchPopMovie(pageNumber);
    }
    public void searchLatestMovies(int pageNumber){ movieRepository.searchLatestMovies(pageNumber); }
    public void searchFevMovies(int pageNumber){
        movieRepository.searchFevMovie(pageNumber);
    }


    public void searchNextPopPage(){
        movieRepository.searchNextPopPage();
    }
    public void searchNextLatestPage(){
        movieRepository.searchNextLatestPage();
    }
    public void searchNextFevPage(){
        movieRepository.searchNextFevPage();
    }


    public void searchNextpage(){ movieRepository.searchNexPage();}
}
