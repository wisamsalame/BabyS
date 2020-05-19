package com.babysitter.babys;

import android.content.Intent;
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

public class registerCu extends AppCompatActivity {
    EditText editText_password_Cu, editText_email_cu, editText_name_Cu;
    Button register;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBarCu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_cu);

        editText_name_Cu = (EditText) findViewById(R.id.editText_name_Cu);
        editText_email_cu = (EditText) findViewById(R.id.editText_email_cu);
        editText_password_Cu = (EditText) findViewById(R.id.editText_password_Cu);

        register = (Button) findViewById(R.id.btn_register_cu);
        progressBarCu = (ProgressBar) findViewById(R.id.progressBarCu);
        firebaseAuth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarCu.setVisibility(View.VISIBLE);
                final String name = editText_name_Cu.getText().toString();
                final String password = editText_password_Cu.getText().toString();
                final String email = editText_email_cu.getText().toString();

                firebaseAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBarCu.setVisibility(View.GONE);
                        if(task.isSuccessful()){

                            Customer customer = new Customer(firebaseAuth.getCurrentUser().getUid() , name , email,password);
                            FirebaseDatabase.getInstance().getReference("Customers").child(firebaseAuth.getCurrentUser().getUid()).setValue(customer).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(registerCu.this,"Regester suucssfully",Toast.LENGTH_LONG).show();
                                }
                            });



                        }
                        else {
                            Toast.makeText(registerCu.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }

                    }
                });
                Intent intent1 = new Intent(registerCu.this, MainCu.class);
                startActivity(intent1);
                finish();
            }


        });


    }
}


