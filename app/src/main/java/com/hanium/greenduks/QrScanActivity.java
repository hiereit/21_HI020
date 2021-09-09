package com.hanium.greenduks;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttNewMessageCallback;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos;
import com.amazonaws.regions.Region;
import com.amazonaws.services.iot.AWSIotClient;
import com.amazonaws.services.iot.model.AttachPolicyRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.UnsupportedEncodingException;

public class QrScanActivity extends AppCompatActivity {
    private IntentIntegrator qrScan;
    AWSIotMqttManager mqttManager;
    String qrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(false);
        qrScan.setPrompt("QR코드를 스캔하세요");
        qrScan.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() != null) {
                qrCode = result.getContents();
                QrScanActivity.MqttTask task = new QrScanActivity.MqttTask();
                task.execute();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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
                        Log.d("yyj", "qr Connection Status: " + status);
                        if (status.equals(AWSIotMqttClientStatus.Connected)) {
                            try {
                                String topic = "android/" + qrCode + "/opendoor";
                                mqttManager.publishString("{ \"member\" : \"user\", \"open\" : 1 }", topic, AWSIotMqttQos.QOS0);
                                Intent intent = new Intent(QrScanActivity.this, SeparateActivity.class);
                                intent.putExtra("topic", topic);
                                startActivity(intent);
                                finish();
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