package com.hanium.greenduks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AccumulatePointActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accumulatepoint);

        TextView tvPoint = findViewById(R.id.tvPoint);
        TextView tvCumulativePoint = findViewById(R.id.tvCumulativePoint);
        TextView tvWeight = findViewById(R.id.tvWeight);
        Button btnConfirmPoint = findViewById(R.id.btnConfirmPoint);

        btnConfirmPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccumulatePointActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
