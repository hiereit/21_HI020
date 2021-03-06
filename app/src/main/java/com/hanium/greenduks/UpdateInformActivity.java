package com.hanium.greenduks;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Point;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

public class UpdateInformActivity extends AppCompatActivity implements NavigationInterface, NavigationView.OnNavigationItemSelectedListener, AmplifyInterface {
    DrawerLayout drawerLayout;
    Handler updateHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateinform);
        ImageView iv_menu = findViewById(R.id.iv_menu);
        drawerLayout = findViewById(R.id.drawer_layout);
        TextView toolbar_name = findViewById(R.id.tvToolbar_name);

        initializeLayout(iv_menu, drawerLayout, toolbar_name, "마이 페이지");
        setNavigationViewListener();

        amplifyInit(this.getApplicationContext());

        EditText etEmail = findViewById(R.id.etEmail);
        EditText etId = findViewById(R.id.etId);
        EditText etPw = findViewById(R.id.etPw);
        EditText etNewPw = findViewById(R.id.etNewPw);
        Button btnCompUpdate = findViewById(R.id.btnCompUpdate);

        Amplify.Auth.fetchUserAttributes(
                attributes -> etEmail.setText(attributes.get(2).getValue()),
                error -> Log.d("yyj", "Failed to fetch user attributes.", error)
        );
        etId.setText(AWSMobileClient.getInstance().getUsername());

        btnCompUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Amplify.Auth.updatePassword(
                        etPw.getText().toString(),
                        etNewPw.getText().toString(),
                        () -> {
                            Message turnAlertMsg = new Message();
                            turnAlertMsg.obj = "비밀번호가 변경되었습니다.";
                            updateHandler.sendMessage(turnAlertMsg);
                        },
                        error -> {
                            Message turnAlertMsg = new Message();
                            turnAlertMsg.obj = "비밀번호 변경에 실패하였습니다.";
                            updateHandler.sendMessage(turnAlertMsg);
                        }
                );
            }
        });

        updateHandler = new Handler(Looper.getMainLooper(), msg -> {
            etPw.setText("");
            etNewPw.setText("");
            Toast.makeText(UpdateInformActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
            return false;
        });

        ImageView iv_qr = findViewById(R.id.iv_qr);
        iv_qr.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), QrScanActivity.class);
            startActivity(intent);
            finish();
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        // xml 파일에서 넣어놨던 header 선언
        View header = navigationView.getHeaderView(0);
        // header에 있는 리소스 가져오기
        ImageView logoutBtn = (ImageView) header.findViewById(R.id.ivNavi_logout);

        logoutBtn.setOnClickListener(v -> {
            AWSMobileClient.getInstance().signOut();
            Intent i = new Intent(UpdateInformActivity.this, LoginActivity.class);
            startActivity(i);
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
