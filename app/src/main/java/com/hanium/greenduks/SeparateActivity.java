package com.hanium.greenduks;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos;
import com.amazonaws.regions.Region;
import com.amazonaws.services.iot.AWSIotClient;
import com.amazonaws.services.iot.model.AttachPolicyRequest;
import com.google.android.material.navigation.NavigationView;

public class SeparateActivity extends AppCompatActivity {
    AWSIotMqttManager mqttManager;
    boolean backPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_separate);
        Button btnCompSeparate = findViewById(R.id.btnCompSeparate);

        btnCompSeparate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SeparateActivity.MqttTask task = new SeparateActivity.MqttTask();
                task.execute();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backPressed = true;
        SeparateActivity.MqttTask task = new SeparateActivity.MqttTask();
        task.execute();
    }

    public class MqttTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            mqttManager = new AWSIotMqttManager(
                    "21hi02905c0a1d_app_client",
                    "a2qfvep4a6xjvp-ats.iot.ap-northeast-2.amazonaws.com");
            mqttManager.setAutoReconnect(false);

            AttachPolicyRequest attachPolicyReq = new AttachPolicyRequest();
            attachPolicyReq.setPolicyName("connect_mqtt");
            attachPolicyReq.setTarget(AWSMobileClient.getInstance().getIdentityId());
            AWSIotClient mIotAndroidClient = new AWSIotClient(AWSMobileClient.getInstance());
            mIotAndroidClient.setRegion(Region.getRegion("ap-northeast-2"));
            mIotAndroidClient.attachPolicy(attachPolicyReq);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                mqttManager.connect(AWSMobileClient.getInstance(), new AWSIotMqttClientStatusCallback() {
                    @Override
                    public void onStatusChanged(final AWSIotMqttClientStatus status, final Throwable throwable) {
                        Log.d("yyj", "sep Connection Status: " + status);
                        if (status.equals(AWSIotMqttClientStatus.Connected)) {
                            try {
                                Intent getIntent = getIntent();
                                mqttManager.publishString("{ \"member\" : \"user\", \"open\" : 0 }", getIntent.getStringExtra("topic"), AWSIotMqttQos.QOS0);
                                if (!backPressed) {
                                    Intent intent = new Intent(SeparateActivity.this, AccumulatePointActivity.class);
                                    intent.putExtra("topic", getIntent.getStringExtra("topic"));
                                    startActivity(intent);
                                }
                                SeparateActivity.this.finish();
                            } catch (Exception e) {
                                Log.e("yyj", "Publish error: ", e);
                            }
                        }
                    }
                });
            } catch (final Exception e) {
                Log.e("yyj", "Connection error: ", e);
            }
        }
    }
}
