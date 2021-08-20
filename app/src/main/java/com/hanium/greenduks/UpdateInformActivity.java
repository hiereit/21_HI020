package com.hanium.greenduks;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class UpdateInformActivity extends AppCompatActivity implements NavigationInterface, NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateinform);
        ImageView iv_menu = findViewById(R.id.iv_menu);
        drawerLayout = findViewById(R.id.drawer_layout);
        TextView toolbar_name = findViewById(R.id.tvToolbar_name);

        initializeLayout(iv_menu, drawerLayout, toolbar_name, "마이 페이지");
        setNavigationViewListener();


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
                finish();
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
                                        finish();
                                    }
                                })
                        .setNegativeButton("아니오", null);
                builder.show();
            }
        });
        ImageView iv_qr = findViewById(R.id.iv_qr);
        iv_qr.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), QrScanActivity.class);
            startActivity(intent);
            finish();
        });
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        startActivity(nextIntent(item, this, drawerLayout));
        finish();
        return true;
    }

    public void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
    }
}
