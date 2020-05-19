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
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class feedback extends AppCompatActivity {

    String IDFORBS;
    FirebaseDatabase database;
    feedbackReport feedbackReport;
    DatabaseReference myRef;
    ArrayList<feedbackReport> feedbackReports= new ArrayList<>();
    ListView listViewForFeedBack;
    feedBackReportAdapter feedBackReportAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
         database = FirebaseDatabase.getInstance();
        listViewForFeedBack =(ListView)findViewById(R.id.listViewForFeedBack);
         feedbackReport = new feedbackReport();
         myRef = database.getReference("feedbacks");
        Intent intent = getIntent();
        IDFORBS = intent.getStringExtra("ID");
        Log.d("IDIDFORBS",IDFORBS);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    feedbackReport report = ds.getValue(feedbackReport.class);
                    final String id = ds.getKey();

                    Log.d("id",ds.getValue()+"");
                    if(IDFORBS.equals(report.IDBS)){
                        feedbackReports.add(report);
                    }


                }
                feedBackReportAdapter = new feedBackReportAdapter(feedback.this,feedbackReports);
                listViewForFeedBack.setAdapter(feedBackReportAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("", "Failed to read value.", error.toException());
            }
        });



    }



    public class feedBackReportAdapter extends ArrayAdapter<feedbackReport> {
        public feedBackReportAdapter(Context context, ArrayList<feedbackReport> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            feedbackReport report = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.feedback_icon, parent, false);
            }
            // Lookup view for data population
            TextView name = (TextView) convertView.findViewById(R.id.textView6);
            TextView review = (TextView) convertView.findViewById(R.id.textView7);
            RatingBar ratingBar = (RatingBar)convertView.findViewById(R.id.ratingBar2);

Log.d("report.stars",report.stars+"");
            // Populate the data into the template view using the data object
            name.setText(report.NameCS);
            review.setText(report.review);
            ratingBar.setRating((float) report.stars);




            // Return the completed view to render on screen
            return convertView;
        }
    }
}
