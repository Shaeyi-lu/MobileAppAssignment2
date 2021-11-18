package com.example.mobileassignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    Context context;
    private static final String TableName="location";
    private static final String Id="Id";
    private static final String Address="Address";
    private static final String Latitude="Latitude";
    private static final String Longitude="Longitude";


    public DBHelper(@Nullable Context context) {
        super(context, TableName, null, 1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        String lookup = "CREATE TABLE " + TableName +" (" + Id + " TEXT PRIMARY KEY AUTOINCREMENT, "+
                Address + " TEXT, " +
                Latitude + " REAL, " +
                Longitude + " REAL);";
        DB.execSQL(lookup);
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("DROP TABLE IF EXISTS " + TableName);
        onCreate(DB);
    }

    void addData(String id,String address, Double latitude, Double longitude){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Address, address);
        contentValues.put(Latitude, latitude);
        contentValues.put(Longitude, longitude);

        long result = DB.insert(TableName, null, contentValues);
        if(result == -1){
            Toast.makeText(context, "Data Not Added", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Data Added Succssfully", Toast.LENGTH_SHORT).show();
        }

    }

    public Boolean updateData(String id ,String address, Double latitude, Double longitude){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Address, address);
        contentValues.put(Latitude, latitude);
        contentValues.put(Longitude, longitude);

        Cursor cursor = DB.rawQuery("SELECT * FROM "+ TableName + " WHERE " + Id + " = ?", new String[]{id});
        if(cursor.getCount()>0) {
            long result = DB.update(TableName, contentValues, Id + "=?", new String[]{id});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }
        else{
            return false;
        }
    }

    public Boolean deleteData(String id){
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("SELECT * FROM "+ TableName + " WHERE " + Id + " = ?", new String[]{id});
        if(cursor.getCount()>0) {
            long result = DB.delete(TableName,  Id +"=?", new String[]{id});
            if (result == -1) {
                return false;
            } else {

                return true;
            }
        }
        else{
            return false;
        }
    }

    public Cursor getData(){
        String query = "SELECT * FROM " + TableName;
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor=null;
        if(database!=null){
            cursor = database.rawQuery(query, null);
        }
        return cursor;

    }



}
