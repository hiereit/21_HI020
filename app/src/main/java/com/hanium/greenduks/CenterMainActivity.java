package com.hanium.greenduks;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CenterMainActivity extends AppCompatActivity implements NavigationInterface {

    ImageView iv_menu;
    DrawerLayout drawerLayout;

    RecyclerView cmRecyclerView;
    CenterMainAdapter cmRecyclerAdapter;
    ArrayList<CenterMain> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_main);

        iv_menu = findViewById(R.id.iv_menu);
        drawerLayout = findViewById(R.id.drawer_layout);
        iv_menu.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.LEFT));
        TextView toolbar_name = findViewById(R.id.tvToolbar_name);
        initializeLayout(iv_menu, drawerLayout, toolbar_name, "수거업체용");

        ImageView iv_qr = findViewById(R.id.iv_qr);
        iv_qr.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), QrScanActivity.class);
            startActivity(intent);
        });

        cmRecyclerView = (RecyclerView)findViewById(R.id.rvCenterMain);

        /* initiate adapter */
        cmRecyclerAdapter = new CenterMainAdapter();

        /* initiate recyclerview */
        cmRecyclerView.setAdapter(cmRecyclerAdapter);
        cmRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        /* adapt data */
        list = new ArrayList<CenterMain>();
        for(int i = 1; i <= 10; i++){
            if(i % 2 == 0)
                list.add(new CenterMain(70, "서울시 성북구 화랑로", i));
            else
                list.add(new CenterMain(50, "서울시 영등포구 여의도로", i));
        }
        cmRecyclerAdapter.setCenterMainList(list);
    }
}
