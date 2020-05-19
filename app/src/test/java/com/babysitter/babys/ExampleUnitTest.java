package com.babysitter.babys;

import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {
    search_customer search_customer;
    @Test

    public void addition_isCorrect() {
        /* first test */

        String Birthday = "01/01/1994";
        String age = "25";
        //assertEquals(age, getAge(Birthday));

        /* Second test */

        Calendar calendar = Calendar.getInstance();
        calendar.set( Calendar.MONTH, 2);
        calendar.set( Calendar.YEAR, 2020);
        calendar.set( Calendar.DAY_OF_MONTH, 12);
        calendar.set( Calendar.HOUR_OF_DAY, 5);
        calendar.set( Calendar.MINUTE, 20);

        //assertEquals(checkFeedback(calendar),true);


        /* third test */
        String email = "wisam@gmail.com";
       // assertEquals(isEmailValid(email),true);


    }


    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean checkFeedback(Calendar calendar){
        Calendar currentCalendar = Calendar.getInstance();
        if(currentCalendar.after(calendar)){
            // before
            return true;

        }
        //after
        return false;
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

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();
        //Log.d("ageS " , ageS+"");
        return ageS;
    }
}