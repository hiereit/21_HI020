package com.hanium.greenduks;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vaibhavlakhera.circularprogressview.CircularProgressView;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.util.ArrayList;

public class PointConfirmActivity extends AppCompatActivity{

    ImageView iv_menu;
    DrawerLayout drawerLayout;

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
        toolbar_name.setText("포인트 확인");

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
}
