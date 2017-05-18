package com.hemant.bountyapp.utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.hemant.bountyapp.database.AppDatabase;
import com.hemant.bountyapp.models.ExpenditureModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by hemant on 5/16/2017.
 */

public class SMSReceiver extends BroadcastReceiver {

    CommonMethods methods;
    AppDatabase database;
    String date = "";
    String place = "";
    String amount = "";
    ExpenditureModel expanse;

    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            Bundle bundle = intent.getExtras();
            SmsMessage[] smsm = null;
            String sms_str = "";
            database = new AppDatabase(context);
            expanse = new ExpenditureModel();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                smsm = new SmsMessage[pdus.length];
                for (int i = 0; i < smsm.length; i++) {
                    smsm[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    Log.wtf("source", "-----" + smsm[i].getOriginatingAddress());
                    if (smsm[i].getOriginatingAddress().equalsIgnoreCase("+917879292392")) {

                       /* sms_str += "Sent From: " + smsm[i].getOriginatingAddress();
                        sms_str += "\r\nMessage: ";
                        sms_str += smsm[i].getMessageBody().toString();
                        sms_str+= "\r\n";*/
                        sms_str += smsm[i].getMessageBody().toString();
                        Toast.makeText(context, "Received Sms" + sms_str, Toast.LENGTH_LONG).show();
                        methods = new CommonMethods();
                        if (sms_str != null && sms_str.length() > 0) {
                            Log.wtf("sms_str", "--------" + sms_str);
                            String[] sortedArray = methods.processSMS(sms_str);

                            if (sortedArray != null && sortedArray.length > 0) {
                                amount = sortedArray[0];
                                place = sortedArray[1];
                                date = sortedArray[2];
                            }
                            // SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
                            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
                            Date formatDate = formatter.parse(date);
                            DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

                            String newDate = format.format(formatDate);

                            //   Log.wtf("newDate","--------"+newDate);
                            String uuid = UUID.randomUUID().toString();
                            expanse.setDate(newDate);
                            expanse.setRemark(place);
                            expanse.setAmount(amount);
                            expanse.setId(uuid);
                            expanse.setCategory("shooping");
                            database.insertExpenditure(expanse);
                        }
                    } else {
                        sms_str = null;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
