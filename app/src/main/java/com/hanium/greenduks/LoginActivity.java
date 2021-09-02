package com.hanium.greenduks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements NavigationInterface, NavigationView.OnNavigationItemSelectedListener, AmplifyInterface {
    private String[] members = new String[]{"일반 회원", "수거 업체", "관리자"};
    private int member;
    DrawerLayout drawerLayout;
    EditText etLoginId;
    EditText etLoginPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView iv_menu = findViewById(R.id.iv_menu);
        drawerLayout = findViewById(R.id.drawer_layout);
        TextView toolbar_name = findViewById(R.id.tvToolbar_name);

        initializeLayout(iv_menu, drawerLayout, toolbar_name, "로그인");
        setNavigationViewListener();

        amplifyInit(this.getApplicationContext());

        etLoginId = findViewById(R.id.etLoginId);
        etLoginPw = findViewById(R.id.etLoginPw);
        Spinner spinner = findViewById(R.id.spinnerMember);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnSignUp = findViewById(R.id.btnSignUp);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, members);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                member = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                member = 0;
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Amplify.Auth.signIn(
                        etLoginId.getText().toString(),
                        etLoginPw.getText().toString(),
                        result -> {
                            if (result.isSignInComplete()){
                                Class nextActivity = null;
                                if (member == 0) {
                                    nextActivity = MainActivity.class;
                                }
                                else if (member == 1) {
                                    nextActivity = CenterMainActivity.class;
                                }
                                else {
                                    nextActivity = QnaListActivity.class;
                                }
                                Intent intent = new Intent(LoginActivity.this, nextActivity);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show();
                            }
                        },
                        error -> {
                            Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show();
                        }
                );
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
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
