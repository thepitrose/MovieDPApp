package com.example.moviedpapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.moviedpapp.Model.MovieModel;
import com.example.moviedpapp.MovieView.MovieRecyclerView;
import com.example.moviedpapp.MovieView.OnMoiveListener;
import com.example.moviedpapp.VariablesShortcut.UrlLinks;
import com.example.moviedpapp.VariablesShortcut.userID;
import com.example.moviedpapp.request.Servicey;
import com.example.moviedpapp.response.IDResponse;
import com.example.moviedpapp.response.tokenResponse;
import com.example.moviedpapp.utils.MovieApi;
import com.example.moviedpapp.utils.PostBodyModel;
import com.example.moviedpapp.viewmodels.MovieListViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMoiveListener , LifecycleObserver {

    private RecyclerView recyclerView ;

    private MovieRecyclerView movieRecyclerAdapterPop,movieRecyclerAdapterlatest,movieRecyclerAdapterFav;
    private MovieListViewModel movieListViewModelPop,movieListViewModellatest,movieListViewModelFav;
    private Button latestMovies,popMovies,favoritesMovies,logBtn;
    private boolean isPop=true;
    private boolean isLates=false;
    private boolean isFavorites=false;

    public static userID userid = new userID();
    //==============================

    private String request_token ="";
    public static String account_id="";
    public static int my_id;
    private PostBodyModel postBodyModel;


    //==============================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        popMovies = findViewById(R.id.button_popular);
        latestMovies = findViewById(R.id.button_latest);
        favoritesMovies = findViewById(R.id.button_Favorites);
        logBtn = findViewById(R.id.button_Log);

        //============================================


        popMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isPop=true;
                isLates=false;
                isFavorites=false;

                SetupPopView();
            }
        });

        latestMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isPop=false;
                isLates=true;
                isFavorites=false;

                SetupLatestView();
                ObserveAnyChange();

            }
        });

        favoritesMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isPop=false;
                isLates=false;
                isFavorites=true;

                if (userid.getId()== 0)
                {
                    getToken();

                }
                else {
                    SetupFevtView();
                    ObserveAnyChange();
                }

            }
        });

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getToken();
            }
        });

        //============================================

        recyclerView = findViewById(R.id.recyclerView);


        movieListViewModelPop = new ViewModelProvider(this).get(MovieListViewModel.class);
        movieListViewModellatest = new ViewModelProvider(this).get(MovieListViewModel.class);
        movieListViewModelFav = new ViewModelProvider(this).get(MovieListViewModel.class);

        ConfigureRecyclerView();
        ObserveAnyChange();

        SetupPopView();


    }

    //============================================

    private void getToken(){
        MovieApi movieApi = Servicey.getMovieApi();
            Call<tokenResponse> responseCall = movieApi.gettoken(
                    UrlLinks.API_KEY
            );

            responseCall.enqueue(new Callback<tokenResponse>() {
                @Override
                public void onResponse(Call<tokenResponse> call, Response<tokenResponse> response) {

                    if (response.code() == 200)
                    {

                        request_token=response.body().gettoken();



                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.themoviedb.org/authenticate/" + request_token));
                        startActivity(browserIntent);


                        MainActivity.super.onResume();


                    }
                    else {
                        try {
                            Log.v("Tag" , "Error " + response.errorBody().string());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }

                @Override
                public void onFailure(Call<tokenResponse> call, Throwable t) {
                }
            });

        }

    private void AccountId(){

        MovieApi movieApi = Servicey.getMovieApi();
        Call<PostBodyModel> responseCall = movieApi.getAccountId(
                UrlLinks.API_KEY ,
                request_token
        );

        responseCall.enqueue(new Callback<PostBodyModel>() {
            @Override
            public void onResponse(Call<PostBodyModel> call, Response<PostBodyModel> response) {

                if (response.code() == 200)
                {

                    account_id=response.body().getId();
                    getMyID();
                }
                else {
                    try {
                        Log.v("Tag" , "Error " + response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(Call<PostBodyModel> call, Throwable t) {
            }
        });

    }


    private void getMyID(){


        MovieApi movieApi = Servicey.getMovieApi();

        Call<IDResponse> responseCall = movieApi.getID(
                UrlLinks.API_KEY ,
                account_id
        );

        responseCall.enqueue(new Callback<IDResponse>() {
            @Override
            public void onResponse(Call<IDResponse> call, Response<IDResponse> response) {

                if (response.code() == 200) {
                    my_id=response.body().getId();
                    userid.setId(my_id);
                    if(isFavorites)
                    {
                        SetupFevtView();
                        ObserveAnyChange();
                    }

                }
                else {
                    try {
                        Log.v("Tag" , "Error " + response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(Call<IDResponse> call, Throwable t) {
            }
        });

    }

    public void onResume(){
        super.onResume();
        AccountId();
    }

    //============================================
    private void ConfigureRecyclerView(){

        movieRecyclerAdapterPop = new MovieRecyclerView(this);


        recyclerView.setAdapter(movieRecyclerAdapterPop);
        recyclerView.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)){
                    movieListViewModelPop.searchNextPopPage();
                }
            }
        });
    }
    //============================================
    private void ObserveAnyChange() {

        if(isPop) {
            movieListViewModelPop.getPopMovies().observe(this, new Observer<List<MovieModel>>() {
                @Override
                public void onChanged(List<MovieModel> movieModels) {
                    if (movieModels != null) {
                        for (MovieModel movieModel : movieModels) {
                            movieRecyclerAdapterPop.setmMovie(movieModels);
                        }
                    }

                }
            });
        }

        else if (isLates) {
            movieListViewModellatest.getLatestMovies().observe(this, new Observer<List<MovieModel>>() {
                @Override
                public void onChanged(List<MovieModel> movieModels) {
                    if (movieModels != null) {
                        for (MovieModel movieModel : movieModels) {
                            movieRecyclerAdapterlatest.setmMovie(movieModels);
                        }
                    }

                }
            });
        }

        else if (isFavorites) {
            movieListViewModelFav.getFevMovies().observe(this, new Observer<List<MovieModel>>() {
                @Override
                public void onChanged(List<MovieModel> movieModels) {
                    if (movieModels != null) {
                        for (MovieModel movieModel : movieModels) {
                            movieRecyclerAdapterFav.setmMovie(movieModels);
                        }
                    }

                }
            });
        }


    }
    //============================================

    public void onMovieClick(int position) {

        if(isPop) {
            Intent intent = new Intent(this, MovieDetails.class);
            intent.putExtra("movie",movieRecyclerAdapterPop.getSelectedMovie(position));
            startActivity(intent);
        }

        else if(isLates) {
            Intent intent = new Intent(this, MovieDetails.class);
            intent.putExtra("movie",movieRecyclerAdapterlatest.getSelectedMovie(position));
            startActivity(intent);
        }

        else if(isFavorites) {
            Intent intent = new Intent(this, MovieDetails.class);
            intent.putExtra("movie",movieRecyclerAdapterFav.getSelectedMovie(position));
            startActivity(intent);
        }

    }
    //============================================
    private void SetupPopView() {

        movieListViewModelPop.searchPopMovies(1);

        movieRecyclerAdapterPop = new MovieRecyclerView(this);

        recyclerView.setAdapter(movieRecyclerAdapterPop);
        recyclerView.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)){
                    movieListViewModelPop.searchNextpage();
                }
            }
        });




    }
    //============================================
    private void SetupLatestView() {

        movieListViewModellatest.searchLatestMovies(1);
        movieRecyclerAdapterlatest = new MovieRecyclerView(this);

        recyclerView.setAdapter(movieRecyclerAdapterlatest);
        recyclerView.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)){
                    movieListViewModellatest.searchNextLatestPage();
                }
            }
        });



    }

    //============================================
    private void SetupFevtView() {

        movieListViewModelFav.searchFevMovies(1);
        movieRecyclerAdapterFav = new MovieRecyclerView(this);

        recyclerView.setAdapter(movieRecyclerAdapterFav);
        recyclerView.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)){
                    movieListViewModelFav.searchNextFevPage();
                }
            }
        });



    }
    //============================================

}