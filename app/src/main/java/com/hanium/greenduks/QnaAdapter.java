package com.hanium.greenduks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class QnaAdapter extends RecyclerView.Adapter<QnaAdapter.ViewHolder> {

    private ArrayList<Qna> list;

    public void setQnaList(ArrayList<Qna> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public QnaAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_qna, parent, false);
        return new QnaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull QnaAdapter.ViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView state;
        TextView title;
        TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            state = itemView.findViewById(R.id.tvQna_state);
            title = itemView.findViewById(R.id.tvQna_title);
            date = itemView.findViewById(R.id.tvQna_date);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        //해당 pos의 QnaBoard 페이지로 이동하는 코드 작성
                    }
                }
            });
        }

        void onBind(Qna item) {
            state.setText(item.getState());
            title.setText(item.getTitle());
            date.setText(item.getDate());
        }
    }
}