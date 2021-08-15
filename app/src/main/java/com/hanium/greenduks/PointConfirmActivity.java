package com.hanium.greenduks;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.vaibhavlakhera.circularprogressview.CircularProgressView;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

public class PointConfirmActivity extends AppCompatActivity{

    ImageView iv_menu;
    DrawerLayout drawerLayout;

    CircularProgressView circularProgressView;
    BarChart mBarChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pointconfirm);

        iv_menu = findViewById(R.id.iv_menu);
        drawerLayout = findViewById(R.id.drawer_layout);
        iv_menu.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.LEFT));


        circularProgressView=findViewById(R.id.cpb_circlebar);
        circularProgressView.setProgress(5000, true); //현재 value

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
