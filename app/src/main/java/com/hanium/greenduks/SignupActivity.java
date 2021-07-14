package com.hanium.greenduks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText etName = findViewById(R.id.etName);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPw = findViewById(R.id.etPw);
        EditText etConfirmPw = findViewById(R.id.etConfirmPw);
        EditText etAddress = findViewById(R.id.etAddress);
        Button btnCompSignUp = findViewById(R.id.btnCompSignUp);

        btnCompSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
