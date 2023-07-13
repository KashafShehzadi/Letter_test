package com.example.letters_test;

import static androidx.core.content.ContentProviderCompat.requireContext;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ShiftDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "shifts.db";
    private static final String TABLE_SHIFTS = "shifts";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_SHIFT_NUMBER = "shift_number";
    private static final String COLUMN_ANSWERS = "answers";

    public ShiftDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SHIFTS_TABLE = "CREATE TABLE " + TABLE_SHIFTS +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_SHIFT_NUMBER + " INTEGER," +
                COLUMN_ANSWERS + " TEXT" +
                ")";
        db.execSQL(CREATE_SHIFTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHIFTS);
        onCreate(db);
    }

    public void addShift(int shiftNumber, List<String> answers) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SHIFT_NUMBER, shiftNumber);
        values.put(COLUMN_ANSWERS, convertListToString(answers));
        db.insert(TABLE_SHIFTS, null, values);

    }

    public List<String> getShiftAnswers(int shiftNumber) {
        List<String> answersList = new ArrayList<>();
        String query = "SELECT " + COLUMN_ANSWERS + " FROM " + TABLE_SHIFTS +
                " WHERE " + COLUMN_SHIFT_NUMBER + " = " + shiftNumber;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String answers = cursor.getString(cursor.getColumnIndex(COLUMN_ANSWERS));
            answersList = convertStringToList(answers);
        }
        cursor.close();

        return answersList;
    }

    private String convertListToString(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(list.get(i));
        }
        return stringBuilder.toString();
    }

    private List<String> convertStringToList(String str) {
        List<String> list = new ArrayList<>();
        String[] items = str.split(",");
        for (String item : items) {
            list.add(item);
        }
        return list;
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SHIFTS, null, null);

    }

    public List<List<String>> getAllShifts() {
        List<List<String>> shiftList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_SHIFTS;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String answers = cursor.getString(cursor.getColumnIndex(COLUMN_ANSWERS));
                List<String> shift = convertStringToList(answers);
                shiftList.add(shift);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return shiftList;
    }


}

