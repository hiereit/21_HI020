package com.hanium.greenduks;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Question;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class QnaListActivity extends AppCompatActivity implements NavigationInterface, NavigationView.OnNavigationItemSelectedListener, AmplifyInterface{

    private static final String TAG = "QnaListActivity";

    ImageView iv_menu;
    DrawerLayout drawerLayout;
    ImageView iv_qr;

    RecyclerView qRecyclerView;
    QnaAdapter qRecyclerAdapter;
    ArrayList<Qna> list;

    Context context;
    String userId = "";

    Handler handler;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qnalist);

        iv_menu = findViewById(R.id.iv_menu);
        drawerLayout = findViewById(R.id.drawer_layout);
        iv_menu.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.LEFT));
        TextView toolbar_name = findViewById(R.id.tvToolbar_name);
        initializeLayout(iv_menu, drawerLayout, toolbar_name, "문의내역");
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
            Intent i = new Intent(QnaListActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        });
        context = this.getApplicationContext();
        amplifyInit(context);
        userId = AWSMobileClient.getInstance().getUsername();


        qRecyclerView = (RecyclerView)findViewById(R.id.rvQna);

        /* initiate adapter */
        qRecyclerAdapter = new QnaAdapter();

        /* initiate recyclerview */
        qRecyclerView.setAdapter(qRecyclerAdapter);
        qRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        /* adapt data */
        list = new ArrayList<Qna>();
        qRecyclerAdapter.setQnaList(list);

        userId = AWSMobileClient.getInstance().getUsername();
        handler = new Handler(Looper.getMainLooper(), msg -> {
            Log.d(TAG, msg.obj.toString());
            list = (ArrayList<Qna>) msg.obj;

            qRecyclerAdapter.setQnaList(list);

            qRecyclerAdapter.setOnItemClickListener(new QnaAdapter.ClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    Intent intent = new Intent(context, QnaBoardActivity.class);
                    Log.d(TAG, "onItemClick intent --> id: " + list.get(position).getId());
                    intent.putExtra("qid", list.get(position).getId());
                    startActivity(intent);
                    finish();
                }
            });

            return false;
        });
        getQuestion(userId);

    }

    private void getQuestion(String userId) {
        Amplify.API.query(
                ModelQuery.list(Question.class, Question.USER_ID.contains(userId)),
                response -> {
                    Message turnAlertMsg = new Message();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    for (Question question : response.getData()) {
                        Log.i("MyAmplifyApp", question.getId());
                        list.add(new Qna(question.getId(), question.getState(), question.getTitle(), simpleDateFormat.format(Long.valueOf(question.getDate()))));
                    }
                    turnAlertMsg.obj = list;
                    handler.sendMessage(turnAlertMsg);
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d("yyj", "item: " + item);
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
