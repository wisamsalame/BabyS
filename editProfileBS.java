package com.babysitter.babys;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class editProfileBS extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private final int PICK_IMAGE_REQUEST = 71;
    private static final int CAMERA_REQUEST = 10001;
    private static final int RESULT_LOAD_IMAGE = 2;
    private FusedLocationProviderClient client;
    private FusedLocationProviderClient fusedLocationClient;
    String name, password, email, data, phone_num, active_limitations, ID, city ,temp_Salary, address ;
    boolean experince, ifHaveCar ,ifhisMale;
    double salary ,latitude , longitude ;
    String age ;
    Location baseLocation;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    EditText editTextFor_name, editText_age, editText_phoneNum, editText_limitations, editText_data,editText_Salary,et_address;
    CheckBox checkBox_ifHaveACar, checkBox_ifHaveExperince;
    Button btn_Edit, btn_Out1;
    TextView textView__age, textView_location;
    ProgressBar progressBar;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    FirebaseStorage storage;
    StorageReference storageReference;
    ImageView theImageButton;
    RadioButton rb_Male , rb_female;
    private Uri filePath;
    private Uri imageurl;
    private String imageurlString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_bs);

        Intent intent = getIntent();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        client = LocationServices.getFusedLocationProviderClient(this);
        name = intent.getStringExtra("Name");
        password = intent.getStringExtra("Password");
        email = intent.getStringExtra("Email");
        data = intent.getStringExtra("Data");
        phone_num = intent.getStringExtra("Phone_num");
        active_limitations = intent.getStringExtra("Active_limitations");
        experince = intent.getBooleanExtra("Experince", false);

        ifHaveCar = intent.getBooleanExtra("IfHaveCar", false);
        age = intent.getStringExtra("Age");
        ID = intent.getStringExtra("ID");
        city = intent.getStringExtra("City");
        salary = intent.getDoubleExtra("Salary", 0.0);
        latitude= intent.getDoubleExtra("Latitude",0.0);
        longitude= intent.getDoubleExtra("Longitude",0.0);
        address = intent.getStringExtra("Address");
        ifhisMale = intent.getBooleanExtra("IfhisMale", false);
        imageurlString = intent.getStringExtra("imageurl");

        rb_Male = (RadioButton)findViewById(R.id.radioM);
        rb_female = (RadioButton)findViewById(R.id.radioF);
        if(ifhisMale){
            rb_Male.setChecked(true);
            rb_female.setChecked(false);
        }else{
            rb_Male.setChecked(false);
            rb_female.setChecked(true);
        }




        radioGroup = (RadioGroup)findViewById(R.id.radioGrp) ;
        editTextFor_name = (EditText) findViewById(R.id.editTextFor_name);
        textView__age = (TextView) findViewById(R.id.textView__age);
        textView_location = (TextView) findViewById(R.id.textView_location);
        editText_phoneNum = (EditText) findViewById(R.id.editText_phoneNum);
        editText_limitations = (EditText) findViewById(R.id.editText_limitations);
        editText_data = (EditText) findViewById(R.id.editText_data);
        checkBox_ifHaveACar = (CheckBox) findViewById(R.id.checkBox_ifHaveACar);
        checkBox_ifHaveExperince = (CheckBox) findViewById(R.id.checkBox_ifHaveExperince);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        editText_Salary = (EditText)findViewById(R.id.editText_Salary);
        btn_Edit = (Button) findViewById(R.id.btn_Edit);
        btn_Out1 = (Button) findViewById(R.id.btn_Out1);
        checkBox_ifHaveACar.setChecked(ifHaveCar);
        checkBox_ifHaveExperince.setChecked(experince);
        et_address = (EditText)findViewById(R.id.editTextFor_adress);
        theImageButton = (ImageView) findViewById(R.id.theImageButton);


theImageButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        chooseImage();

    }
});
        textView__age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        editProfileBS.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        textView_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(editProfileBS.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(editProfileBS.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    Log.d("longitudeeee",longitude+"");
                    return;
                }
                client.getLastLocation().addOnSuccessListener(editProfileBS.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location!= null){
                            baseLocation = location;
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            Log.d("longitude",longitude+"");
                            Geocoder gcd = new Geocoder(editProfileBS.this, Locale.ENGLISH);
                            List<Address> addresses = null;
                            try {
                                addresses = gcd.getFromLocation(latitude, longitude, 1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (addresses.size() > 0) {
                                //babySittersCitis.add(addresses.get(0).getLocality());
                                city = addresses.get(0).getLocality();
                                textView_location.setText(addresses.get(0).getLocality());
                            }
                            else {
                                textView_location.setText("problem");
                            }


;
                        }
                    }
                });

            }
        });



        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                //Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                textView__age.setText(date);
            }
        };

        textView__age.setText(age);
        editTextFor_name.setText(name);
//        editText_age.setText(age);
        editText_phoneNum.setText(phone_num);
        editText_limitations.setText(active_limitations);
        editText_data.setText(data);
        textView_location.setText(city);
        et_address.setText(address);
        new DownloadImageTask((ImageView) findViewById(R.id.theImageButton))
                .execute(imageurlString);



        setUpClick();

    }

    private void fileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                theImageButton.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }



    private void setUpClick() {

        btn_Edit.setOnClickListener(this);
        btn_Out1.setOnClickListener(this);
    }
    private void requestPermision(){
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION} , 1);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Edit:

                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                if(radioButton.getText().equals("Male")){
                    ifhisMale=true;
                }else{
                    ifhisMale=false;
                }
                progressBar.setVisibility(View.VISIBLE);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                name =editTextFor_name.getText().toString();
                age = textView__age.getText().toString();
                phone_num = editText_phoneNum.getText().toString();
                active_limitations = editText_limitations.getText().toString();
                temp_Salary = editText_Salary.getText().toString();
                data = editText_data.getText().toString();
                address = et_address.getText().toString();


                if(!temp_Salary.isEmpty())
                    try
                    {
                        salary= Double.parseDouble(temp_Salary);
                        // it means it is double
                    } catch (Exception e1) {
                        // this means it is not double
                        e1.printStackTrace();
                    }

                data = editText_data.getText().toString();

                if(checkBox_ifHaveACar.isChecked()){
                    ifHaveCar = true;
                }
                else{
                    ifHaveCar = false;
                }

                if(checkBox_ifHaveExperince.isChecked()){
                    experince = true;
                }
                else{
                    experince = false;
                }

                uploadImage();
                myRef.child("Users").child(ID).child("name").setValue(name);
                myRef.child("Users").child(ID).child("active_limitations").setValue(active_limitations);
                myRef.child("Users").child(ID).child("data").setValue(data);
                myRef.child("Users").child(ID).child("phone_num").setValue(phone_num);
                myRef.child("Users").child(ID).child("experince").setValue(experince);
                myRef.child("Users").child(ID).child("ifHaveCar").setValue(ifHaveCar);
                myRef.child("Users").child(ID).child("age").setValue(age);
                myRef.child("Users").child(ID).child("salary").setValue(salary);
                myRef.child("Users").child(ID).child("latitude").setValue(latitude);
                myRef.child("Users").child(ID).child("longitude").setValue(longitude);
                myRef.child("Users").child(ID).child("city").setValue(city);
                myRef.child("Users").child(ID).child("ifhisMale").setValue(ifhisMale);
                myRef.child("Users").child(ID).child("address").setValue(address);




                Intent intent1 = new Intent(this, profileBS.class);
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
                intent1.putExtra("imageurl",imageurl);
                startActivity(intent1);
                finish();
                break;
            case R.id.btn_Out1:
                Intent intent = new Intent(this, profileBS.class);
                startActivity(intent);
                finish();
                break;

        }
    }

    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/"+ ID);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            getDownloadURL(ref);
                            progressDialog.dismiss();
                            Log.d("filepath",filePath+"");
                            Toast.makeText(editProfileBS.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(editProfileBS.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    private void getDownloadURL(StorageReference ref) {
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                myRef.child("Users").child(ID).child("imageURL").setValue(uri.toString());
                Log.d("URI" , uri.toString() + "");
                Log.d("ID" , ID + "");
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
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
