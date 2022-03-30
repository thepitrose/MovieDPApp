package com.example.moviedpapp.utils;

import com.example.moviedpapp.Model.BodyFavorite;
import com.example.moviedpapp.response.IDResponse;
import com.example.moviedpapp.response.MoiveResponse;
import com.example.moviedpapp.response.favoriteResponse;
import com.example.moviedpapp.response.tokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    @GET("/3/movie/popular")
    Call<MoiveResponse> getPopulaar(
            @Query("api_key") String key,
            @Query("page") int page
    );

    @GET("/3/movie/now_playing")
    Call<MoiveResponse> getLatest(
            @Query("api_key") String key,
            @Query("page") int page
    );


    //===========================


    @GET("/3/authentication/token/new")
    Call<tokenResponse> gettoken(
            @Query("api_key") String key
    );


    @GET("/3/authentication/session/new")
    Call<PostBodyModel> getAccountId(
            @Query("api_key") String key , @Query("request_token") String token
    );

    @GET("/3/account/1favorite")
    Call<IDResponse> getID(
            @Query("api_key") String key,  @Query("session_id") String account_id
    );

    @GET("/3/account/{account_id}/favorite/movies")
    Call<MoiveResponse> getFavorite(
            @Path("account_id") int account_id, @Query("api_key") String key , @Query("session_id") String session_id
    );

    //======================================

    @GET("/3/account/{account_id}/favorite/movies")
    Call<MoiveResponse> isFevList(
            @Path("account_id") int account_id,  @Query("api_key") String key , @Query("session_id") String session_id
    );

    @POST("/3/account/{account_id}/favorite")
    Call<favoriteResponse> addFevList(
            @Body BodyFavorite bodyFavorite,
            @Query("api_key") String apiKey,
            @Query("session_id") String sessionId
    );
}
