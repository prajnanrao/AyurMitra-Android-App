package com.example.ayurmitra.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayurmitra.R;
import com.example.ayurmitra.activities.HerbDetailActivity;
import com.example.ayurmitra.models.Herb;

import java.util.List;

public class HerbAdapter extends RecyclerView.Adapter<HerbAdapter.HerbViewHolder> {

    private Context context;
    private List<Herb> herbList;

    public HerbAdapter(Context context, List<Herb> herbList) {
        this.context = context;
        this.herbList = herbList;
    }

    @NonNull
    @Override
    public HerbViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_herb, parent, false);
        return new HerbViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HerbViewHolder holder, int position) {
        Herb herb = herbList.get(position);

        holder.tvName.setText(herb.getName());
        holder.tvDesc.setText(herb.getDescription());
        holder.ivHerb.setImageResource(herb.getImageResId());

        // ✅ Click → Detail Screen with full object
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, HerbDetailActivity.class);
            intent.putExtra("herb_data", herb);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return herbList.size();
    }

    public static class HerbViewHolder extends RecyclerView.ViewHolder {
        ImageView ivHerb;
        TextView tvName, tvDesc;

        public HerbViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHerb = itemView.findViewById(R.id.ivHerb);
            tvName = itemView.findViewById(R.id.tvHerbName);
            tvDesc = itemView.findViewById(R.id.tvHerbDesc);
        }
    }
}