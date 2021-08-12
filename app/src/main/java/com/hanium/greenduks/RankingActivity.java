package com.hanium.greenduks;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class RankingActivity extends AppCompatActivity {

    RecyclerView rRecyclerView;
    RankingAdapter rRecyclerAdapter;
    ArrayList<Ranking> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        rRecyclerView = (RecyclerView)findViewById(R.id.rvRanking);

        /* initiate adapter */
        rRecyclerAdapter = new RankingAdapter();

        /* initiate recyclerview */
        rRecyclerView.setAdapter(rRecyclerAdapter);
        rRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        /* adapt data */
       list = new ArrayList<Ranking>();
       for(int i = 1; i <= 10; i++){
           list.add(new Ranking(i, "testId", 10));
       }

       rRecyclerAdapter.setRankList(list);
    }

}
