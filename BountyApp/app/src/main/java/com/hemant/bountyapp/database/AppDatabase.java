package com.hemant.bountyapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.hemant.bountyapp.models.ExpenditureModel;
import com.hemant.bountyapp.models.IncomeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hemant on 5/15/2017.
 */

public class AppDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "bounty_app_database";
    // table name

    private static final String TABLE_INCOME = "income_table";
    private static final String TABLE_EXPENDITURE = "expenditure_table";

    // column name

    private static final String KEY_ID =   "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_REMARK = "remark";

    private static final String CREATE_TABLE_INCOME = "CREATE TABLE "+ TABLE_INCOME + "(" +KEY_ID +" TEXT PRIMARY KEY,"
            +KEY_AMOUNT +" TEXT,"
            +KEY_REMARK +" TEXT,"
            +KEY_DATE +" TEXT"
            +")";
    private static final String CREATE_TABLE_EXPENDITURE = "CREATE TABLE "+ TABLE_EXPENDITURE + "(" +KEY_ID +" TEXT PRIMARY KEY,"
            +KEY_AMOUNT +" TEXT,"
            +KEY_CATEGORY +" TEXT,"
            +KEY_REMARK +" TEXT,"
            +KEY_DATE +" TEXT"
            +")";

    private  Context context;
    private  SQLiteDatabase database;


    public AppDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENDITURE);

        db.execSQL(CREATE_TABLE_INCOME);
        db.execSQL(CREATE_TABLE_EXPENDITURE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENDITURE);

        onCreate(db);

    }

    // insert functions

    public void insertIncome(IncomeModel income){
        database = this.getWritableDatabase();
        ContentValues cv  = new ContentValues();
        cv.put(KEY_ID,income.getId());
        cv.put(KEY_AMOUNT,income.getAmount());
        cv.put(KEY_REMARK,income.getRemark());
        cv.put(KEY_DATE,income.getDate());

        int k = database.update(TABLE_INCOME, cv, KEY_ID + "=?", new String[]{String.valueOf(income.getId())});
        if (k == 0) {

            database.insert(TABLE_INCOME, null, cv);

        }
        database.close();
    }

    public void insertExpenditure(ExpenditureModel expense){

        database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_ID,expense.getId());
        cv.put(KEY_AMOUNT,expense.getAmount());
        cv.put(KEY_REMARK,expense.getRemark());
        cv.put(KEY_CATEGORY,expense.getCategory());
        cv.put(KEY_DATE,expense.getDate());
        int k = database.update(TABLE_EXPENDITURE, cv, KEY_ID + "=?", new String[]{String.valueOf(expense.getId())});
        if (k == 0) {

            database.insert(TABLE_EXPENDITURE, null, cv);

        }
        database.close();
    }


    // fetch functions

    public List<IncomeModel> getIncome(int pos){

        database = this.getWritableDatabase();
        String query = "";
        List<IncomeModel> incomeList = new ArrayList<>();
        switch (pos){
            case 0:
                query = "Select * from "+TABLE_INCOME;
                break;
        }
        Cursor cursor = database.rawQuery(query,null);
        if(cursor.moveToFirst()){

            do {
                IncomeModel income = new IncomeModel();
                income.setId(cursor.getString(cursor.getColumnIndex(KEY_ID)));
                income.setAmount(cursor.getString(cursor.getColumnIndex(KEY_AMOUNT)));
                income.setRemark(cursor.getString(cursor.getColumnIndex(KEY_REMARK)));
                income.setDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
                incomeList.add(income);

            }while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return incomeList;
    }


    public List<ExpenditureModel> getExpenditure(int pos){
        database = this.getWritableDatabase();
        String query = "";
        List<ExpenditureModel> expenditureList = new ArrayList<>();
        switch (pos){

            case 0:
                query = "Select * from "+TABLE_EXPENDITURE;
                break;

        }

        Cursor cursor = database.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                ExpenditureModel expense = new ExpenditureModel();
                expense.setId(cursor.getString(cursor.getColumnIndex(KEY_ID)));
                expense.setAmount(cursor.getString(cursor.getColumnIndex(KEY_AMOUNT)));
                expense.setDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
                expense.setRemark(cursor.getString(cursor.getColumnIndex(KEY_REMARK)));
                expense.setCategory(cursor.getString(cursor.getColumnIndex(KEY_CATEGORY)));
                expenditureList.add(expense);

            }while (cursor.moveToNext());

        }
        cursor.close();
        database.close();
        return expenditureList;


    }


}
