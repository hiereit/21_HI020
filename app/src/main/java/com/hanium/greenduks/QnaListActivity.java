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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Answer;
import com.amplifyframework.datastore.generated.model.Question;
import com.google.android.material.navigation.NavigationView;

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
    String state = "";
    String title = "";
    String date = "";
    ArrayList<String> titleList = new ArrayList<>();
    ArrayList<String> dateList = new ArrayList<>();
    ArrayList<String> stateList = new ArrayList<>();

    ArrayList<Qna> qnaList = new ArrayList<Qna>();

    Handler getStateHandler;
    Handler getTitleAndDateHandler;
    Handler getDateHandler;

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
            Intent i = new Intent(QnaListActivity.this, AuthActivity.class);
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

        getTitleAndDateHandler = new Handler(Looper.getMainLooper(), msg -> {
            title = msg.obj.toString();

//            Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!titleList: " + titleList);
//            Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!!dateList: " + dateList);
//
            Log.d(TAG, "여기가! " + title);

            return false;
        });

        setList(userId);


        qRecyclerAdapter.setQnaList(list);
    }

    public void setList(String userId){
        Amplify.API.query(
                ModelQuery.list(Question.class, Question.USER_ID.contains(userId)),
                response -> {
                    Message titleMsg = new Message();

                    getStateHandler = new Handler(Looper.getMainLooper(), msg -> {
                        state = msg.obj.toString();
                        stateList.add(state);

                        Log.d(TAG, "!!!!!!!!stateList: " + stateList.toString());
                        Log.d(TAG, "!!!!!!!!state????: " + state);
                        return false;
                    });

                    for (Question question : response.getData()) {
                        Log.i("MyAmplifyApp", question.getTitle());

                        setAnswerState(userId, question.getId());

                        titleList.add(question.getTitle());
                        dateList.add(question.getDate());

                        list.add(new Qna(state, question.getTitle(), question.getDate()));
//                        Log.d(TAG, "getStateHandler에서??????????? " + list.toString());
                    }
                    titleMsg.obj = list;
                    getTitleAndDateHandler.sendMessage(titleMsg);
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );
    }

    public void setAnswerState(String userId, String questionId){
        Amplify.API.query(
                ModelQuery.get(Answer.class, questionId),
                response -> {
                    Message turnAlertMsg = new Message();
                    if(response.getData() == null){
                        turnAlertMsg.obj = "미답변";
                        state = "미답변";
                    }else{
                        turnAlertMsg.obj = "답변완료";
                        state = "답변완료";
                    }
                    getStateHandler.sendMessage(turnAlertMsg);
                },
                error -> Log.e("MyAmplifyApp", error.toString(), error)
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
