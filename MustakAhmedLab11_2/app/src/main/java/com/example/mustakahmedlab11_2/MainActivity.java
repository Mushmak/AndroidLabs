package com.example.mustakahmedlab11_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String AUTHORITY = "com.example.mustakahmedlab11.provider";
    static final String URL = "content://" + AUTHORITY + "/students";
    static final Uri CONTENT_URI = Uri.parse(URL);
    TextView display;
    LayoutInflater layoutInflater;
    TableLayout table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void listStudentsButton(View view){
        table = findViewById(R.id.table);
        int x = table.getChildCount();
        if (x > 0)
        {
            table.removeAllViews();
        }
        listStudents();

    }

    @Override
    protected void onResume(){
        super.onResume();
        listStudents();
    }
    public void listStudents(){
        ContentResolver cr = getContentResolver();
       Cursor cursor =  cr.query(CONTENT_URI,null,null,null,null);
         table = findViewById(R.id.table);
        layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);


        StringBuilder sb = new StringBuilder("");
        if(cursor!=null & cursor.getCount()>0){
            while(cursor.moveToNext()){
                View row = layoutInflater.inflate(R.layout.item_row,null);
                String x = cursor.getString(1);
                String y = cursor.getString(2);
                String big = " " + x + " : " + y ;
                display = row.findViewById(R.id.nameText);
                display.setText(big);
                table.addView(row);
            }
        }
        cursor.close();

    }
}
