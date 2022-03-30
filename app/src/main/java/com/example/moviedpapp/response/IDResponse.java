package com.example.moviedpapp.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IDResponse {
    @SerializedName("id")
    @Expose()
    private int id;

    public int getId() {
        return id;
    }
}
