package com.babysitter.babys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainBS extends AppCompatActivity implements View.OnClickListener {
    Button btn_signin_BS , btn_register_BS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bs);

        btn_signin_BS = (Button) findViewById(R.id.btn_signin_BS);
        btn_register_BS = (Button) findViewById(R.id.btn_register_BS);

        setUpClick();
    }

    private void setUpClick() {

        btn_signin_BS.setOnClickListener(this);
        btn_register_BS.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signin_BS:
                Intent intent1 = new Intent(this, signinBS.class);
                startActivity(intent1);
                break;
            case R.id.btn_register_BS:
                Intent intent = new Intent(this, registerBS.class);
                startActivity(intent);
                break;

        }
    }
}
