package com.hanium.greenduks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeLayout(""); //toolbar, navigation설정 (param: toolbar 이름)
        setNavigationViewListener();

    }

    public void initializeLayout(String name)
    {
        ImageView iv_menu = findViewById(R.id.iv_menu);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        iv_menu.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.LEFT));

        ImageView iv_qr = findViewById(R.id.iv_qr);
        iv_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QrScanActivity.class);
                startActivity(intent);
            }
        });

        TextView toolbar_name = findViewById(R.id.tvToolbar_name);
        toolbar_name.setText(name);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent;
        switch (item.getItemId()) {
            case R.id.navi_menu_mypage:
                intent = new Intent(getApplicationContext(), UpdateInformActivity.class);
                startActivity(intent);
                break;
            case R.id.navi_menu_map:
                intent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent);
                break;
            case R.id.navi_menu_ranking:
                intent = new Intent(getApplicationContext(), RankingActivity.class);
                startActivity(intent);
                break;
            case R.id.navi_menu_qna:
                intent = new Intent(getApplicationContext(), QnaListActivity.class);
                startActivity(intent);
                break;
            case R.id.navi_menu_license:
                intent = new Intent(getApplicationContext(), LicenseActivity.class);
                startActivity(intent);
                break;
        }
        //close navigation drawer
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    public void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
    }
}