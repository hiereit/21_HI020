package com.hanium.greenduks;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobile.client.AWSMobileClient;

import java.text.SimpleDateFormat;

public class ConfirmStatusActivity extends AppCompatActivity implements AmplifyInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmstatus);

        amplifyInit(this);

        TextView tvUserName = findViewById(R.id.tvUserName);
        TextView tvCurrentDate = findViewById(R.id.tvCurrentDate);
        TextView tvTotalWeight = findViewById(R.id.tvTotalWeight);
        TextView tvCumulativePoint2 = findViewById(R.id.tvCumulativePoint2);
        TextView tvLatestAddress = findViewById(R.id.tvLatestAddress);

        tvUserName.setText(AWSMobileClient.getInstance().getUsername());

        SimpleDateFormat sdfm = new SimpleDateFormat ( "yyyy.MM.dd");
        tvCurrentDate.setText(sdfm.format (System.currentTimeMillis()));


    }
}

