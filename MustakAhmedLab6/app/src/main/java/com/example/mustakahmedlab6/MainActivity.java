package com.example.mustakahmedlab6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    myService ms = new myService();
    Intent intent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent = new Intent(this, myService.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void StartRng(View view ){
        //   if (!(ms.isRunning)){
        // startService (new Intent(MainActivity.this, myService.class));
        startService(intent);
        ms.isRunning = true;
        //   }

    }

    public void StopRng(View view){
        //  if(ms.isRunning){
        stopService(intent);
        // stopService(new Intent(MainActivity.this, myService.class));
        ms.isRunning =false;


    }



}



