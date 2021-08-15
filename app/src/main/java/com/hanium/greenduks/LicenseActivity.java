package com.hanium.greenduks;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

public class LicenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);

        Log.d("LicenseActivity", (MainActivity)MainActivity.mContext + "!!!");
        ((MainActivity)MainActivity.mContext).initializeLayout("LICENSE");
        ((MainActivity)MainActivity.mContext).setNavigationViewListener();
    }
}