package com.babysitter.babys;
/*
    dialog send data from ather
*/
import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.ContentValues.TAG;
/*





clcik adapter
check sms 




 */
public class search_customer extends AppCompatActivity implements View.OnClickListener, OnItemClickListener, LocationListener {
    Button btn_Serach ,button_tmp;
    private FusedLocationProviderClient client;
    babySittersAdapter adapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");
    ProgressBar progressBar;
    String age ;
    String name, password, email , ID;
    Location baseLocation;
    Calendar  mainCalender = Calendar.getInstance();;


    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private FirebaseListAdapter<babySitter> mAdapter;

    final ArrayList<babySitter> babySitters= new ArrayList<>();

    final ArrayList<String> babySittersIDs= new ArrayList<>();
    final ArrayList<String> babySittersCitis= new ArrayList<>();
    ListView listViewMain;
    FirebaseListAdapter<String> stringFirebaseListAdapter;
    ArrayAdapter<babySitter> arrayAdapter;
    ArrayList<MessageOrFeedback>messageOrFeedbacks = new ArrayList<>();
    ArrayList<feedbackfromCu>messageOrFeedbacksCu = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_customer);

        Intent intent = getIntent();


        name = intent.getStringExtra("Name");
        password= intent.getStringExtra("Password");
        email = intent.getStringExtra("Email");
        ID= intent.getStringExtra("ID");


        listViewMain = (ListView)findViewById(R.id.list);
        final Geocoder[] geocoder = new Geocoder[1];
        btn_Serach = (Button) findViewById(R.id.btn_Search);
        //button_tmp = (Button)findViewById(R.id.button_tmp);
        progressBar = (ProgressBar)findViewById(R.id.progressBar4);


       firstReferance();

        for (int i = 0 ; i < messageOrFeedbacks.size() ; i++){
            //messageor(messageOrFeedbacks.get(i));
            FirebaseDatabase database = FirebaseDatabase.getInstance();

        }

       //
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                progressBar.setVisibility(View.VISIBLE);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    babySitter user = ds.getValue(babySitter.class);
                    final  String id = ds.getKey();

                    age = getAge(user.getAge());

                    babySitters.add(user);
                    babySittersIDs.add(id);

                    Geocoder gcd = new Geocoder(search_customer.this, Locale.getDefault());
                    List<Address> addresses = null;
                    try {
                        addresses = gcd.getFromLocation(user.getLatitude(), user.getLongitude(), 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (addresses.size() > 0) {
                        babySittersCitis.add(addresses.get(0).getLocality());

                    }
                    else {
                        babySittersCitis.add("problem");
                    }




                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }

        });






        btn_Serach.setOnClickListener(this);
        listViewMain.setOnItemClickListener(this);
    }

    private void secondReferance() {
        DatabaseReference myRef2 = database.getReference("feedbackfromCu");
        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.VISIBLE);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    feedbackfromCu feedbackfromCu = ds.getValue(feedbackfromCu.class);
                    if(feedbackfromCu.getIDCU().equals(ID)){
                        checkTheTimeWithCurrent(feedbackfromCu);
                    }

                }

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void firstReferance() {
        DatabaseReference myRef1 = database.getReference("MessageOrFeedback");
        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.VISIBLE);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    MessageOrFeedback messageOrFeedback = ds.getValue(MessageOrFeedback.class);
                    if(messageOrFeedback.getIDCU().equals(ID)){

                        messageor(messageOrFeedback);

                    }

                }

                progressBar.setVisibility(View.GONE);
                secondReferance();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void checkTheTimeWithCurrent(feedbackfromCu feedbackfromCu) {
//        Log.d("","")
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.set(Calendar.HOUR_OF_DAY,
//Subtract 2 hours
 currentCalendar.get(Calendar.HOUR_OF_DAY)+3);
        currentCalendar.set(Calendar.MONTH,
//Subtract 2 hours
                currentCalendar.get(Calendar.MONTH)+1);

        Calendar calendar = Calendar.getInstance();
        calendar.set( Calendar.MONTH, feedbackfromCu.mounth);
        calendar.set( Calendar.YEAR, feedbackfromCu.year);
        calendar.set( Calendar.DAY_OF_MONTH, feedbackfromCu.day);
        calendar.set( Calendar.HOUR_OF_DAY, feedbackfromCu.hour);
        calendar.set( Calendar.MINUTE, feedbackfromCu.minute);
        Log.d("currentCalendar" , currentCalendar.toString());
        Log.d("calendar" , calendar.toString());
        if(currentCalendar.after(calendar)){
            Log.d("before" , "before");
           // database.getReference("feedbackfromCu").child(feedbackfromCu.IDFB).setValue(null);
            theSecondDialog(feedbackfromCu.IDBS);
            database.getReference("feedbackfromCu").child(feedbackfromCu.IDFB).setValue(null);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            //database.getReference("feedbackfromCu").child(feedbackfromCu.IDFB).setValue(null);

        }
        Log.d("AFTER","");
    }


    @Override
    public void onClick(View v) {
        final ArrayList<babySitter> babySittersTemp= new ArrayList<>();
        Log.d("stage","0");
        if (ActivityCompat.checkSelfPermission(search_customer.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(search_customer.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission.
            return;
        }

        client = new FusedLocationProviderClient(this);
        client.getLastLocation().addOnSuccessListener(search_customer.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {


                if(location!= null){
                    baseLocation = location;
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    Log.d("stage","2");
                    Geocoder gcd = new Geocoder(search_customer.this, Locale.ENGLISH);

                    List<Address> addresses = null;
                    try {

                        addresses = gcd.getFromLocation(latitude, longitude, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("stage","3");
                    if (addresses.size() > 0) {
                        //babySittersCitis.add(addresses.get(0).getLocality());
                        String city = addresses.get(0).getLocality();
                        Log.d("city",city);
                        for(babySitter babySitter : babySitters){
                            if(babySitter.getCity()!=null) {
                                if (babySitter.getCity().equals(city)) {
                                    Log.d("city", babySitter.getName());
                                    babySittersTemp.add(babySitter);
                                }
                            }
                        }
                        //textView_location.setText(addresses.get(0).getLocality());
                    }
                    else {
                        babySittersTemp.addAll(babySitters);
                    }


                    ;
                }
                adapter = new babySittersAdapter(search_customer.this, babySittersTemp);//babySittersTemp
                  listViewMain.setAdapter(adapter);
            }

        });


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    private String getAge(String dobString){

        Date date = null;
        //Log.d("dobString : " , dobString+"");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //Log.d("sdf : " , sdf+"");
        try {
            date = sdf.parse(dobString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(date == null) return null;

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(date);

        int year = dob.get(Calendar.YEAR);
        int month = dob.get(Calendar.MONTH);
        int day = dob.get(Calendar.DAY_OF_MONTH);

        dob.set(year, month+1, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }
        if(age < 0){
            return "unknow";
        }
        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();
        //Log.d("ageS " , ageS+"");
        return ageS;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }



    public class babySittersAdapter extends ArrayAdapter<babySitter> {
        public babySittersAdapter(Context context, ArrayList<babySitter> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final babySitter user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.the_bs_icon, parent, false);
            }
            // Lookup view for data population
            TextView name = (TextView) convertView.findViewById(R.id.textTitle);
            TextView ageText = (TextView) convertView.findViewById(R.id.textAge);
            TextView city = (TextView) convertView.findViewById(R.id.textCity);
            TextView textSalary = (TextView)convertView.findViewById(R.id.textSalary);
            ImageView imageView = (ImageView)convertView.findViewById(R.id.theImage);
            final Button button_details = (Button)convertView.findViewById(R.id.button_details);
            final Button button_order = (Button)convertView.findViewById(R.id.button_order);
            final Button button_feedback = (Button)convertView.findViewById(R.id.button_feedback);
            //final Button button_tmp = (Button)convertView.findViewById(R.id.button_tmp);
            // Populate the data into the template view using the data object
            if(user.getImageURL()!=null){
                DownloadImageTask downloadImageTask = new DownloadImageTask(imageView);
                downloadImageTask.execute(user.getImageURL());

            }

            name.setText(user.getName());
            age = getAge(user.getAge());
            ageText.setText(getAge(user.getAge()));
            city.setText(babySittersCitis.get(position));
            textSalary.setText(user.getSalary()+" for hour");
            button_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(search_customer.this, profileBSCustomer.class) ;
                    intent.putExtra("Name", user.getName());
                    intent.putExtra("Email",  user.getEmail() );
                    intent.putExtra("Data", user.getData());
                    intent.putExtra("Phone_num",user.getPhone_num());
                    intent.putExtra("Experince", user.isExperince());
                    intent.putExtra("Active_limitations",user.getActive_limitations());
                    intent.putExtra("IfHaveCar",user.isIfHaveCar());
                    intent.putExtra("Age", getAge(user.getAge()));
                    intent.putExtra("City", babySittersCitis.get(position));
                    intent.putExtra("Salary", user.getSalary());
                    intent.putExtra("Address", user.getAddress());
                    intent.putExtra("IfhisMale",user.isIfhisMale());
                    intent.putExtra("imageURL",user.getImageURL());


                    startActivity(intent);
                }
            });
            button_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    theDialog(babySitters.get(position).getPhone_num(),babySitters.get(position).getEmail(),babySittersIDs.get(position));
                }
            });

            button_feedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent2 = new Intent(search_customer.this,feedback.class);
                    intent2.putExtra("ID" ,babySittersIDs.get(position) );
                    startActivity(intent2);
                }
            });
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    button_details.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("det","detale");
                        }
                    });


                }
            });
            // Return the completed view to render on screen
            return convertView;
        }
    }


    private void theDialog(final String phone_num, final String email, final String IDBS) {


        final View viewOfDialog = getLayoutInflater().inflate(R.layout.dialog,null);

        final String[] date = new String[1];
        final String[] timeFrom = new String[1];
        final String[] timeTo = new String[1];

        final String massage = "Hi after impressing your profile, I would love to hear more details and if you are available on the requested date.\n" +
                "Thanks\n";/////The message the parent sent to the babysitter whether it was Emil or Watsup

        final TextView mDisplayDate = (TextView) viewOfDialog.findViewById(R.id.tvDate);
        final TextView textViewFrom = (TextView) viewOfDialog.findViewById(R.id.textViewfrom);
        final TextView textViewto = (TextView) viewOfDialog.findViewById(R.id.textViewto);
        final Calendar[] calendar = new Calendar[1];
        final int[] currentHour = new int[1];
        final int[] currentMinute = new int[1];
        final String[] amPm = new String[1];
        final TimePickerDialog[] timePickerDialog = new TimePickerDialog[1];

        final Calendar[] calendar1 = new Calendar[1];
        final int[] currentHour1 = new int[1];
        final int[] currentMinute1 = new int[1];
        final String[] amPm1 = new String[1];
        final TimePickerDialog[] timePickerDialog1 = new TimePickerDialog[1];

        final Button btn_dialog_Email = (Button)viewOfDialog.findViewById(R.id.btn_dialog_Email);
        final Button btn_thisapp = (Button)viewOfDialog.findViewById(R.id.btn_thisapp);
        final Button btn_dialog_SMS = (Button)viewOfDialog.findViewById(R.id.btn_dialog_SMS);
        final Button btn_dialog_Whatsapp = (Button)viewOfDialog.findViewById(R.id.btn_dialog_Whatsapp);
        final Button btn_dialog_Call = (Button)viewOfDialog.findViewById(R.id.btn_dialog_Call);
        final AlertDialog.Builder builder = new AlertDialog.Builder(search_customer.this ,
                android.R.style.Theme_Translucent_NoTitleBar);
        builder.setView(viewOfDialog);
        final Calendar[] calendarFrom = new Calendar[1];
        final Calendar[] calendarto= new Calendar[1];

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() { // define the 'Cancel' button
            public void onClick(DialogInterface dialog, int which) {
                //Either of the following two lines should work.
                dialog.cancel();
                //dialog.dismiss();
            }
        });
        textViewto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar1[0] = Calendar.getInstance();
                currentHour1[0] = calendar1[0].get(Calendar.HOUR_OF_DAY);
                currentMinute1[0] = calendar1[0].get(Calendar.MINUTE);
                final boolean[] temp = {false};
                timePickerDialog1[0] = new TimePickerDialog(search_customer.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm1[0] = "PM";
                        } else {
                            amPm1[0] = "AM";
                        }

                        calendarto[0] = Calendar.getInstance();
                        calendarto[0].set( Calendar.HOUR_OF_DAY, hourOfDay);
                        calendarto[0].set( Calendar.MINUTE, minutes);
                        if(calendarFrom[0].before(calendarto[0])){
                            timeTo[0] = String.format("%02d:%02d", hourOfDay, minutes) + amPm1[0];
                            textViewto.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm1[0]);
                        }
                        else{
                            Toast.makeText(search_customer.this,"must from time be before ",Toast.LENGTH_LONG).show();
                        }
                    }
                }, currentHour1[0], currentMinute1[0], false);

                if(calendarFrom[0] != null){
                             timePickerDialog1[0].show();

                }
                else{
                    Toast.makeText(search_customer.this,"enter form time",Toast.LENGTH_LONG).show();
                }


            }
        });
        textViewFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar[0] = Calendar.getInstance();
                currentHour[0] = calendar[0].get(Calendar.HOUR_OF_DAY);
                currentMinute[0] = calendar[0].get(Calendar.MINUTE);

                timePickerDialog[0] = new TimePickerDialog(search_customer.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm[0] = "PM";
                        } else {
                            amPm[0] = "AM";
                        }
                        timeFrom[0] = String.format("%02d:%02d", hourOfDay, minutes) + amPm[0];
                        textViewFrom.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm[0]);
                        calendarFrom[0] = Calendar.getInstance();


                        calendarFrom[0].set( Calendar.HOUR_OF_DAY, hourOfDay);
                        calendarFrom[0].set( Calendar.MINUTE, minutes);

                    }
                }, currentHour[0], currentMinute[0], false);





                timePickerDialog[0].show();
            }
        });
        final int[] year = new int[1];
        final int[] month = new int[1];
        final int[] day = new int[1];

        final int[] theYear = new int[1];
        final int[] theMonth = new int[1];
        final int[] theDay = new int[1];
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                 year[0] = cal.get(Calendar.YEAR);
                 month[0] = cal.get(Calendar.MONTH);
                 day[0] = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        search_customer.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year[0], month[0], day[0]);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Calendar calendar2 , calendar3;
                calendar2 = Calendar.getInstance();
                calendar3 = Calendar.getInstance();
                calendar3.set(Calendar.DAY_OF_MONTH , day);
                calendar3.set(Calendar.YEAR , year);
                calendar3.set(Calendar.MONTH , month);

                Log.d("current" , calendar2.toString());
                Log.d("current3" , calendar3.toString());
                if(calendar2.before(calendar3)){
                    date[0] = month + "/" + day + "/" + year;
                    theYear[0] = year;
                    theDay[0] = day;
                    theMonth[0] = month;
                    mDisplayDate.setText(date[0]);

                }
                else{
                    Toast.makeText(search_customer.this,"enter a correct date", Toast.LENGTH_LONG).show();
                }
                             }
        };

        btn_thisapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                DatabaseReference myRef = database.getReference("meeting");
                String myId = myRef.push().getKey();

                Meeting meeting = new Meeting(myId,ID , IDBS , name , timeFrom[0],timeTo[0],date[0],phone_num,theYear[0],theMonth[0],theDay[0],currentHour1[0],currentMinute1[0]);
                myRef.child(myId).setValue(meeting);
                Toast.makeText(search_customer.this,"send a request " , Toast.LENGTH_LONG).show();

                return;
            }
        });
        btn_dialog_Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String e_mail = email;
                Intent intent;
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, e_mail);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Order com.example.babys.Customer");
                intent.putExtra(Intent.EXTRA_TEXT, massage);
                startActivity(intent);
            }
        });

        btn_dialog_SMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String theNum = phone_num;


                try {
                    SmsManager smgr = SmsManager.getDefault();
                    smgr.sendTextMessage(theNum, null, massage, null, null);
                    Toast.makeText(search_customer.this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(search_customer.this, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_dialog_Whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp");
                if (isWhatsappInstalled) {
                    Uri uri = Uri.parse("smsto:" + phone_num);
                    Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, massage);
                    sendIntent.setType("text/plain");
                    sendIntent.setPackage("com.whatsapp");
                    startActivity(sendIntent);
                } else {
                    Toast.makeText(search_customer.this, "WhatsApp not Installed",
                            Toast.LENGTH_SHORT).show();
                    Uri uri = Uri.parse("market://details?id=com.whatsapp");
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(goToMarket);

                }
            }
        });
        btn_dialog_Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = phone_num;
                dialContactPhone(number);
            }
        });


        builder.create().show();
    }
    private void theThirdDialog(final String msg , final String id){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(msg);
                alertDialogBuilder.setPositiveButton("ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                //database.getReference("MessageOrFeedback").child(id).setValue(null);
                            }
                        });



        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void theSecondDialog(final String IDBS) {

        final View view = getLayoutInflater().inflate(R.layout.dialogfeedback,null);

        final EditText edittext1 = (EditText)view.findViewById(R.id.editText_review);
        final RatingBar ratingBar = (RatingBar)view.findViewById(R.id.ratingBar_stars);



         Button button_report = (Button)view.findViewById(R.id.button_report);

         final AlertDialog.Builder builder = new AlertDialog.Builder(search_customer.this);
        final AlertDialog dialog = builder.create();
        builder.setView(view);
        builder.create().show();

        button_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String review = edittext1.getText().toString();
                final float num_star = ratingBar.getRating();
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                DatabaseReference myRef = database.getReference("feedbacks");
                feedbackReport feedbackReport=new feedbackReport(IDBS ,ID ,name , review ,num_star,false,false);
                myRef.push().setValue(feedbackReport);
                dialog.dismiss();

                Intent intent = new Intent(getApplicationContext(), search_customer.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Name", name);
                intent.putExtra("Password", password);
                intent.putExtra("Email", email);
                intent.putExtra("ID", ID);
                startActivity(intent);
                finish();

            }
        });



    }

    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }
    private void messageor(MessageOrFeedback messageFeedback){

        boolean temp = false;
        if(messageFeedback.message.equals("ACCEPTED"))
        {

            temp=true;
        }
        theThirdDialog("Hi ,i am "+messageFeedback.nameBS+" and i "+messageFeedback.message+" your issue" , messageFeedback.ID);
        database.getReference("MessageOrFeedback").child(messageFeedback.ID).setValue(null);
        if(temp){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("feedbackfromCu");
            String mId = myRef.push().getKey();
            feedbackfromCu feedbackfromCu=new feedbackfromCu(mId ,messageFeedback.IDBS ,messageFeedback.IDCU , name ,messageFeedback.year,messageFeedback.mounth,messageFeedback.day,messageFeedback.hour,messageFeedback.minute);

            myRef.child(mId).setValue(feedbackfromCu);



        }


}

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
