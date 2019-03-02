package com.simplenote.abdulazizfarah.firstapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by abdulazizfarah on 2018-03-30.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

  private static final String TAG = "DatabaseHelper";

  private static final String TABLE_NAME = "note_table";
  private static final String COL1 = "title";
  private static final String COL2 = "note";

    public DatabaseHelper(Context context){
        super(context, TABLE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL1 + " TEXT, "  + COL2 + " TEXT); ";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS "  + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData (String title, String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, title);
        contentValues.put(COL2, note);

        Log.d(TAG, "addData: Adding " + title + note + "to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1){
            return false;
         }else {
            return true;
        }


    }



    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getNote(String title){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL2 + " FROM " + TABLE_NAME +
                " WHERE " + COL1 + " = '" + title + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateNote(String newNote, String title, String oldNote){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                " = '" + newNote + "' WHERE " + COL1 + " = '" + title + "'" + " AND " + COL2 + " = '" + oldNote + "'";
        Log.d(TAG, "updateNote: query: " + query);
        Log.d(TAG, "updateNote : Setting note to " + newNote);
        db.execSQL(query);
    }

    public void deleteNote(String title, String note){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + title + "'" +
                " AND " + COL2 + " = '" + note + "'";
        Log.d(TAG, "deleteNote: query: " + query);
        Log.d(TAG, "deleteNote: deleting " + note + " from database.");
        db.execSQL(query);
    }






}
