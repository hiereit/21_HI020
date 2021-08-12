package com.hanium.greenduks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class QnaRegisterActivity extends AppCompatActivity {

    Button registerBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qnaregister);

        registerBtn = (Button)findViewById(R.id.btnQnaBoard_register);
        registerBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QnaListActivity.class);
                startActivity(intent);
            }
        });
    }

}
