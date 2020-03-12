package com.example.mustakahmedhw2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Trace;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    SharedPreferences InfoSave;
    EditText searchBar, tagSlot;
    TableLayout tL;
    String SAVED_INFO_SEARCH = "Search terms";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InfoSave = getSharedPreferences(SAVED_INFO_SEARCH, MODE_PRIVATE);
        tL = findViewById(R.id.tableLayout);
        searchBar = findViewById(R.id.search_bar);
        tagSlot = findViewById(R.id.tagSlot);

        refresh("",true);

    }

    public void onClick(final View view){
        switch(view.getId()){
            case R.id.saveBtn: // if you click the save button , it checks to see if the search and tag have valid input
            if(searchBar.getText().length() > 0 && tagSlot.getText().length() >0){
                String searchTerm = searchBar.getText().toString();
                String tag = tagSlot.getText().toString();
                boolean tagSaved = InfoSave.contains(tag);
                SharedPreferences.Editor Editor = InfoSave.edit();
                Editor.putString(tag,searchTerm);
                Editor.apply();
                if(!tagSaved){
                    refresh(tag, false);
                }
                else{
                   // Toast.makeText(getApplicationContext(),"Tag Already Exists", Toast.LENGTH_SHORT).show();
                }
                searchBar.setText("");
                tagSlot.setText("");

            }// if there arent valid inputs its prints alert box saying missing info
            else{
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Missing Information");
                dialog.setMessage("Search Term and Tag cannot be left blank");
                dialog.setPositiveButton("OK",null);
                AlertDialog missDialog = dialog.create();
                missDialog.show();
            }
            break;
            case R.id.clear:
                AlertDialog.Builder alertbox = new AlertDialog.Builder(MainActivity.this);
                alertbox.setTitle("Are you sure?");
                alertbox.setMessage("This will delete everything :) ");
                alertbox.setCancelable(true);
                alertbox.setNegativeButton("NO" ,null);
                alertbox.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tL.removeAllViews();
                        SharedPreferences.Editor editor = InfoSave.edit();
                        editor.clear();
                        editor.apply();
                    }
                });
                AlertDialog confirm = alertbox.create();
                confirm.show();
                break;

            case R.id.newTagBTN:
                String tag1 = ((Button)view).getText().toString();
                String Query = InfoSave.getString(tag1,"");
                String url = "https://twitter.com/search?q=" +Query;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                break;

            case R.id.newEditBTN:
                TableRow row = (TableRow) view.getParent();
                Button tagBtn = row.findViewById(R.id.newTagBTN);
                final String tag = tagBtn.getText().toString();
                String q = InfoSave.getString(tag,"");
                searchBar.setText(q);
                tagSlot.setText(tag);
            break;

            case R.id.newDltBTN:
                TableRow row2 = (TableRow) view.getParent();
                Button tagBtn2 = row2.findViewById(R.id.newTagBTN);
                String tagName = tagBtn2.getText().toString();
                AlertDialog.Builder albox = new AlertDialog.Builder(MainActivity.this);

                albox.setTitle("Delete "+ tagName +"?");
                albox.setMessage("This will delete " +tagName);
                albox.setCancelable(true);
                albox.setNegativeButton("NO",null);
                albox.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TableRow row = (TableRow) view.getParent();
                        tL.removeView(row);
                        SharedPreferences.Editor editor = InfoSave.edit();
                        editor.clear();
                        editor.apply();
                    }
                });
                AlertDialog confirmA = albox.create();
                confirmA.show();
        }// end switch


    }// end on click

    // given by prof in shared pref example

    void refresh(String tag, boolean applytoAll){
        // get the map of tags to queries
        Map<String,String> queryMap = (Map<String, String>) InfoSave.getAll();
        // get the keys from the map
        Set<String> tagSet = queryMap.keySet();
        // convert the set to an array
        String[] tags = tagSet.toArray(new String[0]);
        // sort the tags
        Arrays.sort(tags,String.CASE_INSENSITIVE_ORDER);
        // determine where the new tag should go
        int index = Arrays.binarySearch(tags, tag);
        if(applytoAll){
            for(int i = 0; i < tags.length; i++ ) {
                addTagGui(tags[i], i);
            }
        }
        else
            addTagGui(tag, index);
    }
  // provided in the shared pref example
    // this makes the second xml appear to the first
    void addTagGui(String tag,int index){
        // create a new row by inflating the layout file
        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = li.inflate(R.layout.new_tag, null);

        // get the tag button
        Button tagBTN = (Button)row.findViewById(R.id.newTagBTN);
        //tagBTN.setText(tag + " " + index);
        tagBTN.setText(tag);


        // set the edit listener on the edit button
        Button editBTN = (Button)row.findViewById(R.id.newEditBTN);

        // set the delete button listener on the delete
        Button deleteBtn = (Button)row.findViewById(R.id.newDltBTN);


         tL.addView(row,index);
    }








}
