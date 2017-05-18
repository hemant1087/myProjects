package com.hemant.bountyapp.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.hemant.bountyapp.R;
import com.hemant.bountyapp.utilities.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hemant on 5/15/2017.
 */

public class PermissionActivity extends AppCompatActivity {

    Button continueButton;
    SharedPreferences preferences;
    public static int instance = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permission_activity);
        continueButton = (Button) findViewById(R.id.activity_main_button_continue);
        preferences = getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE);
        try {
            instance = preferences.getInt("Instance", 0);
            //  Log.wtf("received Instance","--------- "+instance);
            if (instance == 1) {
                Intent intent = new Intent(PermissionActivity.this, DashBoard.class);
                startActivity(intent);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                        requestForPermission();

                    } else {
                        Intent intent = new Intent(PermissionActivity.this, DashBoard.class);
                        preferences.edit().putInt("Instance", 1).commit();
                        startActivity(intent);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void requestForPermission() {

        int read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        int receive = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);

        List<String> listPermissionNeeded = new ArrayList<>();
        if (read != PackageManager.PERMISSION_GRANTED) {

            listPermissionNeeded.add(Manifest.permission.READ_SMS);
        }

        if (receive != PackageManager.PERMISSION_GRANTED) {
            listPermissionNeeded.add(Manifest.permission.RECEIVE_SMS);
        }

        if (!listPermissionNeeded.isEmpty()) {

            ActivityCompat.requestPermissions(this, listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]), Constants.REQUEST_ID_MULTIPLE_PERMISSIONS);


        } else {
            Intent intent = new Intent(PermissionActivity.this, DashBoard.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            preferences.edit().putInt("Instance", 1).commit();
            startActivity(intent);
        }

    }

}
