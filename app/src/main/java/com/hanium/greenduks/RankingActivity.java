package com.hanium.greenduks;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class RankingActivity extends AppCompatActivity implements NavigationInterface, NavigationView.OnNavigationItemSelectedListener{

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
        TextView toolbar_name = findViewById(R.id.tvToolbar_name);
        initializeLayout(iv_menu, drawerLayout, toolbar_name, "수거왕");
        setNavigationViewListener();

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d("yyj", "item: " + item);
        Intent intent = nextIntent(item, this, drawerLayout);
        startActivity(intent);
        return true;
    }

    public void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
    }
}
