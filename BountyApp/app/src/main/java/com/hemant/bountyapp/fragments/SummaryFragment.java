package com.hemant.bountyapp.fragments;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.crash.FirebaseCrash;
import com.hemant.bountyapp.R;
import com.hemant.bountyapp.database.AppDatabase;
import com.hemant.bountyapp.models.ExpenditureModel;
import com.hemant.bountyapp.models.IncomeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hemant on 5/11/2017.
 */

public class SummaryFragment extends Fragment {

    TextView totalIncomeText;
    TextView totalExpenditureText;
    TextView grandTotalText;
    AppDatabase db;
    List<IncomeModel> incomeList;
    List<ExpenditureModel> expanseList;
    List<PieEntry> amountList;

    int foodAmount, shoppingAmount, travelAmount, groceryAmount, healthAmount, entertainmentAmount, rentAmount, billsAmount, otherAmount;
    int totalIncome = 0;
    int totalExpenditure = 0;
    int grossTotal;

    int color;
    PieChart pieChart;

    public SummaryFragment() {

    }

    @SuppressLint("ValidFragment")
    public SummaryFragment(int color) {

        this.color = color;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_summary, container, false);
        db = new AppDatabase(getContext());
        incomeList = new ArrayList<>();
        expanseList = new ArrayList<>();
        amountList = new ArrayList<PieEntry>();

        try {
            incomeList = db.getIncome(0);
            expanseList = db.getExpenditure(0);
            totalIncomeText = (TextView) view.findViewById(R.id.total_income_text_view);
            totalExpenditureText = (TextView) view.findViewById(R.id.total_expenditure_text_view);
            grandTotalText = (TextView) view.findViewById(R.id.gross_total_text);
            totalIncome = 0;
            foodAmount = 0;
            shoppingAmount = 0;
            travelAmount = 0;
            groceryAmount = 0;
            healthAmount = 0;
            entertainmentAmount = 0;
            rentAmount = 0;
            billsAmount = 0;
            otherAmount = 0;
            for (int i = 0; i < incomeList.size(); i++) {

                totalIncome = totalIncome + Integer.valueOf(incomeList.get(i).getAmount());
            }
            totalExpenditure = 0;
            for (int j = 0; j < expanseList.size(); j++) {

                totalExpenditure = totalExpenditure + Integer.valueOf(expanseList.get(j).getAmount());

                if (expanseList.get(j).getCategory().equalsIgnoreCase("food")) {

                    foodAmount = foodAmount + Integer.valueOf(expanseList.get(j).getAmount());
                } else if (expanseList.get(j).getCategory().equalsIgnoreCase("shopping")) {

                    shoppingAmount = shoppingAmount + Integer.valueOf(expanseList.get(j).getAmount());
                } else if (expanseList.get(j).getCategory().equalsIgnoreCase("travel")) {

                    travelAmount = travelAmount + Integer.valueOf(expanseList.get(j).getAmount());
                } else if (expanseList.get(j).getCategory().equalsIgnoreCase("grocery")) {

                    groceryAmount = groceryAmount + Integer.valueOf(expanseList.get(j).getAmount());
                } else if (expanseList.get(j).getCategory().equalsIgnoreCase("entertainment")) {

                    entertainmentAmount = entertainmentAmount + Integer.valueOf(expanseList.get(j).getAmount());
                } else if (expanseList.get(j).getCategory().equalsIgnoreCase("health")) {

                    healthAmount = healthAmount + Integer.valueOf(expanseList.get(j).getAmount());
                } else if (expanseList.get(j).getCategory().equalsIgnoreCase("rent")) {

                    rentAmount = rentAmount + Integer.valueOf(expanseList.get(j).getAmount());
                } else if (expanseList.get(j).getCategory().equalsIgnoreCase("bills")) {

                    billsAmount = billsAmount + Integer.valueOf(expanseList.get(j).getAmount());
                } else {
                    otherAmount = otherAmount + Integer.valueOf(expanseList.get(j).getAmount());
                }

            }
            if (foodAmount > 0) {
                amountList.add(new PieEntry(foodAmount, "food"));
            }
            if (shoppingAmount > 0) {
                amountList.add(new PieEntry(shoppingAmount, "shopping"));
            }
            if (travelAmount > 0) {
                amountList.add(new PieEntry(travelAmount, "travel"));
            }
            if (groceryAmount > 0) {
                amountList.add(new PieEntry(groceryAmount, "grocery"));
            }
            if (entertainmentAmount > 0) {
                amountList.add(new PieEntry(entertainmentAmount, "movie"));
            }
            if (healthAmount > 0) {
                amountList.add(new PieEntry(healthAmount, "health"));
            }
            if (rentAmount > 0) {
                amountList.add(new PieEntry(rentAmount, "rent"));
            }
            if (billsAmount > 0) {
                amountList.add(new PieEntry(billsAmount, "bills"));
            }
            if (otherAmount > 0) {
                amountList.add(new PieEntry(otherAmount, "other"));
            }

            grossTotal = totalIncome - totalExpenditure;

            totalIncomeText.setText("Total Income ........................." + getResources().getString(R.string.Rs) + ": " + totalIncome + ".00");
            totalExpenditureText.setText("Total Expenditure.................." + getResources().getString(R.string.Rs) + ": " + totalExpenditure + ".00");
            grandTotalText.setText("Gross Total ......................" + getResources().getString(R.string.Rs) + ": " + grossTotal + ".00");
            pieChart = (PieChart) view.findViewById(R.id.pie_chart);
            PieDataSet dataSet = new PieDataSet(amountList, "");
            dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
            pieChart.setEntryLabelColor(ContextCompat.getColor(getContext(),android.R.color.transparent));
            pieChart.setDrawHoleEnabled(true);
            pieChart.setHoleColor(ContextCompat.getColor(getContext(),android.R.color.transparent));
            // pieChart.setHoleRadius(20f);
            PieData data = new PieData(dataSet);

            data.setValueTextColor(Color.BLACK);
            data.setValueTextSize(13f);
            pieChart.setData(data);
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }


        return view;
    }

}
