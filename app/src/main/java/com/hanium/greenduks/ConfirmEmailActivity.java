package com.hanium.greenduks;

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

import com.amplifyframework.core.Amplify;
import com.google.android.material.navigation.NavigationView;

public class ConfirmEmailActivity extends AppCompatActivity implements AmplifyInterface {

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
                                Toast.makeText(ConfirmEmailActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        error -> Log.d("AuthQuickstart", error.toString())
                );
            }
        });
        ImageView iv_qr = findViewById(R.id.iv_qr);
        iv_qr.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), QrScanActivity.class);
            startActivity(intent);
            finish();
        });
    }
}

