package com.example.mustakahmedhw3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class contactDB extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "contactData";
    public static final String TABLE_NAME = "contacts";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_PHONE = "phone";
    public static final String COL_EMAIL = "email";
    public static final String COL_STREET = "street";
    public static final String COL_ZIP = "zip";

    contactDB(Context context){
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
     String query = "CREATE TABLE " + TABLE_NAME + " ("+
             COL_ID + " INTEGER PRIMARY KEY," +
             COL_NAME +" TEXT," +
             COL_PHONE +" TEXT," +
             COL_EMAIL + " TEXT," +
             COL_STREET + " TEXT," +
             COL_ZIP + " TEXT)" ;

     db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if(i >= i1){
            return;
        }
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public void addContact(String name, String phone,String email, String street, String zip){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(COL_NAME,name);
        c.put(COL_PHONE,phone);
        c.put(COL_EMAIL,email);
        c.put(COL_STREET,street);
        c.put(COL_ZIP,zip);
        db.insert(TABLE_NAME, null,c);

    }

    public Cursor getData(int id){
         SQLiteDatabase db = this.getReadableDatabase();
         Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME +" WHERE " + COL_ID +"="+ id , null );
         return cursor;
    }

    public void deleteContact(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,"id = ?",new String[] { Integer.toString(id)});
    }

    public void updateConstact(Integer id,String name, String phone,String email,String street,String zip){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(COL_NAME,name);
        c.put(COL_PHONE,phone);
        c.put(COL_EMAIL,email);
        c.put(COL_STREET,street);
        c.put(COL_ZIP,zip);
        db.update(TABLE_NAME, c,"id = ?", new String[] {Integer.toString(id)});
    }

    public ArrayList<Contact> getAllContacts(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Contact> allContacts = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME ;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                Contact contact = new Contact();
                contact.name = cursor.getString(cursor.getColumnIndex(COL_NAME));
                contact.id = cursor.getInt(cursor.getColumnIndex(COL_ID));
                allContacts.add(contact);

            }while(cursor.moveToNext());
        }
        return allContacts;
    }



}
