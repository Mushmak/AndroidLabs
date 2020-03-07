package com.example.mustakahmedlab8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    myService ms = new myService();
    Intent intent;
    EditText displayText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this,myService.class);
        displayText = findViewById(R.id.display);

        BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
              //  try {
                  char x = intent.getCharExtra("data", 'A');
                  String y = Character.toString(x);
                  displayText.setText(y);
               // Log.i(" test","see letter from broadcast"+  x );
         //       } catch(Exception x)
             //   {
            //        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
             //   }
            }
        };

            IntentFilter filter = new IntentFilter();
            filter.addAction("com.example.broadcast.RANDOM_BROADCAST");
            registerReceiver(br, filter);

    }

    public void startRNG(View view){
        if(!(ms.isRunning)){
            startService(intent);
            ms.isRunning = true;
        }
    }

    public void stopRNG(View view){
        if(ms.isRunning){
            stopService(intent);
            ms.isRunning = false;
            displayText.setText("");
        }
    }





}
