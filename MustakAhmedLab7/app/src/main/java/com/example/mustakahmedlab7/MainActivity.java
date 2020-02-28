package com.example.mustakahmedlab7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

    myService ms;
    boolean mBound = false;
    boolean sBound = false;
    Intent intent ;
    TextView letter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent = new Intent(this, myService.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


     public void onStart(View view) {
         // super.onStart();
        if(!mBound) {
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
            mBound = true;
        }
    }


     public void onStop(View view) {
         // super.onStop();
         if (mBound) {
             unbindService(connection);
             mBound = false;
         }
     }


    public void StartRng(View view ){
        if (!(sBound)){
        startService(intent);
      //  ms.isRunning = true;
            sBound = true;
       //     ms.isRunning = true;
        }
    }

    public void StopRng(View view) {
        if (sBound) {
            stopService(intent);
          //  ms.isRunning = false;
        sBound = false;
        //ms.isRunning = false;
        }
    }

    public void setLetter(View view){
        letter = findViewById(R.id.letter);
        if(mBound){
            char x = ms.getLetter();
            letter.setText(Character.toString(x));
        }
        else
        {
            letter.setText("service not bound");
        }
    }


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            myService.LocalBinder binder = (myService.LocalBinder) service;
            ms = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBound = false;
        }
    };



}

