package com.example.moviedpapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviedpapp.Model.BodyFavorite;
import com.example.moviedpapp.Model.MovieModel;
import com.example.moviedpapp.VariablesShortcut.UrlLinks;
import com.example.moviedpapp.request.Servicey;
import com.example.moviedpapp.response.MoiveResponse;
import com.example.moviedpapp.response.favoriteResponse;
import com.example.moviedpapp.utils.MovieApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetails extends AppCompatActivity {

    private ImageView imageViewDetails;
    private TextView titleDetails, descDetails;
    private Button favoritesbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        imageViewDetails = findViewById(R.id.imageView_details);
        titleDetails = findViewById(R.id.textView_title_details);
        descDetails = findViewById(R.id.textView_desc_details);
        favoritesbtn = findViewById(R.id.favoritesbtn);

        GetDataFromIntent();

        favoritesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getIntent().hasExtra("movie")){
                    MovieModel movieModel = getIntent().getParcelableExtra("movie");

                    toFev(movieModel.getMovie_id());
                }



            }
        });
    }

    private void GetDataFromIntent() {
        if (getIntent().hasExtra("movie")){
            MovieModel movieModel = getIntent().getParcelableExtra("movie");

            titleDetails.setText(movieModel.getTitle());
            descDetails.setText(movieModel.getMovie_overview());

            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/"
                            +movieModel.getPoster_path())
                    .into(imageViewDetails);

        }
    }

    private void toFev(int movieID){

        MovieApi movieApi = Servicey.getMovieApi();
        Call<MoiveResponse> responseCall = movieApi.isFevList(
                MainActivity.my_id,
                UrlLinks.API_KEY,
                MainActivity.account_id

        );

        responseCall.enqueue(new Callback<MoiveResponse>() {
            @Override
            public void onResponse(Call<MoiveResponse> call, Response<MoiveResponse> response) {
                boolean isFond=false;
                if (response.code() == 200)
                {

                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());

                    for (MovieModel movie :movies)
                    {
                        if (movie.getMovie_id()==movieID){
                            isFond=true;
                        }

                    }

                    if(isFond)
                    {
                        BodyFavorite bodyFavorite = new BodyFavorite("movie",movieID,false);
                        addtoFev(bodyFavorite);
                    }
                    else{

                        BodyFavorite bodyFavorite = new BodyFavorite("movie",movieID,true);
                        addtoFev(bodyFavorite);
                    }
                }
                else {
                    try {
                        Log.v("Tag" , "Error d " + response.errorBody().string());
                        Toast.makeText(MovieDetails.this, "You need to login", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(Call<MoiveResponse> call, Throwable t) {
            }
        });



    }

    private void addtoFev(BodyFavorite bodyFavorite){
        MovieApi movieApi = Servicey.getMovieApi();

        Call<favoriteResponse> responseCall = movieApi.addFevList(
                bodyFavorite,
                UrlLinks.API_KEY,
                MainActivity.account_id

        );

        responseCall.enqueue(new Callback<favoriteResponse>() {
            @Override
            public void onResponse(Call<favoriteResponse> call, Response<favoriteResponse> response) {


            }

            @Override
            public void onFailure(Call<favoriteResponse> call, Throwable t) {
            }
        });

    }



}