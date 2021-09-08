package com.hanium.greenduks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Patterns;
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

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity implements AmplifyInterface {
    public static Activity SignUp;
    EditText etEmail;
    EditText etName;
    EditText etPw;
    EditText etConfirmPw;
    String errMsg;
    Handler signUpHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        SignUp = SignupActivity.this;

        amplifyInit(this.getApplicationContext());

        etEmail = findViewById(R.id.etEmail);
        etName = findViewById(R.id.etId);
        etPw = findViewById(R.id.etPw);
        etConfirmPw = findViewById(R.id.etConfirmPw);
        Button btnEmailConfirm = findViewById(R.id.btnEmailConfirm);

        //비밀번호 형식 검사 추가 -> error에서 나옴
        //not null 설정 필요

        btnEmailConfirm.setOnClickListener(view -> {
            if (!checkFields()) {
                Toast.makeText(SignupActivity.this, errMsg, Toast.LENGTH_SHORT).show();
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
                            Message turnAlertMsg = new Message();
                            turnAlertMsg.obj = "아이디 또는 비밀번호를 확인하세요.";
                            signUpHandler.sendMessage(turnAlertMsg);
                        }
                    },
                    error -> {
                        Message turnAlertMsg = new Message();
                        turnAlertMsg.obj = error.getMessage();
                        signUpHandler.sendMessage(turnAlertMsg);
                    }
            );
        });

        signUpHandler = new Handler(Looper.getMainLooper(), msg -> {
            Toast.makeText(SignupActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
            return false;
        });
    }

    public boolean checkFields() {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        if (!pattern.matcher(etEmail.getText().toString()).matches()) {
            errMsg = "이메일 주소를 확인하세요.";
            return false;
        }
        else if (etName.getText().toString().replace(" ", "").equals("") || etName.getText() == null) {
            errMsg = "아이디를 입력하세요";
            return false;
        }
        else if (etPw.getText().toString().replace(" ", "").equals("") || etPw.getText() == null) {
            errMsg = "비밀번호를 입력하세요";
            return false;
        }
        else if (etPw.getText().length() < 8) {
            errMsg = "비밀번호는 8자 이상이어야 합니다.";
            return false;
        }
        else if (etConfirmPw.getText().toString().replace(" ", "").equals("") || etConfirmPw.getText() == null) {
            errMsg = "비밀번호 확인란을 입력하세요";
            return false;
        }
        else if (!etPw.getText().toString().equals(etConfirmPw.getText().toString())) {
            errMsg = "비밀번호가 일치하지 않습니다.";
            return false;
        }
        return true;
    }
}
