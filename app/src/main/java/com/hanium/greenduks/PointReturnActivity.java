package com.hanium.greenduks;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Point;
import com.amplifyframework.datastore.generated.model.Question;
import com.google.android.material.navigation.NavigationView;
import com.vaibhavlakhera.circularprogressview.CircularProgressView;

import java.util.UUID;

public class PointReturnActivity extends AppCompatActivity implements NavigationInterface, NavigationView.OnNavigationItemSelectedListener, AmplifyInterface{

    private static final String TAG = "PointReturnActivity";

    ImageView iv_menu;
    DrawerLayout drawerLayout;
    ImageView iv_qr;
    String[] bank_items = {"국민은행", "농협은행", "기업은행", "신한은행", "우리은행", "하나은행", "카카오뱅크"};
    Button confirmBtn;
    TextView availablePoint;

    CircularProgressView circularProgressView;
    String userId = "";
    int sumValue = 0;
    int returnPoint = 0;

    Handler valueHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pointreturn);
        amplifyInit(this);
        userId = AWSMobileClient.getInstance().getUsername();

        iv_menu = findViewById(R.id.iv_menu);
        drawerLayout = findViewById(R.id.drawer_layout);
        iv_menu.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.LEFT));
        TextView toolbar_name = findViewById(R.id.tvToolbar_name);

        initializeLayout(iv_menu, drawerLayout, toolbar_name, "포인트 환급");
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
            Intent i = new Intent(PointReturnActivity.this, AuthActivity.class);
            startActivity(i);
            finish();
        });

        circularProgressView = findViewById(R.id.cpb_circlebar);
        availablePoint = findViewById(R.id.tvPointReturn_availablePoint);
        confirmBtn = findViewById(R.id.btnPointReturn_confirm);

        valueHandler = new Handler(Looper.getMainLooper(), msg -> {
            int nowPoint = (int) msg.obj;

            circularProgressView.setProgress((Integer) msg.obj, true);  //현재 value

            if(nowPoint >= 10000){
                confirmBtn.setEnabled(true);
                while(nowPoint >= 10000){
                    returnPoint += 10000;
                    nowPoint -= 10000;
                }
                availablePoint.setText(String.valueOf(returnPoint));
            } else{
                availablePoint.setText(String.valueOf(0));
            }

            String uniqueID = UUID.randomUUID().toString();
            confirmBtn.setOnClickListener(v -> {
                Point item = Point.builder()
                        .userId(userId)
                        .date(String.valueOf(System.currentTimeMillis()))
                        .value(-returnPoint)
                        .weight((double) 0)
                        .id(uniqueID)
                        .build();

                Amplify.API.mutate(ModelMutation.create(item),
                        response -> Log.d(TAG, "Todo with id: " + response.getData().getId()),
                        error -> Log.d(TAG, "Create failed", error)
                );

                Intent i = new Intent(PointReturnActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            });

            return false;
        });
        getMyPoint(userId);

        Spinner spinner = findViewById(R.id.spinner_bank);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
               this, android.R.layout.simple_spinner_item, bank_items);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
        finish();
        return true;
    }

    public void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
    }
}
