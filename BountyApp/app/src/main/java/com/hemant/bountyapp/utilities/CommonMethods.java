package com.hemant.bountyapp.utilities;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by hemant on 5/16/2017.
 */

public class CommonMethods {

    public int timeDifference(String date) {
        Date date1 = null;
        Calendar todayDate = null;
        int diffInDays = 0;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
            todayDate = Calendar.getInstance();
            todayDate.getTimeInMillis();
            date1 = formatter.parse(date);
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date1);
            long ms1 = todayDate.getTimeInMillis();
            long ms2 = c1.getTimeInMillis();
            long diff = ms1 - ms2;
            diffInDays = (int) (diff / (24 * 60 * 60 * 1000));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return diffInDays;
    }

    public String[] processSMS(String receivedSMS) {
        String[] sortedStrings = new String[3];
        String[] tempArray = receivedSMS.split(" ");
        Log.wtf("tempArrayLength", "-------" + tempArray.length);
        String amount = "";
        String date = "";
        String place = "";

       /* for(int i = 0; i<tempArray.length;i++){
            amount = tempArray[13];
            date = tempArray[15];
            place = tempArray[17]+tempArray[18];
        }*/
        sortedStrings[0] = tempArray[13];
        sortedStrings[1] = tempArray[17];
        sortedStrings[2] = tempArray[15];

        Log.wtf("received amount", "------------" + tempArray[13]);
        Log.wtf("received date", "------------" + tempArray[17]);
        Log.wtf("received place", "----------------" + tempArray[15]);

        return sortedStrings;

    }
}
