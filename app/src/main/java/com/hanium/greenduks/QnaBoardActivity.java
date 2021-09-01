package com.hanium.greenduks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Question;
import com.google.android.material.navigation.NavigationView;

public class QnaBoardActivity extends AppCompatActivity implements NavigationInterface, NavigationView.OnNavigationItemSelectedListener{

    ImageView iv_menu;
    DrawerLayout drawerLayout;
    ImageView iv_qr;

    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qnaboard);

        iv_menu = findViewById(R.id.iv_menu);
        drawerLayout = findViewById(R.id.drawer_layout);
        iv_menu.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.LEFT));
        TextView toolbar_name = findViewById(R.id.tvToolbar_name);

        initializeLayout(iv_menu, drawerLayout, toolbar_name, "문의하기");
        setNavigationViewListener();

        iv_qr = findViewById(R.id.iv_qr);
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
            Intent i = new Intent(QnaBoardActivity.this, AuthActivity.class);
            startActivity(i);
            finish();
        });

        //
        context = this.getApplicationContext();

        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(context);

            Log.d("aty", "Initialized Amplify");
        } catch (AmplifyException e) {
            Log.d("aty", "Could not initialize Amplify", e);
        }
        getQuestion("qidTest1");
        //


    }

    //
    private void getQuestion(String id) {
        Amplify.API.query(
                ModelQuery.get(Question.class, id),
                response -> Log.i("aty", ((Question) response.getData()).getConten()),
                error -> Log.e("aty", error.toString(), error)
        );
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = nextIntent(item, this, drawerLayout);
        startActivity(intent);
        finish();
        return true;
    }

    public void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
    }
}
