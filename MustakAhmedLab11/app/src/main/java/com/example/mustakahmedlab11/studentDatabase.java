package com.example.mustakahmedlab11;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class studentDatabase extends SQLiteOpenHelper {
        private static final int DATABASE_VERSION =2;
        private static final String DATABASE_NAME ="StudentDB";
        public static final String DATABASE_TABLE = "StudentTable";

        public static final String KEY_ID = "id";
        public static final String KEY_STUDENT ="student";
        public   static final String KEY_GRADE = "grade";
    String x, y, big ;

    studentDatabase(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + DATABASE_TABLE +" ("+
                KEY_ID+" INTEGER PRIMARY KEY,"+
                KEY_STUDENT+" TEXT,"+
                KEY_GRADE+" TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if (i >= i1)
            return;
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }

    public long addStudent(String student, String grade){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("student",student);
        c.put("grade",grade);
        long id = db.insert("StudentTable",null,c);
        db.close();
        return id;

    }
    public List getData(){
           ArrayList<String> data = new ArrayList<>();
           SQLiteDatabase db = this.getWritableDatabase();
           String query = "SELECT * FROM " + DATABASE_TABLE+ " ORDER BY "+ KEY_ID + " DESC";
           Cursor cursor = db.rawQuery(query,null);
           cursor.moveToFirst();
           do  {
               x = cursor.getString(cursor.getColumnIndex(KEY_STUDENT));
               y = cursor.getString(cursor.getColumnIndex(KEY_GRADE));
               big = " " + x + " : " + y ;
               data.add(big);
           } while ((cursor.moveToNext()));

            return data ;
       }


}
