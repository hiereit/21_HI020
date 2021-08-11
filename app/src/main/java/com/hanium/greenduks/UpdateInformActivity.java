package com.hanium.greenduks;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateInformActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateinform);

        EditText etName = findViewById(R.id.etName);
        EditText etNickName = findViewById(R.id.etNickName);
        EditText etEmail = findViewById(R.id.etId);
        EditText etPw = findViewById(R.id.etPw);
        EditText etConfirmPw = findViewById(R.id.etConfirmPw);
        EditText etPhone = findViewById(R.id.etPhone);
        Button btnCompUpdate = findViewById(R.id.btnCompUpdate);
        Button btnWithdrawal = findViewById(R.id.btnWithdrawal);

        btnCompUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateInformActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnWithdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateInformActivity.this);

                builder.setMessage("회원 탈퇴를 진행하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(UpdateInformActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                })
                        .setNegativeButton("아니오", null);
                builder.show();
            }
        });
    }
}
