package com.hemant.bountyapp.fragments;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.db.chart.model.LineSet;
import com.db.chart.view.LineChartView;
import com.google.firebase.crash.FirebaseCrash;
import com.hemant.bountyapp.R;
import com.hemant.bountyapp.adapters.ExpanseDetailAdapter;
import com.hemant.bountyapp.database.AppDatabase;
import com.hemant.bountyapp.models.ExpenditureModel;
import com.hemant.bountyapp.models.IncomeModel;
import com.hemant.bountyapp.utilities.CommonMethods;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hemant on 5/11/2017.
 */

public class DetailsFragment extends Fragment {

    int color;
    RecyclerView recyclerView;
    List<ExpenditureModel> expanseList;


    AppDatabase db;

    LineChartView chartView;
    float[] values;
    String[] dateLabels;
    ExpenseFragment expanceFragment;
    CommonMethods methods;
    TextView noDataText;

    public DetailsFragment() {

    }

    @SuppressLint("ValidFragment")
    public DetailsFragment(int color) {
        this.color = color;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_expense_details, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.expance_detail_recycler_view);
        noDataText = (TextView) view.findViewById(R.id.no_data_text);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        methods = new CommonMethods();
        expanceFragment = new ExpenseFragment();
        expanseList = new ArrayList<>();

        try {
            db = new AppDatabase(getContext());
            expanseList = db.getExpenditure(0);
            chartView = (LineChartView) view.findViewById(R.id.linechart);

            if (expanseList != null && expanseList.size() > 0) {
                //Log.wtf("received List","***********"+expanseList);
                RecyclerView.Adapter adapter = new ExpanseDetailAdapter(getContext(), expanseList);
                //ExpanseDetailAdapter adapter = new ExpanseDetailAdapter(getContext(),expanseList);
                // adapter.swap(expanseList);
                // recyclerView.setAdapter(adapter);
                recyclerView.swapAdapter(adapter, false);
                adapter.notifyDataSetChanged();

                dateLabels = new String[expanseList.size()];
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
                for (int i = 0; i < expanseList.size(); i++) {
                    int timeDifference = methods.timeDifference(expanseList.get(i).getDate());
                    // Log.wtf("timeDifference", "%%%%%%%%%%%%%%%%%%%%%%" + timeDifference);
                    if (timeDifference < 30) {
                        Date formattedDate = sdf.parse(expanseList.get(i).getDate());
                        DateFormat df = new SimpleDateFormat("dd-MM");
                        String newDate = df.format(formattedDate);
                        dateLabels[i] = newDate;
                    }
                }

                values = StringToFloat(expanseList);
                LineSet dateSet = new LineSet(dateLabels, values);
                dateSet.setDotsColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                dateSet.setSmooth(true);
                dateSet.setColor(ContextCompat.getColor(getContext(), R.color.colorPink));
                dateSet.setThickness(2f);
                dateSet.setDotsRadius(10f);
                dateSet.beginAt(0);
                chartView.setAxisColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                chartView.setLabelsColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                chartView.setHorizontalScrollBarEnabled(true);
                chartView.setVerticalScrollBarEnabled(true);
                chartView.addData(dateSet);
                chartView.show();
            } else {
                recyclerView.setVisibility(View.GONE);
                noDataText.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }

        return view;
    }

    public float[] StringToFloat(List<ExpenditureModel> expanseList) {
        float[] numbers = new float[0];
        try {
            numbers = new float[expanseList.size()];
            for (int i = 0; i < expanseList.size(); i++) {
                float number = Float.parseFloat(expanseList.get(i).getAmount());
                //float rounded = (int) Math.round(number * 1000)/1000f;
                numbers[i] = number;

            }
            return numbers;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }

        return null;
    }

    //
    BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            loadData();
         //  loadGraph();
        }
    };

    //
    private void loadData() {
        //Toast.makeText(getContext(), "List Size" + expanseList.size(), Toast.LENGTH_LONG).show();
        try {
            expanseList = db.getExpenditure(0);
            if (expanseList != null && expanseList.size() > 0) {
                recyclerView.setAdapter(new ExpanseDetailAdapter(getContext(), expanseList));
            }

        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }

   /* private void loadGraph(){
        try{
           // expanseList = db.getExpenditure(0);
            if (expanseList != null && expanseList.size() > 0) {
                dateLabels = new String[expanseList.size()];
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
                for (int i = 0; i < expanseList.size(); i++) {
                    int timeDifference = methods.timeDifference(expanseList.get(i).getDate());
                    // Log.wtf("timeDifference", "%%%%%%%%%%%%%%%%%%%%%%" + timeDifference);
                    if (timeDifference < 30) {
                        Date formattedDate = sdf.parse(expanseList.get(i).getDate());
                        DateFormat df = new SimpleDateFormat("dd-MM");
                        String newDate = df.format(formattedDate);
                        dateLabels[i] = newDate;
                    }
                }
                values = StringToFloat(expanseList);
                LineSet dateSet = new LineSet(dateLabels, values);
                dateSet.setDotsColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                dateSet.setSmooth(true);
                dateSet.setColor(ContextCompat.getColor(getContext(), R.color.colorPink));
                dateSet.setThickness(2f);
                dateSet.setDotsRadius(10f);
                dateSet.beginAt(0);
                chartView.setAxisColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                chartView.setLabelsColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                chartView.setHorizontalScrollBarEnabled(true);
                chartView.setVerticalScrollBarEnabled(true);
                chartView.addData(dateSet);
                chartView.show();

            }

        }catch (Exception e){

            e.printStackTrace();
            FirebaseCrash.report(e);
        }
    }
*/

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(br, new IntentFilter("refresh"));
        loadData();
      //  loadGraph();
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(br);
    }
}
