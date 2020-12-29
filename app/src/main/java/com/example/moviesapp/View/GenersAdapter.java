package com.example.moviesapp.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moviesapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class GenersAdapter extends RecyclerView.Adapter<GenersAdapter.viewHolder> {

    public static onItemClickedListener mListener;
    ArrayList<String> stringArrayList = new ArrayList<>();
    private Context mContext;
    private int selectPos = 0;
    private int row_index = -1;
    public GenersAdapter(ArrayList<String> stringArrayList, Context context) {
        this.stringArrayList = stringArrayList;
        this.mContext = context;
        selectPos = 0;
    }

    public void setOnClickListener(onItemClickedListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_movies_cardview, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {
        String text = stringArrayList.get(position);
        String[] name = text.split("&");
        holder.textView.setText(name[0]);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index = position;
                if(position != RecyclerView.NO_POSITION)
                {
                    mListener.itemClickListener(position);
                }
                notifyDataSetChanged();
            }
        });
        if(row_index == position)
        {
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        }
        else
        {
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.customer_color2));
        }
    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public interface onItemClickedListener {
        public void itemClickListener(int pos);
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.type_movies_cardview_textview);
            cardView = itemView.findViewById(R.id.card_view_geners);
        }
    }

}
