package com.example.mustakahmed_lab1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText FavoriteClass;
    Button Click;
    TextView fun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fun = findViewById(R.id.isfun2);
        FavoriteClass = findViewById(R.id.userInfo1);
        Click = findViewById(R.id.button);



    }
    public void isFun(View v){
         fun.setText(FavoriteClass.getText().toString()+" is fun! ");

    }
}
