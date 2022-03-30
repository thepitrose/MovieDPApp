package com.example.moviedpapp.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostBodyModel {

    @SerializedName("session_id")
    @Expose()
    String session_id;


    public PostBodyModel(String session_id) {

        this.session_id=session_id;
    }


    public String getId() {
        return session_id;
    }
}
