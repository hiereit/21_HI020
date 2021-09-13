package com.hanium.greenduks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserState;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Box;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements AmplifyInterface {
    EditText etLoginId;
    EditText etLoginPw;
    Handler loginHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        amplifyInit(this.getApplicationContext());

        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
            @Override
            public void onResult(UserStateDetails userStateDetails) {
                if (userStateDetails.getUserState().equals(UserState.SIGNED_IN)) {
                    Class nextActivity;
                    if (AWSMobileClient.getInstance().getUsername().equals("som")) {
                        nextActivity = CenterMainActivity.class;
                    }
                    else {
                        nextActivity = MainActivity.class;
                    }
                    Intent intent = new Intent(LoginActivity.this, nextActivity);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onError(Exception e) {
                Log.e("LoginActivity", e.toString());
            }
        });

        etLoginId = findViewById(R.id.etLoginId);
        etLoginPw = findViewById(R.id.etLoginPw);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnSignUp = findViewById(R.id.btnSignUp);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etLoginId.getText().toString().replace(" ", "").equals("") || etLoginId.getText() == null) {
                    Toast.makeText(LoginActivity.this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (etLoginPw.getText().toString().replace(" ", "").equals("") || etLoginPw.getText() == null) {
                    Toast.makeText(LoginActivity.this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Amplify.Auth.signIn(
                        etLoginId.getText().toString(),
                        etLoginPw.getText().toString(),
                        result -> {
                            if (result.isSignInComplete()){
                                Class nextActivity;
                                if (AWSMobileClient.getInstance().getUsername().equals("som")) {
                                    nextActivity = CenterMainActivity.class;
                                }
                                else {
                                    nextActivity = MainActivity.class;
                                }
                                Intent intent = new Intent(LoginActivity.this, nextActivity);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Message turnAlertMsg = new Message();
                                turnAlertMsg.obj = result.toString();
                                loginHandler.sendMessage(turnAlertMsg);
                            }
                        },
                        error -> {
                            Message turnAlertMsg = new Message();
                            turnAlertMsg.obj = error.getMessage();
                            loginHandler.sendMessage(turnAlertMsg);
                        }
                );
            }
        });

        loginHandler = new Handler(Looper.getMainLooper(), msg -> {
            Toast.makeText(LoginActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
            return false;
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
