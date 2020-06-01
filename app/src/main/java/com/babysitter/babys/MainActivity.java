package com.babysitter.babys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_customer , btn_main_BS;
    private static final int GPS_PERMISSION_CODE = 10;
    private static final int STORAGE_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_customer = (Button) findViewById(R.id.btn_customer);
        btn_main_BS = (Button) findViewById(R.id.btn_main_BS);

        primission();
        setUpClick();
    }

    private void primission() {
        checkPermission(
                Manifest.permission.ACCESS_FINE_LOCATION,
                GPS_PERMISSION_CODE);

        checkPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                GPS_PERMISSION_CODE);


    }


    private void setUpClick() {

        btn_main_BS.setOnClickListener(this);
        btn_customer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_customer:
                Intent intent1 = new Intent(this, MainCu.class);
                startActivity(intent1);
                break;
            case R.id.btn_main_BS:
                Intent intent = new Intent(this, MainBS.class);
                startActivity(intent);
                break;

        }
    }

    // Function to check and request permission.
    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] { permission },
                    requestCode);
        }
        else {

        }
    }

    // This function is called when the user accepts or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when the user is prompt for permission.

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        if (requestCode == GPS_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this,
                        "GPS Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {

            }
        }
        else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {

            }
        }
    }


}
