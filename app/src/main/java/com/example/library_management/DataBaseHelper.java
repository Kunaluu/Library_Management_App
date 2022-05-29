package com.example.library_management;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String BOOK_TABLE = "BOOK_TABLE";
    public static final String COLUMN_BOOK_NAME = "BOOK_NAME";
    public static final String COLUMN_BOOK_PAGES  = "BOOK_PAGES";
    public static final String COLUMN_ISSUED_BOOK = "ISSUED_BOOK";
    public static final String COLUMN_ID = "COLUMN_ID";

    public DataBaseHelper(@Nullable Context context) {
        super(context,"Book.db", null, 1);
    }

    //this is called the first time you try to access a database. There should be code in here to create a new database.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + BOOK_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_BOOK_NAME + " TEXT, " + COLUMN_BOOK_PAGES + " INT, " + COLUMN_ISSUED_BOOK + " BOOL)";

        db.execSQL(createTableStatement);
    }
    //this is called whenever the version number of the database changes. It prevents previous users apps from breaking when you change the database design.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(BookManagement bookManagement){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_BOOK_NAME, bookManagement.getName() );
        cv.put(COLUMN_BOOK_PAGES, bookManagement.getPage());
        cv.put(COLUMN_ISSUED_BOOK, bookManagement.isActive());

        long insert = db.insert(BOOK_TABLE, null, cv);
        if (insert == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean deleteOne(BookManagement bookManagement){
        //find customer model in database, if it is found, delete it and return true
        //if it is not found return false
        //using DAO(Data access object style)

        SQLiteDatabase db = this.getWritableDatabase();
        String querystring = "DELETE FROM " + BOOK_TABLE + " WHERE " + COLUMN_ID + "=" + bookManagement.getId();

        Cursor cursor = db.rawQuery(querystring, null);
        if(cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }
    }
    public List<BookManagement> getEveryOne(){
        List<BookManagement> returnList = new ArrayList<>();
        //get data from the database
        String queryString = "SELECT * FROM " + BOOK_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            //loop through the cursor(result set) and create new customer objects.Put them in the return list
            do{
                int customerID = cursor.getInt(0);
                String customerName = cursor.getString(1);
                int customerAge = cursor.getInt(2);
                boolean customerActive = cursor.getInt(3) == 1 ? true: false; //used turnary operator

                BookManagement newBook = new BookManagement(customerID, customerName, customerAge, customerActive);
                returnList.add(newBook);
            }while(cursor.moveToNext());
        }
        else{
            //failure, so don't add anything to the list
        }
        //closing both cursor and database when done
        cursor.close();
        db.close();
        return returnList;
    }
}
