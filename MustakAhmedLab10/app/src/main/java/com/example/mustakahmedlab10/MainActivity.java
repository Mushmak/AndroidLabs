package com.example.mustakahmedlab10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.util.zip.Inflater;


public class MainActivity extends AppCompatActivity {

    LayoutInflater layoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG}, 20);
        }

        String[] permList = {"android.permission.READ_CALL_LOG","android.permission.WRITE_CALL_LOG"};
        int reqCode = 20;

        int[] grantResults = {0,1};
//        getCallLog(findViewById(R.id.button));
        if(permGrant()){
            onRequestPermissionsResult(20,permList,grantResults);
        }



    }// end on create

    public boolean permGrant(){
        if (Build.VERSION.SDK_INT >= 23) {
            if ((checkSelfPermission(Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED)&&(checkSelfPermission(Manifest.permission.WRITE_CALL_LOG) == PackageManager.PERMISSION_GRANTED) ) {
                Log.i("test", "perm given ");
                return true;
            } else {
                Log.i("test", "perm not given ");
                return false;
            }
        } else {
            Log.i("test", "perm given cause < 23  ");
            return true;
            //onRequestPermissionsResult(reqCode,permList,grantResults);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 20){
          if ((grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) && ( grantResults[1] == PackageManager.PERMISSION_GRANTED)){
              getCallLog(findViewById(R.id.button));
          }
        }
        else
        {
            Toast.makeText(this, "no perms", Toast.LENGTH_SHORT).show();
        }
    }

    public void getCallLog(View view) {


        ContentResolver cr = getContentResolver();
        EditText searchBar = findViewById(R.id.searchBar);
      //  Uri allCalls = Uri.parse("CallLog.Calls.CONTENT_URI");
        String searchTerm = searchBar.getText().toString();
        String sqlSearchTerm = CallLog.Calls.NUMBER + " LIKE '%" + searchTerm + "%'";
        StringBuffer sb = new StringBuffer(sqlSearchTerm);


        CheckBox missing, outgoing, incoming, rejected;
        missing = findViewById(R.id.missingCk);
        outgoing = findViewById(R.id.outgoingCk);
        incoming = findViewById(R.id.incomingCk);
        rejected = findViewById(R.id.rejectCk);

        if (!missing.isChecked()) {
            sb.append(" AND " + CallLog.Calls.TYPE + "!= '" + CallLog.Calls.MISSED_TYPE + "'");

        }
        if (!outgoing.isChecked()) {
            sb.append(" AND " + CallLog.Calls.TYPE + "!= '" + CallLog.Calls.OUTGOING_TYPE + "'");
        }
        if (!incoming.isChecked()) {
            sb.append(" AND " + CallLog.Calls.TYPE + "!= '" + CallLog.Calls.INCOMING_TYPE + "'");
        }
        if (!rejected.isChecked()) {
            sb.append(" AND " + CallLog.Calls.TYPE + "!= '" + CallLog.Calls.REJECTED_TYPE + "'");
        }

        Cursor cursor = cr.query(CallLog.Calls.CONTENT_URI, null, sb.toString(), null, null, null);
        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
        int id = cursor.getColumnIndex(CallLog.Calls._ID);

        TableLayout tblLayout = findViewById(R.id.tblLayout1);
        int x = tblLayout.getChildCount();
        if (x > 3)
        {
            tblLayout.removeViews(3,x-3);
        }
        layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        while(cursor.moveToNext()){
            View row = layoutInflater.inflate(R.layout.rows,null,false);
            SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");

            String phoneNum = cursor.getString(number);
            String callType = cursor.getString(type);
            String callDate = cursor.getString(date);
            String callDayTime = formatDate.format(new Date(Long.valueOf(callDate)));
            //String callDayTime = new Date(Long.valueOf(callDate)).toString();
            String callDuration = cursor.getString(duration);
            final String itemId = cursor.getString(id);

            String dir = null;
            int dirCode = Integer.parseInt(callType);
            switch (dirCode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "Outgoing";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    dir = "Incoming";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    dir = "Missed";
                    break;
                case CallLog.Calls.REJECTED_TYPE:
                    dir = "Rejected";
                    break;
            }
            TextView phone = row.findViewById(R.id.text1);
            phone.setText(phoneNum);

            TextView cdate = row.findViewById(R.id.text2);
            cdate.setText(callDayTime);

            TextView cType = row.findViewById(R.id.text3);
            cType.setText(dir);

            TextView dur = row.findViewById(R.id.text4);
            dur.setText(callDuration);

            ImageButton delteBtn = row.findViewById(R.id.delButton);


            delteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteItem(itemId);
                }
            });
            tblLayout.addView(row);

        }// end while

        cursor.close();

}// end get call log


    public void deleteItem(String id){
      //  Uri allCalls = (CallLog.Calls.CONTENT_URI);
        String qString = CallLog.Calls._ID +" = " + " '" +id+ "'";
        getContentResolver().delete(CallLog.Calls.CONTENT_URI,qString,null);
        getCallLog(findViewById(R.id.button));
    }


}// end main activity
