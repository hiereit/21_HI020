package com.hanium.greenduks;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailNoticeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailnotice);

        TextView tvNoticeTitle = findViewById(R.id.tvNoticeTitle);
        TextView tvNoticeContent = findViewById(R.id.tvNoticeContent);
    }
}
