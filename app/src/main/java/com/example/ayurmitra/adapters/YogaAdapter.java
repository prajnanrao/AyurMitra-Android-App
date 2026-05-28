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
import com.example.ayurmitra.activities.YogaDetailActivity;
import com.example.ayurmitra.models.Yoga;
import java.util.List;

public class YogaAdapter extends RecyclerView.Adapter<YogaAdapter.YogaViewHolder> {

    private Context context;
    private List<Yoga> yogaList;

    public YogaAdapter(Context context, List<Yoga> yogaList) {
        this.context = context;
        this.yogaList = yogaList;
    }

    @NonNull
    @Override
    public YogaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_yoga, parent, false);
        return new YogaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YogaViewHolder holder, int position) {
        Yoga yoga = yogaList.get(position);
        holder.tvName.setText(yoga.getName());
        holder.tvDesc.setText(yoga.getDescription());
        holder.tvCategory.setText(yoga.getCategory());
        holder.ivYoga.setImageResource(yoga.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, YogaDetailActivity.class);
            intent.putExtra("YOGA_DATA", yoga);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return yogaList.size();
    }

    static class YogaViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDesc, tvCategory;
        ImageView ivYoga;

        public YogaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvYogaName);
            tvDesc = itemView.findViewById(R.id.tvYogaDesc);
            tvCategory = itemView.findViewById(R.id.tvYogaCategory);
            ivYoga = itemView.findViewById(R.id.ivYoga);
        }
    }
}
