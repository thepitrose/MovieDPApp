package com.example.moviedpapp.MovieView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedpapp.Model.MovieModel;
import com.example.moviedpapp.R;

import java.util.List;

public class MovieRecyclerView extends RecyclerView.Adapter<MovieViewHolder> {

    private List<MovieModel> mMovie;
    private OnMoiveListener onMoiveListener;

    public MovieRecyclerView(OnMoiveListener onMoiveListener) {
        this.onMoiveListener = onMoiveListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_list_view,viewGroup,false);
        return new MovieViewHolder(view, onMoiveListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder viewHolderpop, int i) {


        ((MovieViewHolder)viewHolderpop).textView.setText(mMovie.get(i).getTitle());


        Glide.with(viewHolderpop.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500/"
                        +mMovie.get(i).getPoster_path())
                .into(((MovieViewHolder)viewHolderpop).imageView);


    }

    @Override
    public int getItemCount() {
        if (mMovie !=null)
        {
            return mMovie.size();
        }
        return 0;
    }

    public void setmMovie(List<MovieModel> mMovie) {
        this.mMovie = mMovie;
        notifyDataSetChanged();
    }

    public MovieModel getSelectedMovie(int position){
        if (mMovie != null){
            if (mMovie.size() > 0){
                return mMovie.get(position);
            }
        }
        return null;
    }
}
