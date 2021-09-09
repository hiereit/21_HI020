package com.hanium.greenduks;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public interface NavigationInterface {
    public default void initializeLayout(ImageView iv_menu, DrawerLayout drawerLayout, TextView toolbar_name, String name)
    {
        iv_menu.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.LEFT));
        toolbar_name.setText(name);
    }

    public default Intent nextIntent(@NonNull @NotNull MenuItem item, Context context, DrawerLayout drawerLayout) {
        // Handle navigation view item clicks here.
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.navi_menu_mypage:
                intent = new Intent(context, UpdateInformActivity.class);
                break;
            case R.id.navi_menu_map:
                intent = new Intent(context, MapActivity.class);
                break;
            case R.id.navi_menu_return:
                intent = new Intent(context, PointReturnActivity.class);
                break;
            case R.id.navi_menu_register_question:
                intent = new Intent(context, QnaRegisterActivity.class);
                break;
            case R.id.navi_menu_qna:
                intent = new Intent(context, QnaListActivity.class);
                break;
            case R.id.navi_menu_license:
                intent = new Intent(context, LicenseActivity.class);
                break;
        }
        //close navigation drawer
        drawerLayout.closeDrawer(GravityCompat.START);
        return intent;
    }
}
