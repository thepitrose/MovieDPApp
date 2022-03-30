package com.example.moviedpapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BodyFavorite {
    @SerializedName("media_type")
    @Expose
    private String mediaType;
    @SerializedName("media_id")
    @Expose
    private Integer mediaId;
    @SerializedName("favorite")
    @Expose
    private Boolean favorite;

    public BodyFavorite(String mediaType, Integer mediaId, Boolean favorite) {
        this.mediaType = mediaType;
        this.mediaId = mediaId;
        this.favorite = favorite;
    }
}
