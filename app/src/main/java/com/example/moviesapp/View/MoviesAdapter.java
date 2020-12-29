package com.example.moviesapp.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.moviesapp.R;
import com.example.moviesapp.javaClass.moveRecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.moveViewHolder> implements Filterable {

    private ArrayList<moveRecyclerView> movesList = new ArrayList<>();
    private Context mContext;
    private onItemClickListener mListener;
    private ArrayList<moveRecyclerView> mMoviesListFull;
    private Filter moviesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<moveRecyclerView> filterList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(mMoviesListFull);
            } else {
                String filterName = constraint.toString().toLowerCase().trim();
                for (moveRecyclerView item : mMoviesListFull) {
                    if (item.getName().toLowerCase().trim().contains(filterName)) {
                        filterList.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            movesList.clear();
            movesList.addAll((List) results.values);

            notifyDataSetChanged();
        }
    };

    public MoviesAdapter(ArrayList<moveRecyclerView> movesList, Context context) {
        this.movesList = movesList;
        mMoviesListFull = new ArrayList<>(movesList);
        this.mContext = context;
    }

    public void setOnClickListener(onItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public moveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_movie_cardview, parent, false);
        return new moveViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull moveViewHolder holder, int position) {
        moveRecyclerView item = movesList.get(position);
        holder.setData(item);
    }

    @Override
    public int getItemCount() {
        return movesList.size();
    }

    @Override
    public Filter getFilter() {
        return moviesFilter;
    }

    public void updateMoviesGeners(String id) {
        ArrayList<moveRecyclerView> arrayMoviesFilter = new ArrayList<>();
        System.out.println("ffffffffffffffffff " + id);
        if (id.equals("-1")) {
            arrayMoviesFilter = new ArrayList<>(mMoviesListFull);
        } else {
            for (moveRecyclerView item : mMoviesListFull) {
                if (item.getGenersList().contains(Integer.valueOf(id))) {
                    arrayMoviesFilter.add(item);
                }
            }
        }
        movesList.clear();
        movesList.addAll(arrayMoviesFilter);
        notifyDataSetChanged();
    }

    public interface onItemClickListener {
        public void onCliskListener(int pos);
    }

    public class moveViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, data, ratedNumber;
        RatingBar ratingBar;

        public moveViewHolder(@NonNull final View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.movie_Cardview_imageview);
            name = itemView.findViewById(R.id.movie_Cardview_textview_name);
            ratedNumber = itemView.findViewById(R.id.movie_Cardview_text_rating);
            ratingBar = itemView.findViewById(R.id.movie_Cardview_ratingBar);
            data = itemView.findViewById(R.id.movie_Cardview_data_txt);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onCliskListener(position);
                        }
                    }
                }
            });
        }

        public void setData(moveRecyclerView item) {
            String url = "http://image.tmdb.org/t/p/w185/";
            url += item.getImageUrl();
            Picasso.with(mContext).load(url).resize(150, 150).into(imageView);
            name.setText(item.getName());
            data.setText(item.getData());
            ratedNumber.setText(String.valueOf(item.getRated()));
            ratingBar.setRating(Float.valueOf((float) (item.getRated() / 2.0)));
        }
    }
}
