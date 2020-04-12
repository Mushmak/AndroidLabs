package com.example.mustakahmedlab11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText studentName, studentGrade;
    studentDatabase sdb ;
    LayoutInflater layoutInflater;
    TableLayout tblLayout;
   // TableRow row;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sdb = new studentDatabase(this);

    } // end on create

    public void generateStudentTable(View view ){
        tblLayout = (TableLayout) findViewById(R.id.maintable);
        layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        int x = tblLayout.getChildCount();
        if (x > 3)
        {
            tblLayout.removeViews(3,x-3);
        }

        ArrayList<String> sData = (ArrayList<String>) sdb.getData();
        Log.i("test", "array List: " +sData.size());

        for( int i = 0; i < sData.size(); i++)
        {
            View row = layoutInflater.inflate(R.layout.item_row,null,false);
            TextView display = row.findViewById(R.id.nameText);
            display.setText(sData.get(i));
            Log.i("test", "array List: " +sData.get(i));

            tblLayout.addView(row);
        }






    }//end gen table


    public void addNewStudent(View view){
        studentGrade = (EditText) findViewById(R.id.gradeEdit);
        studentName = (EditText) findViewById(R.id.nameEdit);

        String name = studentName.getText().toString();
        String grade = studentGrade.getText().toString();

        if ((name.equals("")) ||(grade.equals("") )) {
            Toast.makeText(MainActivity.this,"fill both fields",Toast.LENGTH_SHORT).show();

        }
        else {
            sdb.addStudent(name,grade);
          generateStudentTable(findViewById(R.id.listButton));
        }

    }// end add new student





}// end main
