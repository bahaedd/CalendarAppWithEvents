package com.example.calenderapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.sql.StatementEvent;

import androidx.annotation.Nullable;

import static com.example.calenderapp.DBStructure.TIME;

public class DBOpenHelper extends SQLiteOpenHelper {

    public static final String CREATE_EVENTS_TABLE = "create table "+DBStructure.EVENT_TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
            +DBStructure.EVENT+" TEXT,"+ TIME+" TEXT,"+DBStructure.DATE+" TEXT,"+ DBStructure.MONTH+" TEXT, "+DBStructure.YEAR+" TEXT,"+DBStructure.Notify+" TEXT)";
    public static final String DROP_EVENTS_TABLE = "DROP TABLE IF EXISTS "+DBStructure.EVENT_TABLE_NAME;



    public DBOpenHelper(@Nullable Context context) {
        super(context, DBStructure.DB_NAME, null,DBStructure.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_EVENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DROP_EVENTS_TABLE);
        onCreate(db);
    }

    public void SaveEvent(String event, String time, String date, String month, String year, String notify,SQLiteDatabase database){

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBStructure.EVENT,event);
        contentValues.put(TIME,time);
        contentValues.put(DBStructure.DATE,date);
        contentValues.put(DBStructure.MONTH,month);
        contentValues.put(DBStructure.YEAR,year);
        contentValues.put(DBStructure.Notify,notify);
        database.insert(DBStructure.EVENT_TABLE_NAME,null,contentValues);





    }
    public Cursor ReadEvents(String date, SQLiteDatabase database){

        String [] Projections = {DBStructure.EVENT,DBStructure.TIME,DBStructure.DATE,DBStructure.MONTH,DBStructure.YEAR};
        String Selection = DBStructure.DATE+"=?";
        String [] SelectionArgs = {date};

        return database.query(DBStructure.EVENT_TABLE_NAME,Projections,Selection,SelectionArgs,null,null,null);

    }

   public Cursor ReadIDEvents(String date,String event, String time, SQLiteDatabase database){

        String [] Projections = {DBStructure.ID,DBStructure.Notify};
        String Selection = DBStructure.DATE+"=? and "+DBStructure.EVENT+"=? and "+ TIME+"=?";
        String [] SelectionArgs = {date,event,time};

        return database.query(DBStructure.EVENT_TABLE_NAME,Projections,Selection,SelectionArgs,null,null,null);

    }

  public Cursor ReadEventsperMonth(String month, String year, SQLiteDatabase databasee){

        String [] Projections = {DBStructure.EVENT,DBStructure.TIME,DBStructure.DATE,DBStructure.MONTH,DBStructure.YEAR};
        String Selection = DBStructure.MONTH+"=? and "+DBStructure.YEAR+"=?";
        String [] SelectionArgs = {month,year};

        return databasee.query(DBStructure.EVENT_TABLE_NAME,Projections,Selection,SelectionArgs,null,null,null);

    }

    public void deleteEvent(String event, String date, String time,SQLiteDatabase database){
        String selection = DBStructure.EVENT+"=? and "+DBStructure.DATE+"=? and "+ DBStructure.TIME+"=?";
        String[] selectionArgs = {event,date,time};
        database.delete(DBStructure.EVENT_TABLE_NAME,selection,selectionArgs);

    }
    public void updateEvent(String date, String event, String time,String notify,SQLiteDatabase databasee){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBStructure.Notify,notify);
        String Selection = DBStructure.DATE+"=? and "+DBStructure.EVENT+"=? and "+ TIME+"=?";
        String [] SelectionArgs = {date,event,time};
        databasee.update(DBStructure.EVENT_TABLE_NAME,contentValues,Selection,SelectionArgs);
    }
}
