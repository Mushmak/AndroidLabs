package com.example.mustakahmedhw3;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class displayContact  extends AppCompatActivity {
    public contactDB cdb;
    TextView nameF, phoneF, emailF,streetF,zipF;
    int updateID=0;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact_detail);
        nameF = (TextView) findViewById(R.id.nameField);
        phoneF = (TextView) findViewById(R.id.PhoneField);
        emailF = (TextView) findViewById(R.id.EmailField);
        streetF = (TextView) findViewById(R.id.streetField);
        zipF = (TextView) findViewById(R.id.zipField);
        saveButton = findViewById(R.id.SaveBtn);

        cdb = new contactDB(this);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            int value = extras.getInt("id");
            if (value>0){
                updateID = value;
                getContactAsync getAsync = new getContactAsync();
                getAsync.execute(value);
            }
        }



    }

    public void postGet(Cursor cr){
        cr.moveToFirst();

        String name = cr.getString(cr.getColumnIndex(cdb.COL_NAME));
        String phone = cr.getString(cr.getColumnIndex(cdb.COL_PHONE));
        String email = cr.getString(cr.getColumnIndex(cdb.COL_EMAIL));
        String street = cr.getString(cr.getColumnIndex(cdb.COL_STREET));
        String zip = cr.getString(cr.getColumnIndex(cdb.COL_ZIP));

        if(!cr.isClosed()){
            cr.close();
        }
        saveButton.setVisibility(View.INVISIBLE);

        nameF.setText((CharSequence)name);
        nameF.setFocusable(false);
        nameF.setClickable(false);

        phoneF.setText((CharSequence)phone);
        phoneF.setFocusable(false);
        phoneF.setClickable(false);

        emailF.setText((CharSequence)email);
        emailF.setFocusable(false);
        emailF.setClickable(false);

        streetF.setText((CharSequence)street);
        streetF.setFocusable(false);
        streetF.setClickable(false);

        zipF.setText((CharSequence)zip);
        zipF.setFocusable(false);
        zipF.setClickable(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Bundle extras = getIntent().getExtras();
        if(extras!= null){
            int value = extras.getInt("id");
            if (value > 0){
                getMenuInflater().inflate(R.menu.display_contact,menu);
            }
            else {
                getMenuInflater().inflate(R.menu.add_menu,menu);
            }
        }
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.Edit){
            saveButton.setVisibility(View.VISIBLE);
            nameF.setEnabled(true);
            nameF.setFocusableInTouchMode(true);
            nameF.setClickable(true);

            phoneF.setEnabled(true);
            phoneF.setFocusableInTouchMode(true);
            phoneF.setClickable(true);

            emailF.setEnabled(true);
            emailF.setFocusableInTouchMode(true);
            emailF.setClickable(true);

            streetF.setEnabled(true);
            streetF.setFocusableInTouchMode(true);
            streetF.setClickable(true);

            zipF.setEnabled(true);
            zipF.setFocusableInTouchMode(true);
            zipF.setClickable(true);
        }

        if(item.getItemId()==R.id.delete){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("DELETE!");
            dialog.setMessage("DO YOU WANT TO DELETE THIS ");
            dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    deleteContactAsync deleteContactAsync = new deleteContactAsync();
                    deleteContactAsync.execute();
                }
            }); // come back and add delete listener
            dialog.setNegativeButton("NO",null);
            AlertDialog di = dialog.create();
            di.show();
        }




        return  true;
    }

    public void run(View view){
        Bundle extras = getIntent().getExtras();
        if(extras!= null){
            int value = extras.getInt("id");
            if (value > 0){
                if(nameF.getText().toString().isEmpty() || phoneF.getText().toString().isEmpty() ||
                emailF.getText().toString().isEmpty() || streetF.getText().toString().isEmpty() || zipF.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"No fields can be empty",Toast.LENGTH_SHORT).show();
                }
                else{
                 editContactAsync eAsync = new editContactAsync();
                 eAsync.execute(nameF.getText().toString(),phoneF.getText().toString(),emailF.getText().toString(),streetF.getText().toString(),zipF.getText().toString());
                }

            }
            else {
                if(nameF.getText().toString().isEmpty() || phoneF.getText().toString().isEmpty() ||
                        emailF.getText().toString().isEmpty() || streetF.getText().toString().isEmpty() || zipF.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"No fields can be empty",Toast.LENGTH_SHORT).show();
                }
                else{
                    insertContactAsync iAsync = new insertContactAsync();
                    iAsync.execute(nameF.getText().toString(),phoneF.getText().toString(),emailF.getText().toString(),streetF.getText().toString(),zipF.getText().toString());
                }




            }
        }


    }// end run

    private class getContactAsync extends AsyncTask<Integer,String,String>{

        Cursor cr;

        @Override
        protected String doInBackground(Integer... strings) {
            cr = cdb.getData(strings[0]);
            return "";
        }
        @Override
        protected void onPostExecute(String rslt){
          postGet(cr);
        }
    }



    private class insertContactAsync extends AsyncTask<String,String,String>{

        Cursor cr;

        @Override
        protected String doInBackground(String... strings) {
            cdb.addContact(strings[0],strings[1],strings[2],strings[3],strings[4]);
            return "";
        }
        @Override
        protected void onPostExecute(String rslt){
            Toast.makeText((getApplicationContext()),"Inserted",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
    }

    private class editContactAsync extends AsyncTask<String,String,String>{

        Cursor cr;

        @Override
        protected String doInBackground(String... strings) {
            cdb.updateConstact(updateID,strings[0],strings[1],strings[2],strings[3],strings[4]);
            return "";
        }
        @Override
        protected void onPostExecute(String rslt){
            Toast.makeText((getApplicationContext()),"updated",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
    }

    private class deleteContactAsync extends AsyncTask<Integer,String,String>{

        Cursor cr;

        @Override
        protected String doInBackground(Integer... strings) {
            cdb.deleteContact(updateID);
            return "";
        }
        @Override
        protected void onPostExecute(String rslt){
            Toast.makeText((getApplicationContext()),"Deleted",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
    }




}// end
