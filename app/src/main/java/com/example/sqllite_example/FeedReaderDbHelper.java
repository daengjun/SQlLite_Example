package com.example.sqllite_example;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;


public class FeedReaderDbHelper extends SQLiteOpenHelper {


//    안드로이드 SQLite execSQL()과 rawQuery()의 차이
//    SQLite execSQL()과 rawQuery()의 차이
//    void execSQL(String sql)

//- SELECT 명령을 제외한 모든 SQL 문장을 실행한다.ex) CREATE TABLE, DELETE, INSERT 등

// Cursor rawQuery(String sql, String[] selectionArgs)

//- SELECT 명령어를 사용하여 쿼리를 실행하려면 rawQuery()를 사용하면 된다.
//쿼리의 결과는 Cursor 객체로 반환된다.
//Cursor 객체는 쿼리에 의하여 생성된 행들을 가리킨다.
//Cursor는 DB에서 결과를 순회하고 데이터를 읽는 데 사용되는 표준적인 메커니즘이다.


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Contract.FeedEntry.TABLE_NAME + " (" +
                    Contract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    Contract.FeedEntry.COLUMN_NAME_TITLE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Contract.FeedEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long insertData(String title) {
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Contract.FeedEntry.COLUMN_NAME_TITLE, title);
//        values.put(Contract.FeedEntry.COLUMN_NAME_SUBTITLE, subtitle);

// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(Contract.FeedEntry.TABLE_NAME, null, values);

        return newRowId;

    }

    public Cursor getAll() {

        SQLiteDatabase db = this.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                Contract.FeedEntry.COLUMN_NAME_TITLE};

// Filter results WHERE "title" = 'My Title'
//        String selection = Contract.FeedEntry.COLUMN_NAME_TITLE + " = ?";
//        String[] selectionArgs = {"Title"};

// How you want the results sorted in the resulting Cursor
//        String sortOrder =
//                Contract.FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

        // query로 하는경우 projection 배열안에 컬럼값 들 반환
//        Cursor cursor = db.query(
//                Contract.FeedEntry.TABLE_NAME,   // The table to query
//                projection,             // The array of columns to return (pass null to get all)
//                null,              // The columns for the WHERE clause
//                null,          // The values for the WHERE clause
//                null,                   // don't group the rows
//                null, null);                  // don't filter by row groups);

        //rawQuery로 하는경우
        Cursor cursor = db.rawQuery("SELECT*FROM " + Contract.FeedEntry.TABLE_NAME, null);


        return cursor;
    }

    public int delete(String title) {
// Define 'where' part of query.
        String selection = Contract.FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = {title};
// Issue SQL statement.
        int deletedRows = this.getWritableDatabase().delete(Contract.FeedEntry.TABLE_NAME, selection, selectionArgs);
        return deletedRows;
    }


}
