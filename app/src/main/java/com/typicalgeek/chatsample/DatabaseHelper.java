package com.typicalgeek.chatsample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MessagesDB";
    private static final int DATABASE_VERSION = 1;
    private static final String MESSAGES_TABLE_NAME = "messages_table";
    private static final String MESSAGES_COL_0 = "ID";
    private static final String MESSAGES_COL_1 = "CONTENT";
    private static final String MESSAGES_COL_2 = "SENDER";
    private static final String MESSAGES_COL_3 = "RECIPIENT";
    private static final String MESSAGES_COL_4 = "DATE";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + MESSAGES_TABLE_NAME + "(" + MESSAGES_COL_0 + " INTEGER PRIMARY KEY, "
                + MESSAGES_COL_1 + " TEXT, " + MESSAGES_COL_2 + " TEXT, " + MESSAGES_COL_3 + " TEXT, " +
                MESSAGES_COL_4 + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        refreshDB();
    }

    public boolean insertMessage(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MESSAGES_COL_1, message.getMessageContent());
        contentValues.put(MESSAGES_COL_2, message.getMessageSender());
        contentValues.put(MESSAGES_COL_3, message.getMessageRecipient());
        contentValues.put(MESSAGES_COL_4, message.getMessageDate());
        long result = db.insert(MESSAGES_TABLE_NAME, null, contentValues);
        db.close();
        return result != -1;
    }

    public Cursor getAllData(int USER_ID){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + MESSAGES_TABLE_NAME + " WHERE " + MESSAGES_COL_2
                + " = \'" + USER_ID + "\' OR " + MESSAGES_COL_3 + " = \'" + USER_ID + "\'", null);
    }

    /*public int countAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        return getAllData().getCount();
    }*/

    public void deleteMessage(int ID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + MESSAGES_TABLE_NAME + " WHERE " + MESSAGES_COL_0 + " = " + ID);
    }

    private void refreshDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + MESSAGES_TABLE_NAME);
        onCreate(db);
    }

}
