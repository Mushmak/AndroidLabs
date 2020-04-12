package com.example.mustakahmedlab11;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.regex.Matcher;


public class StudentProvide extends ContentProvider {

    studentDatabase sdb = null ;
    public static final String AUTHORITY = "com.example.mustakahmedlab11.provider";
    static final String URL = "content://" + AUTHORITY + "/students";
    static final Uri CONTENT_URI = Uri.parse(URL);


    private static HashMap<String, String> STUDENTS_PROJECTION_MAP;

    public static final int Student = 1;
    public static final int Grade = 2;

    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        MATCHER.addURI(AUTHORITY, "students",Student);
        MATCHER.addURI(AUTHORITY,"grades",Grade);
    }

    public StudentProvide() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        SQLiteDatabase db = sdb.getWritableDatabase();
        switch (MATCHER.match(uri)){
            case Student:
                count = db.delete("StudentTable", selection, selectionArgs);
                break;

            case Grade:
                String id = uri.getPathSegments().get(1);
                count = db.delete( "StudentTable", "id " + id + (!TextUtils.isEmpty(selection) ? "AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (MATCHER.match(uri)){
            /**
             * Get all student records
             */
            case Student:
                return "vnd.android.cursor.dir/vnd.example.students";
            /**
             * Get a particular student
             */
            case Grade:
                return "vnd.android.cursor.item/vnd.example.students";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
     SQLiteDatabase db = sdb.getWritableDatabase();
         long id = db.insert("StudentTable","",values);
      return uri;
    }


    @Override
    public boolean onCreate() {
      sdb = new studentDatabase(getContext());
        return  true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = sdb.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables("StudentTable");
        switch (MATCHER.match(uri)){
            case Student :
            builder.setProjectionMap(STUDENTS_PROJECTION_MAP);
            break;
            case Grade :
                builder.appendWhere("id = " + uri.getPathSegments().get(1));
            break;
            default:
        }
        Cursor c = builder.query(db, projection,selection,selectionArgs,null,null,sortOrder);
        c.setNotificationUri(getContext().getContentResolver(),uri);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = sdb.getWritableDatabase();
        int count = 0;
        switch (MATCHER.match(uri)) {
            case Student:
                count = db.update("StudentTable", values, selection, selectionArgs);
                break;

            case Grade:
                count = db.update("StudentTable", values,
                        "id = " + uri.getPathSegments().get(1) +
                                (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
