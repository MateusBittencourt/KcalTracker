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

        String [] sqlSelect = {
                "descricao"
        };

        String [] selectionArgs = foodName.split("[ ]");




        for (int i = 0; i<selectionArgs.length; i++){
            selectionArgs[i] = "%" + selectionArgs[i] + "%";
            query = query + " descricao LIKE ? AND";
        }
        query = query.substring(0,query.length()-3);

        Log.d("Search1", Arrays.toString(selectionArgs));

        Log.d("query", query);


        Cursor c = db.rawQuery(query, selectionArgs);

        ArrayList<String> foodList = new ArrayList<String>();

        c.moveToFirst();

        while (!c.isAfterLast()) {
            foodList.add(c.getString(0));
            c.moveToNext();
        };
        Log.d("foodList", String.valueOf(foodList));
        return foodList;
    }
}