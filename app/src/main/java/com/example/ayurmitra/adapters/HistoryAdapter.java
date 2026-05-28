package com.example.ayurmitra.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ayurmitra.R;
import com.example.ayurmitra.models.HealthScore;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<HealthScore> historyList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(HealthScore score);
    }

    public HistoryAdapter(List<HealthScore> historyList, OnItemClickListener listener) {
        this.historyList = historyList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_score, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HealthScore score = historyList.get(position);
        holder.tvScore.setText(String.valueOf(score.getTotalScore()));
        
        if (score.getTimestamp() != null) {
            // ✅ Fixed: Use new Date(Long) instead of .toDate() for Long timestamps
            Date date = new Date(score.getTimestamp());
            SimpleDateFormat dateFormater = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            SimpleDateFormat timeFormater = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            holder.tvDate.setText(dateFormater.format(date));
            holder.tvTime.setText(timeFormater.format(date));
        } else {
            holder.tvDate.setText("Recently");
            holder.tvTime.setText("");
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(score);
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvScore, tvDate, tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvScore = itemView.findViewById(R.id.tvScoreItem);
            tvDate = itemView.findViewById(R.id.tvDateItem);
            tvTime = itemView.findViewById(R.id.tvTimeItem);
        }
    }
}
