package com.babysitter.babys;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class order extends AppCompatActivity {

    Button button_back ;
    String name, password, email ,data,phone_num,active_limitations , ID , city;
    boolean experince,ifHaveCar ;
    String age;
    ListView listViewMain;
    OrderAdapter adapter;
    double salary ,latitude , longitude ;
    final ArrayList<Meeting> meetings= new ArrayList<>();
    ArrayList<Meeting> meetingsTmp= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Intent intent = getIntent();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("meeting");
        listViewMain = (ListView)findViewById(R.id.list_order);


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


        button_back = (Button)findViewById(R.id.button_backOrder);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    Meeting meeting = ds.getValue(Meeting.class);
                    Log.d("meeting" , ds.getValue(Meeting.class).toString());
                    if(meeting.IDBS.equals(ID)){
                        meetings.add(meeting);

                    }
                }
                adapter = new OrderAdapter(order.this, meetings);//babySittersTemp
                listViewMain.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("", "Failed to read value.", error.toException());
            }
        });



        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(order.this , profileBS.class);
                startActivity(intent);
                finish();
            }
        });
    }



    public class OrderAdapter extends ArrayAdapter<Meeting> {
        public OrderAdapter(Context context, ArrayList<Meeting> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final Meeting user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.order_cu, parent, false);
            }
            // Lookup view for data population
            TextView text = (TextView) convertView.findViewById(R.id.textView_text);
            final Button button_acceptOrder = (Button)convertView.findViewById(R.id.button_acceptOrder);
            final Button button_refuseOrder = (Button)convertView.findViewById(R.id.button_refuseOrder);
            // Populate the data into the template view using the data object
            text.setText(getString(R.string.thename) +" "+user.nameCU +" "+getString(R.string.canwork )+" "+user.dateOfMeeting+" "+getString(R.string.from)+" "
                    +user.timeFrom+" "+getString(R.string.to)+" "+user.timeTO+" "+getString(R.string.thank));

            button_acceptOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("MessageOrFeedback");
                    String mId = myRef.push().getKey();
                    MessageOrFeedback messageOrFeedback = new MessageOrFeedback(mId,user.IDCu,user.IDBS,name,"ACCEPTED",user.year ,user.mounth,user.day,user.hour,user.minute );
                    myRef.child(mId).setValue(messageOrFeedback);

                    Log.d("its a firebase", "onClick: "+database.getReference("meeting").child(user.ID));
                    database.getReference("meeting").child(user.ID).setValue(null);
                    Log.d("Postion " , position+"");
                    meetingsTmp = meetings;
                    meetings.clear();
                    for (int i = 0; i < meetingsTmp.size(); i++) {
                        if(i != position){
                            meetings.add(meetingsTmp.get(i));
                        }

                    }
                    //meetings.remove(position);
                   adapter.notifyDataSetChanged();
                   // adapter = new OrderAdapter(order.this, meetings);//babySittersTemp
                    //listViewMain.setAdapter(adapter);

                }
            });
            button_refuseOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("MessageOrFeedback");
                    String mId = myRef.push().getKey();
                    MessageOrFeedback messageOrFeedback = new MessageOrFeedback(mId,user.IDCu,user.IDBS,name,"REFUSE",user.year ,user.mounth,user.day,user.hour,user.minute );

                    myRef.child(mId).setValue(messageOrFeedback);
                    database.getReference("meeting").child(user.ID).setValue(null);
                    Log.d("button_refuseOrder","button_refuseOrder");
                    meetingsTmp = meetings;
                    meetings.clear();
                    for (int i = 0; i < meetingsTmp.size(); i++) {
                        if(i != position){
                            meetings.add(meetingsTmp.get(i));
                        }

                    }
                    adapter.notifyDataSetChanged();
                                }
            });
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {







                }
            });
            // Return the completed view to render on screen
            return convertView;
        }
    }
}
