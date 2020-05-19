package com.babysitter.babys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainCu extends AppCompatActivity implements View.OnClickListener {
    Button btn_signin_Cu , btn_register_Cu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cu);

        btn_signin_Cu = (Button) findViewById(R.id.btn_signin_Cu);
        btn_register_Cu = (Button) findViewById(R.id.btn_register_Cu);

        setUpClick();
    }

    private void setUpClick() {

        btn_signin_Cu.setOnClickListener(this);
        btn_register_Cu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signin_Cu:
                Intent intent1 = new Intent(this, signinCu.class);
                startActivity(intent1);
                break;
            case R.id.btn_register_Cu:
                Intent intent = new Intent(this, registerCu.class);
                startActivity(intent);
                break;

        }
    }
}
