package com.hanium.greenduks;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserState;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Box;

import java.util.ArrayList;

public class CenterMainActivity extends AppCompatActivity implements AmplifyInterface {

    private static final String TAG = "CenterMainActivity";

    ImageView logoutBtn;
    ImageView iv_qr;

    RecyclerView cmRecyclerView;
    CenterMainAdapter cmRecyclerAdapter;
    ArrayList<CenterMain> centerList;

    String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_main);

        amplifyInit(this);
        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
            @Override
            public void onResult(UserStateDetails userStateDetails) {
                Intent i;
                if (userStateDetails.getUserState().equals(UserState.SIGNED_IN)) {
                    return;
                }
                switch (userStateDetails.getUserState()){
                    case SIGNED_OUT:
                        i = new Intent(CenterMainActivity.this, LoginActivity.class);
                        break;
                    default:
                        AWSMobileClient.getInstance().signOut();
                        i = new Intent(CenterMainActivity.this, LoginActivity.class);
                        break;
                }
                startActivity(i);
                finish();
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, e.toString());
            }
        });

        logoutBtn = findViewById(R.id.ivCenter_logout);
        logoutBtn.setOnClickListener(v -> {
            AWSMobileClient.getInstance().signOut();
            Intent i = new Intent(CenterMainActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        });

        iv_qr = findViewById(R.id.ivCenter_qr);
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
        centerList = new ArrayList<CenterMain>();

        userId = AWSMobileClient.getInstance().getUsername();
        getBoxList(userId);
        Log.d(TAG, userId);

        for(int i = 1; i <= 10; i++){
            if(i % 2 == 0)
                centerList.add(new CenterMain(3.4, "서울시 성북구 화랑로 테스트동", String.valueOf(i)));
            else
                centerList.add(new CenterMain(2.1, "서울시 영등포구 여의도로 테스트동", String.valueOf(i)));
        }
        cmRecyclerAdapter.setCenterMainList(centerList);
    }

    public void getBoxList(String userId){
//        Amplify.API.query(
//                ModelQuery.list(Box.class, Box.CENTER_ID.contains(userId)),
//                response -> {
//                    for (Box todo : response.getData()) {
//                        Log.i(TAG, todo.getWeight().toString());
//                    }
//                },
//                error -> Log.e(TAG, "Query failure", error)
//        );

    }
}
