package com.hanium.greenduks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Point;
import com.google.android.material.navigation.NavigationView;
import com.vaibhavlakhera.circularprogressview.CircularProgressView;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.util.ArrayList;

public class PointConfirmActivity extends AppCompatActivity implements NavigationInterface, NavigationView.OnNavigationItemSelectedListener, AmplifyInterface{

    private static final String TAG = "PointConfirmActivity";

    ImageView iv_menu;
    DrawerLayout drawerLayout;
    ImageView iv_qr;

    Context context;
    String userId = "";

    CircularProgressView circularProgressView;
    Handler valueHandler;
    int sumValue = 0;
    TextView tvPointConfirm_point;
    TextView tvPointConfirm_remain;

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
            finish();
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        // xml 파일에서 넣어놨던 header 선언
        View header = navigationView.getHeaderView(0);
        // header에 있는 리소스 가져오기
        ImageView logoutBtn = (ImageView) header.findViewById(R.id.ivNavi_logout);

        logoutBtn.setOnClickListener(v -> {
            AWSMobileClient.getInstance().signOut();
            Intent i = new Intent(PointConfirmActivity.this, AuthActivity.class);
            startActivity(i);
            finish();
        });

        context = this.getApplicationContext();
        amplifyInit(context);
        userId = AWSMobileClient.getInstance().getUsername();

        circularProgressView = findViewById(R.id.cpb_circlebar);
        tvPointConfirm_point = findViewById(R.id.tvPointConfirm_point);
        tvPointConfirm_remain = findViewById(R.id.tvPointConfirm_remain);
        valueHandler = new Handler(Looper.getMainLooper(), msg -> {
            circularProgressView.setProgress((Integer) msg.obj, true);  //현재 value
            tvPointConfirm_point.setText(msg.obj.toString());
            tvPointConfirm_remain.setText(String.valueOf(10000-(int)msg.obj));
            return false;
        });
        getMyPoint(userId);


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

    private void getMyPoint(String userId){

        Amplify.API.query(
                ModelQuery.list(Point.class, Point.USER_ID.contains(userId)),
                response -> {
                    Message turnAlertMsg = new Message();
                    for (Point point : response.getData()) {
                        Log.i(TAG, point.getValue().toString());
                        sumValue += point.getValue();
                    }
                    turnAlertMsg.obj = sumValue;
                    valueHandler.sendMessage(turnAlertMsg);
                },
                error -> Log.e(TAG, "Query failure", error)
        );
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
