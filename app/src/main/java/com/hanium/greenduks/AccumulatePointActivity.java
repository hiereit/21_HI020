package com.hanium.greenduks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Point;

import java.util.UUID;

public class AccumulatePointActivity extends AppCompatActivity implements AmplifyInterface {
    Handler createHandler;
    Handler valueHandler;
    int sumValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accumulatepoint);

        amplifyInit(this);

        TextView tvPoint = findViewById(R.id.tvPoint);
        TextView tvCumulativePoint = findViewById(R.id.tvCumulativePoint);
        TextView tvWeight = findViewById(R.id.tvWeight);
        Button btnConfirmPoint = findViewById(R.id.btnConfirmPoint);
        String userId = AWSMobileClient.getInstance().getUsername();

        int pointValue = 300;
        double weight = 1.2;

        //weight -> 서버로 보내거나 / db에서 읽어와서 계산
        //point -> 무게에 따라 point 계산

        String uniqueID = UUID.randomUUID().toString();
        Point point = Point.builder()
                .userId(userId)
                .date(String.valueOf(System.currentTimeMillis()))
                .value(pointValue)
                .id(uniqueID)
                .weight(weight)
                .build();

        Amplify.API.mutate(ModelMutation.create(point),
                response -> {
                    Message turnAlertMsg = new Message();
                    turnAlertMsg.arg1 = response.getData().getValue();
                    turnAlertMsg.obj = response.getData().getWeight();
                    createHandler.sendMessage(turnAlertMsg);
                    Log.d("yyj", "축적: " + uniqueID);
                },
                error -> Log.e("yyj", "Create failed", error)
        );

        createHandler = new Handler(Looper.getMainLooper(), msg -> {
            tvPoint.setText(String.valueOf(msg.arg1));
            tvWeight.setText(String.valueOf(msg.obj));

            Amplify.API.query(
                    ModelQuery.list(Point.class, Point.USER_ID.contains(userId)),
                    res -> {
                        sumValue = 0;
                        Message turnAlertMsg = new Message();
                        for (Point p : res.getData()) {
                            sumValue += p.getValue();
                        }
                        turnAlertMsg.obj = sumValue;
                        valueHandler.sendMessage(turnAlertMsg);
                    },
                    error -> Log.d("yyj", "Query failure", error)
            );
            return false;
        });

        valueHandler = new Handler(Looper.getMainLooper(), msg -> {
            tvCumulativePoint.setText(msg.obj.toString());
            Log.d("yyj", "전체 point: " + msg.obj.toString());
            return false;
        });

        btnConfirmPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
