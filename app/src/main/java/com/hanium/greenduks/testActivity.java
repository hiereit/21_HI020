package com.hanium.greenduks;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.iot.AWSIot;
import com.amazonaws.services.iot.AWSIotClient;
import com.amazonaws.services.iot.model.AttachPolicyRequest;
import com.amazonaws.services.iot.model.CreatePolicyRequest;
import com.amazonaws.services.iot.model.DescribeEndpointRequest;
import com.amazonaws.services.iot.model.DescribeEndpointResult;
import com.amazonaws.services.iot.model.Policy;

import androidx.appcompat.app.AppCompatActivity;

public class testActivity extends AppCompatActivity {
    AWSIotMqttManager mqttManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        Button btn = findViewById(R.id.testBtn);
        CognitoCachingCredentialsProvider credentialsProvider;

        MqttTask task = new MqttTask();
        task.execute();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mqttManager.publishString("Hello to all subscribers!", "myTopic", AWSIotMqttQos.QOS0);
                } catch (Exception e) {
                    Log.e("yyj", "Publish error: ", e);
                }
            }
        });
    }

    public class MqttTask extends AsyncTask<Void, Void, CognitoCachingCredentialsProvider> {

        @Override
        protected CognitoCachingCredentialsProvider doInBackground(Void... voids) {
            CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                    getApplicationContext(),
                    "ap-northeast-2:0ee81826-95e8-4800-a9a7-eeaec5cbdc11", // 자격 증명 풀 ID
                    Regions.AP_NORTHEAST_2 // 리전
            );
            Log.d("yyj", "provider,,,," + credentialsProvider.getIdentityId());
            return credentialsProvider;
        }

        @Override
        protected void onPostExecute(CognitoCachingCredentialsProvider cognitoCachingCredentialsProvider) {
            super.onPostExecute(cognitoCachingCredentialsProvider);

            // Initialize the AWSIotMqttManager with the configuration
            mqttManager = new AWSIotMqttManager(
                    "box1",
                    "a2qfvep4a6xjvp-ats.iot.ap-northeast-2.amazonaws.com");

            AttachPolicyRequest attachPolicyReq = new AttachPolicyRequest();
            attachPolicyReq.setPolicyName("arn:aws:iot:ap-northeast-2:471413150019:policy/iot_policy"); // name of your IOT AWS policy
            attachPolicyReq.setTarget(cognitoCachingCredentialsProvider.getIdentityId());
            AWSIotClient mIotAndroidClient = new AWSIotClient(AWSMobileClient.getInstance());
            mIotAndroidClient.setRegion(Region.getRegion("ap-northeast-2")); // name of your IoT Region such as "us-east-1"
            mIotAndroidClient.attachPolicy(attachPolicyReq);

            try {
                mqttManager.connect(AWSMobileClient.getInstance(), new AWSIotMqttClientStatusCallback() {
                    @Override
                    public void onStatusChanged(final AWSIotMqttClientStatus status, final Throwable throwable) {
                        Log.d("yyj", "Connection Status: " + String.valueOf(status));
                    }
                });
            } catch (final Exception e) {
                Log.e("yyj", "Connection error: ", e);
            }

        }
    }
}
