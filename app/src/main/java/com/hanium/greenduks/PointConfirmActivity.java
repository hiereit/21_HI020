package com.hanium.greenduks;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.vaibhavlakhera.circularprogressview.CircularProgressView;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

public class PointConfirmActivity extends AppCompatActivity{

    CircularProgressView circularProgressView;
    BarChart mBarChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pointconfirm);


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
