package com.example.moviedpapp.request;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviedpapp.MainActivity;
import com.example.moviedpapp.Model.MovieModel;
import com.example.moviedpapp.VariablesShortcut.UrlLinks;
import com.example.moviedpapp.response.MoiveResponse;
import com.example.moviedpapp.utils.AppExecutors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {


    private static MovieApiClient movieApiClient;

    //========================= instance for pop movie

    private MutableLiveData<List<MovieModel>> popMovies;
    private RetrieveMoviesRunnablePop retrieveMoviesPopRunnable;

    //========================= instance for latest movie

    private MutableLiveData<List<MovieModel>> latestMovies;
    private RetrieveMoviesRunnable retrieveMoviesLatestRunnable;

    //========================= instance for Fev movie

    private MutableLiveData<List<MovieModel>> fevMovies;
    private RetrieveMoviesRunnableFev retrieveMoviesFevRunnable;

    //========================= Builder

    public static MovieApiClient getInstance() {
        if (movieApiClient == null) {
            movieApiClient = new MovieApiClient();
        }
        return movieApiClient;
    }

    private MovieApiClient() {
        latestMovies = new MutableLiveData<>();
        popMovies = new MutableLiveData<>();
        fevMovies = new MutableLiveData<>();

    }

    public LiveData<List<MovieModel>> getLatesMovies() {
        return latestMovies;
    }
    public LiveData<List<MovieModel>> getPopMovies() {
        return popMovies;
    }
    public LiveData<List<MovieModel>> getFevMovies() {
        return fevMovies;
    }



    //========================= for latest movie

    public void searchLatestMovies(int pageNumber) {

        if(retrieveMoviesLatestRunnable !=null){
            retrieveMoviesLatestRunnable = null;
        }

        retrieveMoviesLatestRunnable = new RetrieveMoviesRunnable(pageNumber);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveMoviesLatestRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // cancelling the retrofit call
                myHandler.cancel(true);
            }
        }, 4000, TimeUnit.MILLISECONDS);
    }

    private class RetrieveMoviesRunnable implements Runnable {

        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {

            try {
                Response response = getLatestMovies(pageNumber).execute();

                if (cancelRequest) {
                    return;
                }

                if(response.code()==200)
                {
                    List<MovieModel> list = new ArrayList<>(((MoiveResponse)response.body()).getMovies());
                    if (pageNumber==1){
                        latestMovies.postValue(list);
                    }

                    else {
                        List<MovieModel> currentMovies = latestMovies.getValue();
                        currentMovies.addAll(list);
                        latestMovies.postValue(currentMovies);
                    }
                }
                else {
                    String error = response.errorBody().string();
                    Log.v("tag" , "Error" + error);
                    latestMovies.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                latestMovies.postValue(null);
            }



        }

        private Call<MoiveResponse> getLatestMovies(int pageNumber) {
            return Servicey.getMovieApi().getLatest(
                    UrlLinks.API_KEY,
                    pageNumber
            );
        }


        private void cancelRequest() {
            Log.v("tag", "Cancelling search request");
            cancelRequest = true;
        }

    }


    //========================= for pop movie

    public void searchPopMovie(int pageNumber) {

        if(retrieveMoviesPopRunnable !=null){
            retrieveMoviesPopRunnable = null;
        }

        retrieveMoviesPopRunnable = new RetrieveMoviesRunnablePop(pageNumber);

        final Future myHandlerPop = AppExecutors.getInstance().networkIO().submit(retrieveMoviesPopRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // cancelling the retrofit call
                myHandlerPop.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS);
    }

    private class RetrieveMoviesRunnablePop implements Runnable {

        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnablePop( int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {

            try {
                Response responsePop = getPop(pageNumber).execute();

                if (cancelRequest) {
                    return;
                }

                if(responsePop.code()==200)
                {
                    List<MovieModel> list = new ArrayList<>(((MoiveResponse)responsePop.body()).getMovies());
                    if (pageNumber==1){
                        popMovies.postValue(list);
                    }

                    else {
                        List<MovieModel> currentMovies = popMovies.getValue();
                        currentMovies.addAll(list);
                        popMovies.postValue(currentMovies);
                    }
                }
                else {
                    String error = responsePop.errorBody().string();
                    Log.v("tag" , "Error " + error);
                    popMovies.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                popMovies.postValue(null);
            }



        }

        private Call<MoiveResponse> getPop(int pageNumber) {
            return Servicey.getMovieApi().getPopulaar(
                    UrlLinks.API_KEY,
                    pageNumber
            );
        }


        private void cancelRequest() {
            Log.v("tag", "Cancelling search request");
            cancelRequest = true;
        }

    }


    //========================= for Fev movie

    public void searchFevMovie(int pageNumber) {

        if(retrieveMoviesFevRunnable !=null){
            retrieveMoviesFevRunnable = null;
        }

        retrieveMoviesFevRunnable = new RetrieveMoviesRunnableFev(pageNumber);

        final Future myHandlerFev = AppExecutors.getInstance().networkIO().submit(retrieveMoviesFevRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // cancelling the retrofit call
                myHandlerFev.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS);
    }

    private class RetrieveMoviesRunnableFev implements Runnable {

        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnableFev( int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {

            try {
                Response responseFev = getFev().execute();

                if (cancelRequest) {
                    return;
                }

                if(responseFev.code()==200)
                {
                    fevMovies.postValue(null);

                    List<MovieModel> list = new ArrayList<>(((MoiveResponse)responseFev.body()).getMovies());
                    if (pageNumber==1){
                        fevMovies.postValue(list);
                    }

                    else {
                        List<MovieModel> currentMovies = fevMovies.getValue();
                        currentMovies.addAll(list);
                        fevMovies.postValue(currentMovies);
                    }
                }
                else {
                    String error = responseFev.errorBody().string();
                    Log.v("tag" , "Error " + error);
                    fevMovies.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                fevMovies.postValue(null);
            }



        }

        private Call<MoiveResponse> getFev() {
            return Servicey.getMovieApi().getFavorite(
                    MainActivity.my_id,
                    UrlLinks.API_KEY,
                    MainActivity.account_id
            );
        }


        private void cancelRequest() {
            Log.v("tag", "Cancelling search request");
            cancelRequest = true;
        }

    }



}
