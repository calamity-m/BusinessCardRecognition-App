package com.example.canberra.businesscardsaver;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
/**
 * Created by Mark on 1/04/2017.
 */

public class BusinessCardDbHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "cards";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_COMPANY = "company";
    public static final String COLUMN_WEBSITE = "website";
    public static final String COLUMN_PHONE_NUMBER = "phoneNumber";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_EMAIL_ADDRESS = "emailAddress";

    public BusinessCardDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME + "(" +
                COLUMN_ID + " integer primary key, " +
                COLUMN_TITLE + " text, " +
                COLUMN_NAME + " text, " +
                COLUMN_COMPANY + " text," +
                COLUMN_WEBSITE + " text," +
                COLUMN_PHONE_NUMBER + " text," +
                COLUMN_ADDRESS + " text," +
                COLUMN_EMAIL_ADDRESS + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<BusinessCard> getAllCards() {
        ArrayList<BusinessCard> cardList = new ArrayList<BusinessCard>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            BusinessCard card = new BusinessCard(
                    res.getString(res.getColumnIndex(COLUMN_TITLE)),
                    res.getString(res.getColumnIndex(COLUMN_NAME)),
                    res.getString(res.getColumnIndex(COLUMN_COMPANY)),
                    res.getString(res.getColumnIndex(COLUMN_WEBSITE)),
                    res.getString(res.getColumnIndex(COLUMN_PHONE_NUMBER)),
                    res.getString(res.getColumnIndex(COLUMN_ADDRESS)),
                    res.getString(res.getColumnIndex(COLUMN_EMAIL_ADDRESS))
            );
            card.setId(res.getString(res.getColumnIndex(COLUMN_ID)));
            cardList.add(card);
            res.moveToNext();
        }
        return cardList;
    }

    public String insertCard(BusinessCard card) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, card.getTitle());
        contentValues.put(COLUMN_NAME, card.getName());
        contentValues.put(COLUMN_COMPANY, card.getCompany());
        contentValues.put(COLUMN_WEBSITE, card.getWebsite());
        contentValues.put(COLUMN_PHONE_NUMBER, card.getPhoneNumber());
        contentValues.put(COLUMN_ADDRESS, card.getAddress());
        contentValues.put(COLUMN_EMAIL_ADDRESS, card.getEmailAddress());
        long id = db.insert(TABLE_NAME, null, contentValues);
        card.setId(Long.toString(id));
        return Long.toString(id);
    }

    public Integer deleteCard(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "id = ? ", new String[]{id});
    }

    public boolean updateCard(String id, BusinessCard card) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, card.getTitle());
        contentValues.put(COLUMN_NAME, card.getName());
        contentValues.put(COLUMN_COMPANY, card.getCompany());
        contentValues.put(COLUMN_WEBSITE, card.getWebsite());
        contentValues.put(COLUMN_PHONE_NUMBER, card.getPhoneNumber());
        contentValues.put(COLUMN_ADDRESS, card.getAddress());
        contentValues.put(COLUMN_EMAIL_ADDRESS, card.getEmailAddress());
        int a = db.update(TABLE_NAME, contentValues, "id = ? ", new String[]{id});

        return true;
    }


}
