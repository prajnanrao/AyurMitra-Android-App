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
import com.example.ayurmitra.activities.DoshaDetailActivity;
import com.example.ayurmitra.models.Dosha;
import java.util.List;

public class DoshaAdapter extends RecyclerView.Adapter<DoshaAdapter.DoshaViewHolder> {

    private Context context;
    private List<Dosha> doshaList;

    public DoshaAdapter(Context context, List<Dosha> doshaList) {
        this.context = context;
        this.doshaList = doshaList;
    }

    @NonNull
    @Override
    public DoshaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dosha, parent, false);
        return new DoshaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoshaViewHolder holder, int position) {
        Dosha dosha = doshaList.get(position);
        holder.tvName.setText(dosha.getName());
        holder.tvDesc.setText(dosha.getDescription());

        holder.ivDosha.setImageResource(dosha.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DoshaDetailActivity.class);
            intent.putExtra("dosha_data", dosha);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return doshaList.size();
    }

    public static class DoshaViewHolder extends RecyclerView.ViewHolder {
        ImageView ivDosha;
        TextView tvName, tvDesc;

        public DoshaViewHolder(@NonNull View itemView) {
            super(itemView);
            ivDosha = itemView.findViewById(R.id.ivDosha);
            tvName = itemView.findViewById(R.id.tvDoshaName);
            tvDesc = itemView.findViewById(R.id.tvDoshaDesc);
        }
    }
}
