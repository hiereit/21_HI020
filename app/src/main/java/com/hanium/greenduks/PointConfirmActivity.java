package com.hanium.greenduks;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.vaibhavlakhera.circularprogressview.CircularProgressView;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.util.ArrayList;

public class PointConfirmActivity extends AppCompatActivity implements NavigationInterface, NavigationView.OnNavigationItemSelectedListener{

    ImageView iv_menu;
    DrawerLayout drawerLayout;
    ImageView iv_qr;

    CircularProgressView circularProgressView;
    BarChart mBarChart;

    RecyclerView rRecyclerView;
    UsedPointAdapter upRecyclerAdapter;
    ArrayList<UsedPoint> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pointconfirm);

        iv_menu = findViewById(R.id.iv_menu);
        drawerLayout = findViewById(R.id.drawer_layout);
        iv_menu.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.LEFT));
        TextView toolbar_name = findViewById(R.id.tvToolbar_name);

        initializeLayout(iv_menu, drawerLayout, toolbar_name, "포인트 확인");
        setNavigationViewListener();

        iv_qr = findViewById(R.id.iv_qr);
        iv_qr.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), QrScanActivity.class);
            startActivity(intent);
        });

        circleProgress();
        setBarChart();

        rRecyclerView = (RecyclerView)findViewById(R.id.rvUsedPoint);

        /* initiate adapter */
        upRecyclerAdapter = new UsedPointAdapter();

        /* initiate recyclerview */
        rRecyclerView.setAdapter(upRecyclerAdapter);
        rRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        /* adapt data */
        list = new ArrayList<UsedPoint>();
        for(int i = 1; i <= 10; i++){
            list.add(new UsedPoint(10000, "환급", "2021.08." + i));
        }
        upRecyclerAdapter.setPointList(list);
    }


    private void circleProgress(){
        circularProgressView=findViewById(R.id.cpb_circlebar);
        circularProgressView.setProgress(5000, true); //현재 value
    }

    private void setBarChart(){
        mBarChart = (BarChart) findViewById(R.id.barchart);

        mBarChart.addBar(new BarModel("12", 300,0xFF56B7F1));
        mBarChart.addBar(new BarModel("13", 100,  0xFF56B7F1));
        mBarChart.addBar(new BarModel("14", 130,0xFF56B7F1));
        mBarChart.addBar(new BarModel("15", 105,0xFF56B7F1));
        mBarChart.addBar(new BarModel("16",106, 0xFF56B7F1));
        mBarChart.addBar(new BarModel("17",135, 0xFF56B7F1));
        mBarChart.addBar(new BarModel("18",235, 0xFF56B7F1));
        mBarChart.addBar(new BarModel("19",123,  0xFF56B7F1));
        mBarChart.addBar(new BarModel("20",311,  0xFF56B7F1));
        mBarChart.addBar(new BarModel("21",453,  0xFF56B7F1));
        mBarChart.addBar(new BarModel("22",234,  0xFF56B7F1));
        mBarChart.startAnimation();
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
