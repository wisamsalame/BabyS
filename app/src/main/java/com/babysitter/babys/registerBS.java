package com.babysitter.babys;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class registerBS extends AppCompatActivity implements View.OnClickListener {

    EditText editText_password_reg , editText_email  , editText_name_BS;
    Button register;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_bs);

        editText_name_BS =(EditText) findViewById(R.id.editText_name_BS);
        editText_email = (EditText) findViewById(R.id.editText_email);
        editText_password_reg = (EditText) findViewById(R.id.editText_password_reg);

        register = (Button) findViewById(R.id.btn_register);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();
        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                progressBar.setVisibility(View.VISIBLE);
                final String name = editText_name_BS.getText().toString();
                final String password = editText_password_reg.getText().toString();
                final String email = editText_email.getText().toString();
                final Location location = new Location("location");
                firebaseAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            babySitter babySitter = new babySitter(name , password , email , "your data ","your phone num" , false , "have any limated" ,false , "00/00/0000" ,"please click here to know your city" ,0.0 , 0.0 , 0.0,true,"address", null);
                            FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getCurrentUser().getUid()).setValue(babySitter).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(registerBS.this,"Regester suucssfully",Toast.LENGTH_LONG).show();
                                }
                            });



                        }
                        else {
                            Toast.makeText(registerBS.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });

                Intent intent1 = new Intent(this, MainBS.class);
                startActivity(intent1);
                finish();
                break;
        }
    }
}
