package com.babysitter.babys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class profileBS extends AppCompatActivity implements View.OnClickListener {

    String name, password, email ,data,phone_num,active_limitations , ID , city,address,imageurlString;
    boolean experince,ifHaveCar,ifhisMale ;
    String age;
    double salary ,latitude , longitude ;
    Button btn_edit_profileBS , btn_BS_out , button_order_cu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_bs);
        Intent intent = getIntent();
        name = intent.getStringExtra("Name");
        password= intent.getStringExtra("Password");
        email = intent.getStringExtra("Email");
        data= intent.getStringExtra("Data");
        phone_num= intent.getStringExtra("Phone_num");
        active_limitations= intent.getStringExtra("Active_limitations");
        experince = intent.getBooleanExtra("Experince",false);
        ifHaveCar = intent.getBooleanExtra("IfHaveCar",false);
         age = intent.getStringExtra("Age");
        ID = intent.getStringExtra("ID");
        city= intent.getStringExtra("City");
        salary= intent.getDoubleExtra("Salary",0.0);
        latitude= intent.getDoubleExtra("Latitude",0.0);
        longitude= intent.getDoubleExtra("Longitude",0.0);
        address = intent.getStringExtra("Address");
        ifhisMale = intent.getBooleanExtra("IfhisMale", false);
        imageurlString = intent.getStringExtra("imageurl");
        btn_edit_profileBS = (Button)findViewById(R.id.btn_edit_profileBS);
        btn_BS_out = (Button)findViewById(R.id.btn_BS_out);
        button_order_cu=(Button)findViewById(R.id.button_order_cu);
        setUpClick();
//        Log.d("from profile" , name);

    }

    private void setUpClick() {

        btn_edit_profileBS.setOnClickListener(this);
        btn_BS_out.setOnClickListener(this);
        button_order_cu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_edit_profileBS:
                Intent intent1 = new Intent(this, editProfileBS.class);
                intent1.putExtra("Name", name);
                intent1.putExtra("Password",  password);
                intent1.putExtra("Email",  email );
                intent1.putExtra("Data", data);
                intent1.putExtra("Phone_num",phone_num);
                intent1.putExtra("Experince", experince);
                intent1.putExtra("Active_limitations",active_limitations);
                intent1.putExtra("IfHaveCar",ifHaveCar);
                intent1.putExtra("Age", age);
                intent1.putExtra("ID", ID);
                intent1.putExtra("City", city);
                intent1.putExtra("Salary", salary);
                intent1.putExtra("Latitude", latitude);
                intent1.putExtra("Longitude", longitude);
                intent1.putExtra("Address", address);
                intent1.putExtra("IfhisMale",ifhisMale);
                intent1.putExtra("imageurl",imageurlString);
                startActivity(intent1);
                finish();
                break;
            case R.id.btn_BS_out:
                Intent intent = new Intent(this, MainBS.class);
                startActivity(intent);
                finish();
                break;

            case R.id.button_order_cu:
                Intent intent2 = new Intent(this, order.class);
                intent2.putExtra("Name", name);
                intent2.putExtra("Password",  password);
                intent2.putExtra("Email",  email );
                intent2.putExtra("Data", data);
                intent2.putExtra("Phone_num",phone_num);
                intent2.putExtra("Experince", experince);
                intent2.putExtra("Active_limitations",active_limitations);
                intent2.putExtra("IfHaveCar",ifHaveCar);
                intent2.putExtra("Age", age);
                intent2.putExtra("ID", ID);
                intent2.putExtra("City", city);
                intent2.putExtra("Salary", salary);
                intent2.putExtra("Latitude", latitude);
                intent2.putExtra("Longitude", longitude);
                intent2.putExtra("Address", address);
                intent2.putExtra("IfhisMale",ifhisMale);
                intent2.putExtra("imageurl",imageurlString);
                startActivity(intent2);
                finish();
                break;

        }
    }
}
