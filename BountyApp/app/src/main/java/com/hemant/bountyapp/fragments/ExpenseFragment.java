package com.hemant.bountyapp.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.hemant.bountyapp.R;
import com.hemant.bountyapp.database.AppDatabase;
import com.hemant.bountyapp.models.ExpenditureModel;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by hemant on 5/12/2017.
 */

public class ExpenseFragment extends Fragment implements View.OnClickListener {

    int color;
    EditText expanseEditText;
    EditText remarkEditText;
    String category;
    String dateText;
    Button saveButton;
    BoomMenuButton boomMenuButton;

    public ExpenseFragment() {

    }

    @SuppressLint("ValidFragment")
    public ExpenseFragment(int color) {

        this.color = color;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.expense_fragment, container, false);
        boomMenuButton = (BoomMenuButton) view.findViewById(R.id.boom_circle);

        expanseEditText = (EditText) view.findViewById(R.id.expense_edit_text);
        remarkEditText = (EditText) view.findViewById(R.id.remark_edit_text);
        saveButton = (Button) view.findViewById(R.id.expanse_save_button);
        try {
            Calendar c = Calendar.getInstance();
            Date date = c.getTime();
            dateText = date.toString();
            // Log.wtf("currentDate", "^^^^" + dateText);
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }

        saveButton.setOnClickListener(this);

        try {
            final int[][] subButtonColors = new int[9][2];
            for (int i = 0; i < 9; i++) {
                subButtonColors[i][1] = GetRandomColor();
                subButtonColors[i][0] = Util.getInstance().getPressedColor(subButtonColors[i][1]);
            }

            final Drawable[] circleSubButtonDrawables = new Drawable[9];
            int[] drawablesResource = new int[]{
                    R.drawable.travel,
                    R.drawable.shopping,
                    R.drawable.food,
                    R.drawable.record,
                    R.drawable.rent,
                    R.drawable.heart,
                    R.drawable.grocery,
                    R.drawable.copy,
                    R.drawable.other,

            };
            for (int i = 0; i < 9; i++)
                circleSubButtonDrawables[i]
                        = ContextCompat.getDrawable(getContext(), drawablesResource[i]);


            final String[] circleSubButtonTexts = new String[]{
                    "Travel",
                    "Shopping",
                    "Food",
                    "Movie",
                    "Rent",
                    "Health",
                    "Groceries",
                    "Bills",
                    "Other"};


            boomMenuButton.postDelayed(new Runnable() {
                @Override
                public void run() {
                    new BoomMenuButton.Builder().subButtons(circleSubButtonDrawables, subButtonColors, circleSubButtonTexts)
                            .button(ButtonType.CIRCLE)
                            .boom(BoomType.LINE)
                            .place(PlaceType.CIRCLE_9_1).subButtonsShadow(Util.getInstance().dp2px(2), Util.getInstance().dp2px(2))
                            .onSubButtonClick(new BoomMenuButton.OnSubButtonClickListener() {
                                @Override
                                public void onClick(int buttonIndex) {

                                    switch (buttonIndex) {
                                        case 0:
                                            category = "Travel";
                                            break;
                                        case 1:
                                            category = "Shopping";
                                            break;
                                        case 2:
                                            category = "Food";
                                            break;
                                        case 3:
                                            category = "Entertainment";
                                            break;
                                        case 4:
                                            category = "Rent";
                                            break;
                                        case 5:
                                            category = "Health";
                                            break;
                                        case 6:
                                            category = "Grocery";
                                            break;
                                        case 7:
                                            category = "Bills";
                                            break;
                                        case 8:
                                            category = "Other";
                                            break;
                                    }
                                    Toast.makeText(
                                            getContext(),
                                            "On click " + circleSubButtonTexts[buttonIndex] + buttonIndex,
                                            Toast.LENGTH_SHORT).show();

                                }
                            })
                            .init(boomMenuButton);
                }
            }, 1);
        } catch (Exception e) {
            FirebaseCrash.report(e);
            e.printStackTrace();
        }

        return view;
    }

    private static String[] Colors = {
            "#F44336",
            "#E91E63",
            "#9C27B0",
            "#2196F3",
            "#03A9F4",
            "#00BCD4",
            "#009688",
            "#4CAF50",
            "#8BC34A",
            "#CDDC39",
            "#FFEB3B",
            "#FFC107",
            "#FF9800",
            "#FF5722",
            "#795548",
            "#9E9E9E",
            "#607D8B"};

    public static int GetRandomColor() {
        int p = 0;
        try {
            Random random = new Random();
            p = random.nextInt(Colors.length);
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.report(e);
        }
        return Color.parseColor(Colors[p]);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.expanse_save_button:
                    try {
                        if (expanseEditText.getText().toString() != null && !expanseEditText.getText().toString().equalsIgnoreCase("")) {

                            if (category != null && !category.equalsIgnoreCase("")) {
                                if (remarkEditText.getText().toString() != null && !remarkEditText.getText().toString().equalsIgnoreCase("")) {
                                    AppDatabase db = new AppDatabase(getContext());
                                    String uuid = UUID.randomUUID().toString();
                                    ExpenditureModel expanseObject = new ExpenditureModel();
                                    expanseObject.setId(uuid);
                                    expanseObject.setDate(dateText);
                                    expanseObject.setAmount(expanseEditText.getText().toString().trim());
                                    expanseObject.setRemark(remarkEditText.getText().toString().trim());
                                    expanseObject.setCategory(category);
                                    db.insertExpenditure(expanseObject);
                                    expanseEditText.setText("");
                                    remarkEditText.setText("");
                                    //
                                    Intent intent = new Intent("refresh");
                                    getActivity().sendBroadcast(intent);
                                } else {
                                    remarkEditText.setError("Please Enter Remark");
                                    remarkEditText.setError("Please Enter Remark");
                                }

                            } else {
                                Toast.makeText(getContext(), "Please Select Category", Toast.LENGTH_SHORT).show();
                            }

                        } else {

                            expanseEditText.setError("Please Enter Amount");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        FirebaseCrash.report(e);
                    }

                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
