package com.example.kcaltracker.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

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

        String [] sqlSelect = {
                "descricao"
        };

        String [] selectionArgs = {
                "%" + foodName + "%"
        };

        Cursor c = db.rawQuery("SELECT descricao FROM [taco.info] WHERE descricao LIKE ?", selectionArgs);

        ArrayList<String> foodList = new ArrayList<String>();

        c.moveToFirst();
        do {
            foodList.add(c.getString(0));
        } while (c.moveToNext());
        return foodList;
    }
}