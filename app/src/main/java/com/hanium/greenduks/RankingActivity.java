package com.hanium.greenduks;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class RankingActivity extends AppCompatActivity {

    ImageView iv_menu;
    DrawerLayout drawerLayout;

    RecyclerView rRecyclerView;
    RankingAdapter rRecyclerAdapter;
    ArrayList<Ranking> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        iv_menu = findViewById(R.id.iv_menu);
        drawerLayout = findViewById(R.id.drawer_layout);
        iv_menu.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.LEFT));


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
