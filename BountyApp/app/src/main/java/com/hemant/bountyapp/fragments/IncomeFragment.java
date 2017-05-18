package com.hemant.bountyapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hemant.bountyapp.R;
import com.hemant.bountyapp.database.AppDatabase;
import com.hemant.bountyapp.models.IncomeModel;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by hemant on 5/12/2017.
 */

public class IncomeFragment extends Fragment implements View.OnClickListener {

    int color;
    EditText incomeEditText;
    EditText remarkEditText;
    String category;
    String dateText;
    Button saveButton;

    public IncomeFragment() {

    }
    @SuppressLint("ValidFragment")
    public IncomeFragment(int color) {
        this.color = color;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.income_fragment, container, false);
        incomeEditText = (EditText) view.findViewById(R.id.income_edit_text);
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        dateText = date.toString();
       // Log.wtf("currentDate", "^^^^" + dateText);
        remarkEditText = (EditText) view.findViewById(R.id.income_remark_edit_text);
        saveButton = (Button) view.findViewById(R.id.income_save_button);
        saveButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.income_save_button:
                if (incomeEditText.getText().toString() != null && !incomeEditText.getText().toString().equalsIgnoreCase("")) {
                    AppDatabase db = new AppDatabase(getContext());
                    String uuid = UUID.randomUUID().toString();
                    IncomeModel income = new IncomeModel();
                    income.setId(uuid);
                    income.setAmount(incomeEditText.getText().toString().trim());
                    income.setRemark(remarkEditText.getText().toString().trim());
                    income.setDate(dateText);
                    db.insertIncome(income);
                    incomeEditText.setText("");
                    remarkEditText.setText("");
                } else {
                    incomeEditText.setError("Please enter amount");

                }
                break;
        }
    }
}
