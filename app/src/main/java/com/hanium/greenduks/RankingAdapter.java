package com.hanium.greenduks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {

    private ArrayList<Ranking> list;

    public void setRankList(ArrayList<Ranking> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public RankingAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_ranking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RankingAdapter.ViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView rank;
        TextView memberId;
        TextView weight;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            rank = itemView.findViewById(R.id.tvRanking_rank);
            memberId = itemView.findViewById(R.id.tvRanking_id);
            weight = itemView.findViewById(R.id.tvRanking_weight);
        }

        void onBind(Ranking item){
            rank.setText(String.valueOf(item.getRank()));
            memberId.setText(item.getMemberId());
            weight.setText(String.valueOf(item.getWeight()));
        }

    }
}