package com.hanium.greenduks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vaibhavlakhera.circularprogressview.CircularProgressView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CenterMainAdapter extends RecyclerView.Adapter<CenterMainAdapter.ViewHolder> {

    private ArrayList<CenterMain> list;

    public void setCenterMainList(ArrayList<CenterMain> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public CenterMainAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_center_main, parent, false);
        return new CenterMainAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CenterMainAdapter.ViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CircularProgressView circularProgressView;
        TextView addr;
        TextView boxId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            circularProgressView = itemView.findViewById(R.id.cpb_center_circlebar);
            addr = itemView.findViewById(R.id.tvCenterMain_addr);
            boxId = itemView.findViewById(R.id.tvCenterMain_boxId);
        }

        void onBind(CenterMain item) {
            circularProgressView.setProgress((int) item.getWeight(), true); //현재 value
            addr.setText(item.getAddr());
            boxId.setText(String.valueOf(item.getBoxId()));
        }
    }
}