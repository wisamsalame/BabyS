package com.babysitter.babys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class signinBS extends AppCompatActivity implements View.OnClickListener {

    EditText editText_email_signin , editText_password_signin  ;
    Button btn_signin;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    TextView forgetPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_bs);

        editText_email_signin =(EditText) findViewById(R.id.editText_email_signin);
        editText_password_signin = (EditText) findViewById(R.id.editText_password_signin);
        forgetPass =(TextView)findViewById(R.id.forgetPass);

        btn_signin = (Button) findViewById(R.id.btn_signin);
        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
        firebaseAuth = FirebaseAuth.getInstance();
        btn_signin.setOnClickListener(this);
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signinBS.this , ForgetPassword.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        String username = editText_email_signin.getText().toString();
        String passsword = editText_password_signin.getText().toString();


        firebaseAuth.signInWithEmailAndPassword(username,passsword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Users");

                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    final  String id = firebaseUser.getUid();
                    // Read from the database
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                             Intent intent = new Intent(signinBS.this, profileBS.class);
                                babySitter bS = new babySitter();
                                bS.setName(dataSnapshot.child(id).getValue(babySitter.class).getName());
                                bS.setPassword(dataSnapshot.child(id).getValue(babySitter.class).getPassword());
                                bS.setEmail(dataSnapshot.child(id).getValue(babySitter.class).getEmail());
                                bS.setData(dataSnapshot.child(id).getValue(babySitter.class).getData());
                                bS.setPhone_num(dataSnapshot.child(id).getValue(babySitter.class).getPhone_num());
                                bS.setExperince(dataSnapshot.child(id).getValue(babySitter.class).isExperince());
                                bS.setActive_limitations(dataSnapshot.child(id).getValue(babySitter.class).getActive_limitations());
                                bS.setIfHaveCar(dataSnapshot.child(id).getValue(babySitter.class).isIfHaveCar());
                                bS.setAge(dataSnapshot.child(id).getValue(babySitter.class).getAge());
                                bS.setCity(dataSnapshot.child(id).getValue(babySitter.class).getCity());
                                bS.setSalary(dataSnapshot.child(id).getValue(babySitter.class).getSalary());
                                bS.setLatitude(dataSnapshot.child(id).getValue(babySitter.class).getLatitude());
                                bS.setLongitude(dataSnapshot.child(id).getValue(babySitter.class).getLongitude());
                                bS.setAddress(dataSnapshot.child(id).getValue(babySitter.class).getAddress());
                                bS.setIfhisMale(dataSnapshot.child(id).getValue(babySitter.class).isIfhisMale());
                                bS.setImageURL(dataSnapshot.child(id).getValue(babySitter.class).getImageURL());

                                intent.putExtra("Name", bS.getName());
                                intent.putExtra("Password", bS.getPassword());
                                intent.putExtra("Email", bS.getEmail());
                                intent.putExtra("Data", bS.getData());
                                intent.putExtra("Phone_num", bS.getPhone_num());
                                intent.putExtra("Experince", bS.isExperince());
                                intent.putExtra("Active_limitations", bS.getActive_limitations());
                                intent.putExtra("IfHaveCar", bS.isIfHaveCar());
                                intent.putExtra("Age", bS.getAge());
                                intent.putExtra("ID", id);
                                intent.putExtra("City", bS.getCity());
                                intent.putExtra("Salary", bS.getSalary());
                                intent.putExtra("Latitude", bS.getLatitude());
                                intent.putExtra("Longitude", bS.getLongitude());
                                intent.putExtra("IfhisMale", bS.isIfhisMale());
                                intent.putExtra("Address", bS.getAddress());
                                intent.putExtra("imageurl", bS.getImageURL());
                                //intent.putExtra("Location", bS.getLocation());

                                 startActivity(intent);

                           // }

                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                           // Log.w(TAG, "Failed to read value.", error.toException());
                        }
                    });


                }
                else{
                    Toast.makeText(signinBS.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

