package com.hanium.greenduks;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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

import com.amplifyframework.core.Amplify;
import com.google.android.material.navigation.NavigationView;

public class ConfirmEmailActivity extends AppCompatActivity implements AmplifyInterface {
    Handler signUpHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmemail);

        amplifyInit(this.getApplicationContext());

        EditText etConfirmNumber = findViewById(R.id.etConfirmNumber);
        Button btnCompSignUp = findViewById(R.id.btnCompSignUp);

        btnCompSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etConfirmNumber.getText().toString().replace(" ", "").equals("") || etConfirmNumber.getText() == null) {
                    Toast.makeText(ConfirmEmailActivity.this, "인증 번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent signUpIntent = getIntent();
                Amplify.Auth.confirmSignUp(
                        signUpIntent.getStringExtra("userName"),
                        etConfirmNumber.getText().toString(),
                        result ->
                        {
                            if (result.isSignUpComplete()) {
                                Intent intent = new Intent(ConfirmEmailActivity.this, LoginActivity.class);
                                startActivity(intent);
                                SignupActivity.SignUp.finish();
                                finish();
                            }
                            else {
                                Message turnAlertMsg = new Message();
                                turnAlertMsg.obj = result.toString();
                                signUpHandler.sendMessage(turnAlertMsg);
                            }
                        },
                        error -> {
                            Message turnAlertMsg = new Message();
                            turnAlertMsg.obj = error.getMessage();
                            signUpHandler.sendMessage(turnAlertMsg);
                        }
                );
            }
        });
        signUpHandler = new Handler(Looper.getMainLooper(), msg -> {
            Toast.makeText(ConfirmEmailActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
            return false;
        });
    }
}

