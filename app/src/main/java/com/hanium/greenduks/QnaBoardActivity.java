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

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Answer;
import com.amplifyframework.datastore.generated.model.Question;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class QnaBoardActivity extends AppCompatActivity implements NavigationInterface, NavigationView.OnNavigationItemSelectedListener, AmplifyInterface{

    private static final String TAG = "QnaBoardActivity";

    ImageView iv_menu;
    DrawerLayout drawerLayout;
    ImageView iv_qr;

    Context context;

    TextView questionTitle;
    TextView questionContent;
    TextView answer;

    Handler questionHandler;
    Handler answerHandler;

    ArrayList<String> msgList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qnaboard);

        iv_menu = findViewById(R.id.iv_menu);
        drawerLayout = findViewById(R.id.drawer_layout);
        iv_menu.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.LEFT));
        TextView toolbar_name = findViewById(R.id.tvToolbar_name);

        initializeLayout(iv_menu, drawerLayout, toolbar_name, "문의하기");
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
            Intent i = new Intent(QnaBoardActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        });

        questionTitle = findViewById(R.id.tvQnaBoard_title);
        questionContent = findViewById(R.id.tvQnaBoard_question);
        answer = findViewById(R.id.tvQnaBoard_answer);

        context = this.getApplicationContext();
        amplifyInit(context);
        String qid = getIntent().getStringExtra("qid");
        if(qid != null){
            Log.d(TAG, "qid: " + qid);
        }

        msgList = new ArrayList<String>();
        questionHandler = new Handler(Looper.getMainLooper(), msg -> {

            msgList = (ArrayList<String>) msg.obj;

            questionTitle.setText(msgList.get(0));
            questionContent.setText(msgList.get(1));

            return false;
        });
        getQuestion(qid);

        answerHandler = new Handler(Looper.getMainLooper(), msg -> {
            if(msg.obj != null) {
                answer.setText(msg.obj.toString());
            }
            return false;
        });
        getAnswer(qid);

    }

    private void getQuestion(String qid) {
        Amplify.API.query(
                ModelQuery.get(Question.class, qid),
                response -> {
                    Log.i("aty", ((Question) response.getData()).getContent());

                    Message turnAlertMsg = new Message();

                    msgList.add(response.getData().getTitle());
                    msgList.add(response.getData().getContent());
                    turnAlertMsg.obj = msgList;
                    questionHandler.sendMessage(turnAlertMsg);
                    Log.d(TAG, "msgList in getQuestion method: " + msgList);
                },
                error -> Log.e("aty", error.toString(), error)
        );
    }

    private void getAnswer(String qid){
        Amplify.API.query(
                ModelQuery.list(Answer.class, Answer.QUESTION_ID.contains(qid)),
                response -> {
                    Message turnAlertMsg = new Message();
                    for (Answer answer : response.getData()) {
                        Log.i("MyAmplifyApp", answer.getContent());
                        turnAlertMsg.obj = answer.getContent();
                    }
                    answerHandler.sendMessage(turnAlertMsg);
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
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
