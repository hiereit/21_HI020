package com.hanium.greenduks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class UsedPointAdapter extends RecyclerView.Adapter<UsedPointAdapter.ViewHolder>{

    private ArrayList<UsedPoint> list;

    public void setPointList(ArrayList<UsedPoint> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public UsedPointAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_point, parent, false);
        return new UsedPointAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UsedPointAdapter.ViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView point;
        TextView content;
        TextView date;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            point = itemView.findViewById(R.id.tvUsedPoint_point);
            content = itemView.findViewById(R.id.tvUsedPoint_content);
            date = itemView.findViewById(R.id.tvUsedPoint_date);
        }

        void onBind(UsedPoint item){
            point.setText(String.valueOf(item.getPoint()));
            content.setText(item.getContent());
            date.setText(item.getDate());
        }

    }
}
