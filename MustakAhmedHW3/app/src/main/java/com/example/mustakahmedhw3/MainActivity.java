package com.example.mustakahmedhw3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ListView listView;
    contactDB cdb ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cdb = new contactDB(this);
        ArrayList  listOfContacts = cdb.getAllContacts();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listOfContacts);

        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int searchID = i  +1 ;

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id",searchID);
                Intent intent = new Intent(getApplicationContext(),displayContact.class);
                intent.putExtras(dataBundle);
                startActivity(intent);

            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.add_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.addContact){
            Bundle dataBundle = new Bundle();
            dataBundle.putInt("id",0);

            Intent intent = new Intent(getApplicationContext(),displayContact.class);
            intent.putExtras(dataBundle);
            startActivity(intent);

        }
        return  true;
    }

    public boolean onKeyDown(int keycode, KeyEvent event){
        if(keycode == KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode,event);
    }


}
