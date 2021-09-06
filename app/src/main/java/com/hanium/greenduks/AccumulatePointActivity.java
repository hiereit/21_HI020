package com.hanium.greenduks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Point;

import java.util.UUID;

public class AccumulatePointActivity extends AppCompatActivity implements AmplifyInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accumulatepoint);

        amplifyInit(this);

        TextView tvPoint = findViewById(R.id.tvPoint);
        TextView tvCumulativePoint = findViewById(R.id.tvCumulativePoint);
        TextView tvWeight = findViewById(R.id.tvWeight);
        Button btnConfirmPoint = findViewById(R.id.btnConfirmPoint);
        int value = 0;

        String uniqueID = UUID.randomUUID().toString();
        Point todo = Point.builder()
                .userId(AWSMobileClient.getInstance().getUsername())
                .date(String.valueOf(System.currentTimeMillis()))
                .value(value)
                .id(uniqueID)
                .weight(1.2)
                .build();

        Amplify.API.mutate(ModelMutation.create(todo),
                response -> Log.i("MyAmplifyApp", "Todo with id: " + response.getData().getId()),
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );

        btnConfirmPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccumulatePointActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
