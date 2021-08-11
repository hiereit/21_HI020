package com.hanium.greenduks;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmStatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmstatus);

        TextView tvUserName = findViewById(R.id.tvUserName);
        TextView tvCurrentDate = findViewById(R.id.tvCurrentDate);
        TextView tvTotalWeight = findViewById(R.id.tvTotalWeight);
        TextView tvCumulativePoint2 = findViewById(R.id.tvCumulativePoint2);
        TextView tvLatestAddress = findViewById(R.id.tvLatestAddress);
    }
}

