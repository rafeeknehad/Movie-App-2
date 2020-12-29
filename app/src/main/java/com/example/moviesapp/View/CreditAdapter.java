package com.example.moviesapp.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moviesapp.R;
import com.example.moviesapp.javaClass.CastCrew;
import com.example.moviesapp.model.Credits.CreditsResponse;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.CreditViewHolder> {

    public List<CastCrew> data;
    public Context context;

    public CreditAdapter(List<CastCrew> data,Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public CreditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.personal_cardview, parent, false);
        return new CreditViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return (data.size());
    }

    public class CreditViewHolder extends RecyclerView.ViewHolder {
        CircularImageView profileImage;
        TextView name;

        public CreditViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.personal_cardView_imageView);
            name = itemView.findViewById(R.id.personal_name);
        }

        public void setData(int pos) {
            CastCrew item = data.get(pos);

            Picasso.with(context).load("http://image.tmdb.org/t/p/w185/"+String.valueOf(item.getProfileImage())).
                    placeholder(R.drawable.profile).into(profileImage);
            name.setText(item.getName());
        }
    }
}
