package com.hanium.greenduks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SeparateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_separate);

        Button btnScan = findViewById(R.id.btnScan);
        Button btnCompSeparate = findViewById(R.id.btnCompSeparate);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SeparateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnCompSeparate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SeparateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
