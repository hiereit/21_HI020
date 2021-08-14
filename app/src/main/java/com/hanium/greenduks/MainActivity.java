package com.hanium.greenduks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView iv_menu;
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_menu = findViewById(R.id.iv_menu);
        drawerLayout = findViewById(R.id.drawer_layout);

        iv_menu.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.LEFT));
    }
}