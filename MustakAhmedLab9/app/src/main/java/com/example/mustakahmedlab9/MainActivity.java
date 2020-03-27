package com.example.mustakahmedlab9;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView callLog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //  logs.setMovementMethod(new ScrollingMovementMethod());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callLog = findViewById(R.id.callDetail);
        callLog.setMovementMethod(new ScrollingMovementMethod());

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG}, 20);
        String[] permList = {"android.permission.READ_CALL_LOG"};
        int reqCode = 20;

        int[] grantResults = {0};

        if (pemGrant()) {
            onRequestPermissionsResult(reqCode, permList, grantResults);
        }

        // callLog.setText(getCallLog());
        // callLog.setText(log);


    }

    public boolean pemGrant() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
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
        if (requestCode == 20) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callLog.setText(getCallLog());
            }
            else {
                callLog.setText("permission denied: nothing will show ");
            }
        }


    }

    public String getCallLog() {
        StringBuffer sb = new StringBuffer();
        ContentResolver cR = getContentResolver();



        Cursor cursor = cR.query(CallLog.Calls.CONTENT_URI, null, null, null, null);



            int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
            int date = cursor.getColumnIndex(CallLog.Calls.DATE);
            int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
            sb.append("Call Details : ");
            while (cursor.moveToNext()) {
                String phoneNum = cursor.getString(number);
                String callType = cursor.getString(type);
                String callDate = cursor.getString(date);
                Date callDayTime = new Date(Long.valueOf(callDate));
                String callDuration = cursor.getString(duration);
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
                sb.append("\nPhone Number: --- " + phoneNum + "\nCall Type: --- "
                        + dir + " \nCall Date: --- " + callDayTime +
                        " \nCall Duration in sec : --- " + callDuration);
                sb.append("\n-------------------------");
            }// end while
            cursor.close();

    return sb.toString();

    }// end get




}
