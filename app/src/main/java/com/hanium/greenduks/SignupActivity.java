package com.hanium.greenduks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.google.android.material.navigation.NavigationView;

public class SignupActivity extends AppCompatActivity implements NavigationInterface, NavigationView.OnNavigationItemSelectedListener, AmplifyInterface {
    DrawerLayout drawerLayout;
    public static Activity SignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        SignUp = SignupActivity.this;

        ImageView iv_menu = findViewById(R.id.iv_menu);
        drawerLayout = findViewById(R.id.drawer_layout);
        TextView toolbar_name = findViewById(R.id.tvToolbar_name);

        initializeLayout(iv_menu, drawerLayout, toolbar_name, "회원 가입");
        setNavigationViewListener();

        amplifyInit(this.getApplicationContext());

        EditText etEmail = findViewById(R.id.etEmail);
        EditText etName = findViewById(R.id.etName);
        EditText etNickName = findViewById(R.id.etNickName);
        EditText etPw = findViewById(R.id.etPw);
        EditText etConfirmPw = findViewById(R.id.etConfirmPw);
        EditText etPhone = findViewById(R.id.etPhone);
        Button btnEmailConfirm = findViewById(R.id.btnEmailConfirm);

        //비밀번호 형식 검사 추가 -> error에서 나옴
        //not null 설정 필요

        btnEmailConfirm.setOnClickListener(view -> {
            if (!etPw.getText().toString().equals(etConfirmPw.getText().toString())) {
                Toast.makeText(SignupActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            AuthSignUpOptions options = AuthSignUpOptions.builder()
                    .userAttribute(AuthUserAttributeKey.email(), etEmail.getText().toString())
                    .build();
            Amplify.Auth.signUp(etName.getText().toString(), etPw.getText().toString(), options,
                    result -> {
                        if (result.isSignUpComplete()) {
                            Intent intent = new Intent(SignupActivity.this, ConfirmEmailActivity.class);
                            intent.putExtra("userName", etName.getText().toString());
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(SignupActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> Log.d("AuthQuickStart", "Sign up failed", error)
            );
        });

        View iv_qr = findViewById(R.id.iv_qr);
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
