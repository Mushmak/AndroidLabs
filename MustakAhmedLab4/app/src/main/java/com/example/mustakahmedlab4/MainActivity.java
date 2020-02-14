package com.example.mustakahmedlab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    EditText userName, password;
    Button login;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.login);
        userName = findViewById(R.id.userField);
        password = findViewById(R.id.passField);


    } // end on create


    public void login(View view)
    {
        String checkUser = "cs4322";
        String checkPass = "123456";
        String userInfo = userName.getText().toString();
        String passInfo = password.getText().toString();

        if(userInfo.equals(checkUser) && passInfo.equals(checkPass)) {
            Intent intent = new Intent(this, DisplayLoginActivity.class);
            intent.putExtra("username", userInfo);
            startActivity(intent);

        } // end if
        else
        {
            Intent intent = new Intent(this, DisplayFailedLogin.class);
            startActivity(intent);
        }// end else

    }// end login

} // end main activity
