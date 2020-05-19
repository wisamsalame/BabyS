package com.babysitter.babys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

public class profileBSCustomer extends AppCompatActivity implements View.OnClickListener {
    private static final int RESULT_SHARE_SMS = 4;
    private static final int RESULT_SHARE_MAIL = 5;
    String name, password, email ,data,phone_num,active_limitations , ID , city,address , imagrURL;
    boolean experince,ifHaveCar , ifhisMale ;
    String age;
    double salary ,latitude , longitude ;
    TextView bsProfileCustomer_gender,bsProfileCustomer_address , bsProfileCustomer_name , bsProfileCustomer_age , bsProfileCustomer_location ,bsProfileCustomer_experiance,
            bsProfileCustomer_salary,bsProfileCustomer_Email,bsProfileCustomer_limate,bsProfileCustomer_car,bsProfileCustomer_data;

    Button btn_accept;
    ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_bscustomer);
        Intent intent = getIntent();



        bsProfileCustomer_name = (TextView)findViewById(R.id.bsProfileCustomer_name);
        bsProfileCustomer_gender = (TextView)findViewById(R.id.bsProfileCustomer_gender);
        bsProfileCustomer_address = (TextView)findViewById(R.id.bsProfileCustomer_address);
        bsProfileCustomer_age = (TextView)findViewById(R.id.bsProfileCustomer_age);
        bsProfileCustomer_location = (TextView)findViewById(R.id.bsProfileCustomer_location);
        bsProfileCustomer_experiance = (TextView)findViewById(R.id.bsProfileCustomer_experiance);
        bsProfileCustomer_salary = (TextView)findViewById(R.id.bsProfileCustomer_salary);
        bsProfileCustomer_Email = (TextView)findViewById(R.id.bsProfileCustomer_Email);
        bsProfileCustomer_limate = (TextView)findViewById(R.id.bsProfileCustomer_limate);
        bsProfileCustomer_car = (TextView)findViewById(R.id.bsProfileCustomer_car);
        bsProfileCustomer_data = (TextView)findViewById(R.id.bsProfileCustomer_data);
        btn_accept = (Button)findViewById(R.id.btn_accept);
        imageView1 = (ImageView)findViewById(R.id.imageView1);

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
        imagrURL = intent.getStringExtra("imageURL");
//        Log.d("imagrURL : " , imagrURL);

         if(imagrURL!=null){
         new profileBSCustomer.DownloadImageTask((ImageView) findViewById(R.id.imageView1))
            .execute(imagrURL);
        }
        bsProfileCustomer_name.setText(name);
        bsProfileCustomer_age.setText(age);
        bsProfileCustomer_location.setText(city);
        bsProfileCustomer_experiance.setText(experince+"");
        bsProfileCustomer_salary.setText(salary+"");
        bsProfileCustomer_Email.setText(email);
        bsProfileCustomer_limate.setText(active_limitations);
        bsProfileCustomer_car.setText(ifHaveCar+"");
        bsProfileCustomer_data.setText(data);

        if(ifhisMale){
            bsProfileCustomer_gender.setText(" Male");
        }else{
            bsProfileCustomer_gender.setText(" Female");
        }

        if(address==null || address.isEmpty() || address.equals(" ") || address.equals("please enter your address")){
            bsProfileCustomer_address.setText("no address");
        }
        else{
            bsProfileCustomer_address.setText(address);
        }
        btn_accept.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_accept:
                theDialog(RESULT_SHARE_MAIL);



        }
    }


    private void theDialog(int resultLoad) {

        final String whatIWantToShare= "This Film " + " is Wanderful";/////////////////////////////////////////////////
        AlertDialog.Builder builder = new AlertDialog.Builder(profileBSCustomer.this);
        builder.setTitle("Title");
        builder.setItems(new CharSequence[]
                        {"Email", "SMS", "Whatsapp", "Call" ,"Chat"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which) {
                            case 0:
                                //What ever you want to do with the value
                                String e_mail = email;
                                Intent intent;
                                intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("message/rfc822");
                                intent.putExtra(Intent.EXTRA_EMAIL, e_mail);
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Film");
                                intent.putExtra(Intent.EXTRA_TEXT, whatIWantToShare);
                                startActivity(intent);


                                break;
                            case 1:
                                //What ever you want to do with the value
                                String theNum = phone_num;


                                try{
                                    SmsManager smgr = SmsManager.getDefault();
                                    smgr.sendTextMessage(theNum,null,whatIWantToShare,null,null);
                                    Toast.makeText(profileBSCustomer.this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
                                }
                                catch (Exception e){
                                    Toast.makeText(profileBSCustomer.this, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
                                }


                                break;
                            case 2:
                                boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp");
                                if (isWhatsappInstalled) {
                                    Uri uri = Uri.parse("smsto:" + phone_num);
                                    Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
                                    sendIntent.putExtra(Intent.EXTRA_TEXT, whatIWantToShare);
                                    sendIntent.setType("text/plain");
                                    sendIntent.setPackage("com.whatsapp");
                                    startActivity(sendIntent);
                                } else {
                                    Toast.makeText(profileBSCustomer.this, "WhatsApp not Installed",
                                            Toast.LENGTH_SHORT).show();
                                    Uri uri = Uri.parse("market://details?id=com.whatsapp");
                                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(goToMarket);

                                }
                                break;
                            case 3:
                                String number=phone_num;
                                dialContactPhone(number);
                                break;
                            case 4:
                                //Toast.makeText(this,)
                                break;
                        }
                    }
                });
        builder.create().show();



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
