package com.babysitter.babys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_customer , btn_main_BS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_customer = (Button) findViewById(R.id.btn_customer);
        btn_main_BS = (Button) findViewById(R.id.btn_main_BS);

        setUpClick();
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
}
