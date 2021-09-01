package com.hanium.greenduks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

//import com.amazonaws.mobile.client.AWSMobileClient;
//import com.amplifyframework.AmplifyException;
//import com.amplifyframework.api.graphql.model.ModelMutation;
//import com.amplifyframework.core.Amplify;
//import com.amplifyframework.datastore.AWSDataStorePlugin;
//import com.amplifyframework.datastore.generated.model.Question;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Question;
import com.google.android.material.navigation.NavigationView;


public class QnaRegisterActivity extends AppCompatActivity implements NavigationInterface, NavigationView.OnNavigationItemSelectedListener{


    ImageView iv_menu;
    DrawerLayout drawerLayout;
    ImageView iv_qr;

    Button registerBtn;

    //
    private static final String TAG = "yyj";
    Context context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qnaregister);

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
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        // xml 파일에서 넣어놨던 header 선언
        View header = navigationView.getHeaderView(0);
        // header에 있는 리소스 가져오기
        ImageView logoutBtn = (ImageView) header.findViewById(R.id.ivNavi_logout);

        logoutBtn.setOnClickListener(v -> {
            AWSMobileClient.getInstance().signOut();
            Intent i = new Intent(QnaRegisterActivity.this, AuthActivity.class);
            startActivity(i);
            finish();
        });

        //
            context = this.getApplicationContext();

            try {
                Amplify.addPlugin(new AWSCognitoAuthPlugin());
                Amplify.addPlugin(new AWSApiPlugin());
                Amplify.configure(context);

                Log.d(TAG, "Initialized Amplify");
            } catch (AmplifyException e) {
                Log.d(TAG, "Could not initialize Amplify", e);
            }

            String userId = AWSMobileClient.getInstance().getUsername();
            Question item = Question.builder()
                    .title("testTitle3")
                    .content("testContent3")
                    .date(String.valueOf(System.currentTimeMillis()))
                    .userId(userId)
                    .id("qtest3")
                    .build();

            Amplify.API.mutate(ModelMutation.create(item),
                    response -> Log.d(TAG, "Todo with id: " + response.getData().getId()),
                    error -> Log.d(TAG, "Create failed", error)
            );

        registerBtn = (Button)findViewById(R.id.btnQnaBoard_register);
        registerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), QnaListActivity.class);
            startActivity(intent);
            finish();
        });


    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d("yyj", "item: " + item);
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
