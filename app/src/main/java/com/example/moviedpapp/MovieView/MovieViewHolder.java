package com.example.moviedpapp.MovieView;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviedpapp.R;

import org.jetbrains.annotations.NotNull;

public class MovieViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView textView;
    ImageView imageView;


    OnMoiveListener onMoiveListener;

    @SuppressLint("ResourceType")
    public MovieViewHolder(@NonNull @NotNull View itemView, OnMoiveListener onMoiveListener) {
        super(itemView);

        this.onMoiveListener = onMoiveListener;

        textView = itemView.findViewById(R.id.movie_title);
        imageView = itemView.findViewById(R.id.movie_img);


        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        onMoiveListener.onMovieClick(getAdapterPosition());
    }
}