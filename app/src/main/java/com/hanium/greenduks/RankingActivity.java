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

import java.util.ArrayList;


public class RankingActivity extends AppCompatActivity implements NavigationInterface, NavigationView.OnNavigationItemSelectedListener{

    ImageView iv_menu;
    DrawerLayout drawerLayout;
    ImageView iv_qr;

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

        iv_qr = findViewById(R.id.iv_qr);
        iv_qr.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), QrScanActivity.class);
            startActivity(intent);
            finish();
        });

        rRecyclerView = (RecyclerView)findViewById(R.id.rvRanking);

        /* initiate adapter */
        rRecyclerAdapter = new RankingAdapter();

        /* initiate recyclerview */
        rRecyclerView.setAdapter(rRecyclerAdapter);
        rRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        /* adapt data */
       list = new ArrayList<Ranking>();
       for(int i = 0; i <= 10; i++){
           int imgName = -1;
           list.add(new Ranking(i+1, "testNickname", 10-i));

           switch (list.get(i).getRank()){
               case 1:
                   imgName = R.drawable.goldmedal_img;
                   break;
               case 2:
                   imgName = R.drawable.silvermedal_img;
                   break;
               case 3:
                   imgName = R.drawable.bronzemedal_img;
                   break;
               default:
                   imgName = R.drawable.white_img;
                   break;
           }
           list.get(i).setRankImg(imgName);
       }

       rRecyclerAdapter.setRankList(list);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = nextIntent(item, this, drawerLayout);
        startActivity(intent);
        finish();
        return true;
    }

    public void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
    }
}
