package com.example.mustakahmedlab7;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Random;

public class myService extends Service{


    Boolean isRunning = false;
    String tag = "myService";
    char letter ;
    private final IBinder binder = new LocalBinder();


    @Override
    public IBinder onBind(Intent intent) {
        Log.i(tag, " Service Bound");
        return binder;
    }

    @Override
    public void onCreate() {
        Log.i(tag, " Service Started");
        isRunning = true;
    }

    @Override
    public boolean onUnbind(Intent intent){
        Log.i(tag, "Service Unbound");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.i(tag, "Service Stopped");
        isRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Thread thread = new Thread() {
            public void run (){
                Log.i(tag, " New Thread Create: " + getId());
                while (isRunning) {
                    char x = randomLetter();
                    letter = x;
                    Log.i("Letter", " " + x);
                    try {
                        sleep(1000);
                    }
                    catch (InterruptedException e){
                        Log.i(tag, "broke thread");
                    }
                }
            }




        }; // end thread
        thread.start();
        return START_STICKY;
        //    return super.onStartCommand(intent, flags,startId);
    } // end on start command

    public char randomLetter(){
        Random rng = new Random();
        int rngLetter = rng.nextInt(90-65)+65 ;
        char c = (char) rngLetter;
        return c;
    }

    public class LocalBinder extends Binder{
        myService getService(){
            return myService.this;
        }
    }
    public char getLetter(){
        return this.letter;
    }
}


