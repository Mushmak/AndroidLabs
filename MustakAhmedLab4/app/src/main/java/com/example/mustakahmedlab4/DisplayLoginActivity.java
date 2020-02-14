package com.example.mustakahmedlab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;



public class DisplayLoginActivity extends AppCompatActivity {

    TextView welcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_login);

        welcome = findViewById(R.id.welcome);

        Intent intent = getIntent();
        String message = intent.getStringExtra("username");
        welcome.setText(message +" Welcome to my webpage!");



     }
}
