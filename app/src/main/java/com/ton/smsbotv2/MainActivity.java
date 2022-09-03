// this application for publication
// need to add webhook on main activity

package com.ton.smsbotv2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST = 0;
    Button btnEnter;
    EditText etNumber, etWebhook;
    SharePrefHelper sharePref = new SharePrefHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appPermissionRequest();
        init();
        String phoneNo = sharePref.getFromSharePref(MainActivity.this, "PhoneNo");
        String webHook = sharePref.getFromSharePref(MainActivity.this, "WebHook");
        if (phoneNo != null || webHook != null) {
            etNumber.setText(phoneNo);
            etWebhook.setText(webHook);
        }

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNo = etNumber.getText().toString();
                String webHook = etWebhook.getText().toString();
                if (phoneNo.equals("") || webHook.equals("")) {
                    Toast.makeText(MainActivity.this, getString(R.string.wrong_setup), Toast.LENGTH_SHORT).show();
                } else {
                    sharePref.saveToSharePref(MainActivity.this, "PhoneNo", phoneNo);
                    sharePref.saveToSharePref(MainActivity.this, "WebHook", webHook);
                    Toast.makeText(MainActivity.this, getString(R.string.save_setup), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {
        btnEnter = findViewById(R.id.btnEnter);
        etNumber = findViewById(R.id.etNumber);
        etWebhook = findViewById(R.id.etWebhook);
    }

    private void appPermissionRequest() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, MY_PERMISSIONS_REQUEST);
    }
}