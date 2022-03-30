package com.example.moviedpapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class favoriteModel  implements Parcelable {

    String media_type;
    int media_id;
    boolean favorite;

    public favoriteModel(String media_type, int media_id, boolean favorite) {
        this.media_type = media_type;
        this.media_id = media_id;
        this.favorite = favorite;
    }

    //----------------------------------------------------------------

    public String getMedia_type() {
        return media_type;
    }

    public int getMedia_id() {
        return media_id;
    }

    public boolean isFavorite() {
        return favorite;
    }

    //----------------------------------------------------------------

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(media_type);
        parcel.writeInt(media_id);
        parcel.writeByte((byte) (favorite ? 1 : 0));
    }

    //----------------------------------------------------------------



    protected favoriteModel(Parcel in) {
        media_type = in.readString();
        media_id = in.readInt();
        favorite = in.readByte() != 0;
    }

    public static final Creator<favoriteModel> CREATOR = new Creator<favoriteModel>() {
        @Override
        public favoriteModel createFromParcel(Parcel in) {
            return new favoriteModel(in);
        }

        @Override
        public favoriteModel[] newArray(int size) {
            return new favoriteModel[size];
        }
    };

}
