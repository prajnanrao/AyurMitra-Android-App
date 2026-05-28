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
import com.example.ayurmitra.activities.ExpertDetailActivity;
import com.example.ayurmitra.models.Expert;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ExpertsAdapter extends RecyclerView.Adapter<ExpertsAdapter.ExpertViewHolder> {

    private Context context;
    private List<Expert> expertList;

    public ExpertsAdapter(Context context, List<Expert> expertList) {
        this.context = context;
        this.expertList = expertList;
    }

    @NonNull
    @Override
    public ExpertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_expert, parent, false);
        return new ExpertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpertViewHolder holder, int position) {
        Expert expert = expertList.get(position);

        holder.tvName.setText(expert.getName());
        holder.tvSpecialty.setText(expert.getSpecialization());
        holder.tvExperience.setText(expert.getExperience());
        holder.ivExpert.setImageResource(expert.getImageResId());

        // ✅ Function to open detail
        View.OnClickListener openDetail = v -> {
            Intent intent = new Intent(context, ExpertDetailActivity.class);
            intent.putExtra("expert_data", expert);
            context.startActivity(intent);
        };

        // ✅ Set listener on both the whole item AND the Consult button
        holder.itemView.setOnClickListener(openDetail);
        holder.btnConsult.setOnClickListener(openDetail);
    }

    @Override
    public int getItemCount() {
        return expertList.size();
    }

    public static class ExpertViewHolder extends RecyclerView.ViewHolder {

        ImageView ivExpert;
        TextView tvName, tvSpecialty, tvExperience;
        MaterialButton btnConsult;

        public ExpertViewHolder(@NonNull View itemView) {
            super(itemView);
            ivExpert = itemView.findViewById(R.id.ivExpert);
            tvName = itemView.findViewById(R.id.tvExpertName);
            tvSpecialty = itemView.findViewById(R.id.tvSpecialty);
            tvExperience = itemView.findViewById(R.id.tvExperience);
            btnConsult = itemView.findViewById(R.id.btnConsult);
        }
    }
}