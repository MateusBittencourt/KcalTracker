package com.example.kcaltracker.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "taco.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db = getReadableDatabase();
    private SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public ArrayList<String> getDescricao(String foodName) {
        String sqlTables = "[taco.info]";
        String query = "SELECT descricao FROM [taco.info] WHERE";
        String [] selectionArgs = foodName.split("[ ]");
        ArrayList<String> foodList = new ArrayList<String>();

        for (int i = 0; i<selectionArgs.length; i++){
            selectionArgs[i] = "%" + selectionArgs[i] + "%";
            query = query + " descricao LIKE ? AND";
        }
        query = query.substring(0,query.length()-3);

        Cursor c = db.rawQuery(query, selectionArgs);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            foodList.add(c.getString(0));
            c.moveToNext();
        };
        return foodList;
    }

    public FoodItem getFoodItem(String descricao) {
        String sqlTables = "[taco.info]";
        String query = "SELECT * FROM [taco.info] WHERE descricao LIKE ?";
        String [] selectionArgs = { descricao };
        ArrayList<String> foodInfo = new ArrayList<String>();

        Cursor c = db.rawQuery(query, selectionArgs);
        c.moveToFirst();

        int n = c.getColumnCount();

        for (int i = 0; i < n; i++){
            foodInfo.add(c.getString(i));
        }

        FoodItem foodItem = new FoodItem(foodInfo);
        return foodItem;
    }
}