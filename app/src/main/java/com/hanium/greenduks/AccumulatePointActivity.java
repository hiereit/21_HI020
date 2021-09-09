package com.hanium.greenduks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttNewMessageCallback;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos;
import com.amazonaws.regions.Region;
import com.amazonaws.services.iot.AWSIotClient;
import com.amazonaws.services.iot.model.AttachPolicyRequest;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Point;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class AccumulatePointActivity extends AppCompatActivity implements AmplifyInterface {
    Handler valueHandler;
    Handler weightHandler;
    AWSIotMqttManager mqttManager;
    int sumValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accumulatepoint);

        amplifyInit(this);

        AccumulatePointActivity.MqttTask task = new AccumulatePointActivity.MqttTask();
        task.execute();

        TextView tvPoint = findViewById(R.id.tvPoint);
        TextView tvCumulativePoint = findViewById(R.id.tvCumulativePoint);
        TextView tvWeight = findViewById(R.id.tvWeight);
        Button btnConfirmPoint = findViewById(R.id.btnConfirmPoint);
        String userId = AWSMobileClient.getInstance().getUsername();

        weightHandler = new Handler(Looper.getMainLooper(), msg -> {
            double weight = Double.parseDouble(msg.obj.toString());
            int pointValue = (int) (weight * 500);

            Log.d("yyj", "무게: " + weight + ",,,포인트: " + pointValue);

            tvWeight.setText(msg.obj.toString());
            tvPoint.setText(String.valueOf(pointValue));

            String uniqueID = UUID.randomUUID().toString();
            Point point = Point.builder()
                    .userId(userId)
                    .date(String.valueOf(System.currentTimeMillis()))
                    .value(pointValue)
                    .id(uniqueID)
                    .weight(weight)
                    .build();

            Amplify.API.mutate(ModelMutation.create(point),
                    response -> {
                        if (response.hasData()) {
                            Log.d("yyj", "포인트 db insert 이후");
                            Amplify.API.query(
                                    ModelQuery.list(Point.class, Point.USER_ID.contains(userId)),
                                    res -> {
                                        sumValue = 0;
                                        Message turnAlertMsg = new Message();
                                        for (Point p : res.getData()) {
                                            sumValue += p.getValue();
                                        }
                                        Log.d("yyj", "총합 : " + sumValue);
                                        turnAlertMsg.obj = sumValue;
                                        valueHandler.sendMessage(turnAlertMsg);
                                    },
                                    error -> Log.d("yyj", "Query failure", error)
                            );
                        }
                    },
                    error -> Log.e("yyj", "Create failed", error)
            );
            return false;
        });

        valueHandler = new Handler(Looper.getMainLooper(), msg -> {
            tvCumulativePoint.setText(msg.obj.toString());
            Log.d("yyj", "전체 point: " + msg.obj.toString());
            return false;
        });

        btnConfirmPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccumulatePointActivity.this.finish();
            }
        });
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
                        Log.d("yyj", "accum Connection Status: " + status);
                        if (status.equals(AWSIotMqttClientStatus.Connected)) {
                            try {
                                Intent getIntent = getIntent(); //topic 변경 시 확인
                                mqttManager.subscribeToTopic(getIntent.getStringExtra("topic"), AWSIotMqttQos.QOS0 /* Quality of Service */,
                                        new AWSIotMqttNewMessageCallback() {
                                            @Override
                                            public void onMessageArrived(final String topic, final byte[] data) {
                                                try {
                                                    String message = new String(data, "UTF-8");
                                                    if(!message.isEmpty()) {
                                                        Log.d("yyj", "Message received: " + message);
                                                        Message turnAlertMsg = new Message();
                                                        turnAlertMsg.obj = message;
                                                        weightHandler.sendMessage(turnAlertMsg);
                                                    }
                                                } catch (UnsupportedEncodingException e) {
                                                    Log.e("yyj", "Message encoding error: ", e);
                                                }
                                            }
                                        });
                            } catch (Exception e) {
                                Log.e("yyj", "Subscription error: ", e);
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
