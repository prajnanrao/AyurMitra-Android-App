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
import java.util.List;

public class RecommendedExpertsAdapter extends RecyclerView.Adapter<RecommendedExpertsAdapter.ViewHolder> {

    private Context context;
    private List<Expert> experts;

    public RecommendedExpertsAdapter(Context context, List<Expert> experts) {
        this.context = context;
        this.experts = experts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_expert_recommended, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expert expert = experts.get(position);
        holder.tvName.setText(expert.getName());
        holder.tvSpecialty.setText(expert.getSpecialization());
        holder.tvRating.setText("⭐ " + expert.getRating());
        holder.ivExpert.setImageResource(expert.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ExpertDetailActivity.class);
            intent.putExtra("expert_data", expert);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return experts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivExpert;
        TextView tvName, tvSpecialty, tvRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivExpert = itemView.findViewById(R.id.ivExpert);
            tvName = itemView.findViewById(R.id.tvExpertName);
            tvSpecialty = itemView.findViewById(R.id.tvSpecialty);
            tvRating = itemView.findViewById(R.id.tvRating);
        }
    }
}
