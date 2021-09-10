package com.hanium.greenduks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

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

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserState;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthChannelEventName;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.InitializationStatus;
import com.amplifyframework.datastore.generated.model.Point;
import com.amplifyframework.hub.HubChannel;
import com.google.android.material.navigation.NavigationView;
import com.vaibhavlakhera.circularprogressview.CircularProgressView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements NavigationInterface, NavigationView.OnNavigationItemSelectedListener, AmplifyInterface{

    private static final String TAG = "MainActivity";

    ImageView iv_menu;
    DrawerLayout drawerLayout;
    ImageView iv_qr;

    Handler weightHandler;
    Handler valueHandler;

    String userId = "";
    CircularProgressView circularProgressView;
    int sumValue = 0;
    Double sumWeight = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amplifyInit(this);
        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
            @Override
            public void onResult(UserStateDetails userStateDetails) {
                Intent i;
                if (userStateDetails.getUserState().equals(UserState.SIGNED_IN)) {
                    return;
                }
                switch (userStateDetails.getUserState()){
                    case SIGNED_OUT:
                        i = new Intent(MainActivity.this, LoginActivity.class);
                        break;
                    default:
                        AWSMobileClient.getInstance().signOut();
                        i = new Intent(MainActivity.this, LoginActivity.class);
                        break;
                }
                startActivity(i);
                finish();
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, e.toString());
            }
        });

        iv_menu = findViewById(R.id.iv_menu);
        drawerLayout = findViewById(R.id.drawer_layout);
        iv_menu.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.LEFT));
        TextView toolbar_name = findViewById(R.id.tvToolbar_name);

        initializeLayout(iv_menu, drawerLayout, toolbar_name, "");
        setNavigationViewListener();

        iv_qr = findViewById(R.id.iv_qr);
        iv_qr.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), QrScanActivity.class);
            startActivity(intent);
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        // xml 파일에서 넣어놨던 header 선언
        View header = navigationView.getHeaderView(0);
        // header에 있는 리소스 가져오기
        ImageView logoutBtn = (ImageView) header.findViewById(R.id.ivNavi_logout);

        logoutBtn.setOnClickListener(v -> {
            AWSMobileClient.getInstance().signOut();
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        });

        userId = AWSMobileClient.getInstance().getUsername();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView tvDate = findViewById(R.id.tvMain_date);
        tvDate.setText(getTodayDate());

        TextView tvWeight = findViewById(R.id.tvMain_weight);
        weightHandler = new Handler(Looper.getMainLooper(), msg -> {
            tvWeight.setText(msg.obj.toString());
            return false;
        });
        getMyWeight(userId);

        circularProgressView=findViewById(R.id.cpb_circlebar);
        valueHandler = new Handler(Looper.getMainLooper(), msg -> {
            circularProgressView.setProgress((Integer) msg.obj, true);  //현재 value
            return false;
        });
        getMyPoint(userId);
        circularProgressView.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, PointConfirmActivity.class);
            startActivity(i);
        });
    }

    public String getTodayDate(){
        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        String korDayOfWeek = "";
        switch(dayOfWeek){
            case 1:
                korDayOfWeek = "일";
                break;
            case 2:
                korDayOfWeek = "월";
                break;
            case 3:
                korDayOfWeek = "화";
                break;
            case 4:
                korDayOfWeek = "수";
                break;
            case 5:
                korDayOfWeek = "목";
                break;
            case 6:
                korDayOfWeek = "금";
                break;
            case 7:
                korDayOfWeek = "토";
                break;
        }

        String today = year + "." + month + "." + date + "." + " (" + korDayOfWeek + ")";
        return today;
    }

    public void getMyWeight(String userId){

        Amplify.API.query(
                ModelQuery.list(Point.class, Point.USER_ID.contains(userId)),
                response -> {
                    Message turnAlertMsg = new Message();
                    sumWeight = 0.0;
                    for (Point point : response.getData()) {
                        Log.i(TAG, point.getWeight().toString());
                        sumWeight += point.getWeight();
                    }
                    turnAlertMsg.obj = sumWeight;
                    weightHandler.sendMessage(turnAlertMsg);
                },
                error -> Log.e(TAG, "Query failure", error)
        );
    }

    private void getMyPoint(String userId){

        Amplify.API.query(
                ModelQuery.list(Point.class, Point.USER_ID.contains(userId)),
                response -> {
                    Message turnAlertMsg = new Message();
                    sumValue = 0;
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = nextIntent(item, this, drawerLayout);
        startActivity(intent);
        return true;
    }

    public void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
    }
}