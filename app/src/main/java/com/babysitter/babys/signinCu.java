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

import java.util.ArrayList;

public class signinCu extends AppCompatActivity {
    EditText editText_email_signin_Cu , editText_password_signin_Cu  ;
    Button btn_signin_Cu;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    TextView forgetPass2;
    ArrayList<MessageOrFeedback> messageOrFeedbacks = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_cu);

        editText_email_signin_Cu =(EditText) findViewById(R.id.editText_email_signin_Cu);
        editText_password_signin_Cu = (EditText) findViewById(R.id.editText_password_signin_Cu);
        forgetPass2 =(TextView)findViewById(R.id.forgetPass2);
forgetPass2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(signinCu.this , ForgetPassword.class);
        startActivity(intent);
    }
});
        btn_signin_Cu = (Button) findViewById(R.id.btn_signin_Cu);
        progressBar = (ProgressBar)findViewById(R.id.progressBarCu2);
        firebaseAuth = FirebaseAuth.getInstance();
        btn_signin_Cu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String username = editText_email_signin_Cu.getText().toString();
                String passsword = editText_password_signin_Cu.getText().toString();

                firebaseAuth.signInWithEmailAndPassword(username,passsword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("Customers");

                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            final  String id = firebaseUser.getUid();
                            // Read from the database
                            myRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    // This method is called once with the initial value and again
                                    // whenever data at this location is updated.
                                    //String value = dataSnapshot.getValue(String.class);
                                    // for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    Intent intent = new Intent(signinCu.this, search_customer.class);


                                    Customer customer = new Customer();


                                    customer.setEmail(dataSnapshot.child(id).getValue(Customer.class).getEmail());
                                    customer.setName(dataSnapshot.child(id).getValue(Customer.class).getName());
                                    customer.setPassword(dataSnapshot.child(id).getValue(Customer.class).getPassword());
                                    customer.setID(id);

                                    /**/

                                    intent.putExtra("Name", customer.getName());
                                    intent.putExtra("Password", customer.getPassword());
                                    intent.putExtra("Email", customer.getEmail());
                                    intent.putExtra("ID", customer.getID());

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
                            Toast.makeText(signinCu.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }


        });
    }
}
