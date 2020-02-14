package com.example.mustakahmedlab2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    SeekBar PizzaSize;

    TextView Inches, Price ;
    CheckBox Anch, Pineapple, Garlic, Okra;
//    RadioButton Crisp, Thick, Soggy, AtRes, Pickup, Deliver;
//    RadioGroup Crust, toGo;

   // double Total ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PizzaSize = findViewById(R.id.seekBar);
        Inches = findViewById(R.id.Progress);

        Anch = findViewById(R.id.Anch);
        Pineapple = findViewById(R.id.Pineapple);
        Garlic = findViewById(R.id.Garlic);
        Okra = findViewById(R.id.Okra);





// https://stackoverflow.com/questions/9821614/getting-percentage-value-from-seek-bar


        PizzaSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {

            public void onStopTrackingTouch(SeekBar bar)
            {
                int value = bar.getProgress();
                Inches.setText(value+ "in");// the value of the seekBar progress

            }

            public void onStartTrackingTouch(SeekBar bar)
            {

            }

            public void onProgressChanged(SeekBar bar,
            int paramInt, boolean paramBoolean)
            {

                //Inches.setText("" + paramInt + "%"); // here in textView the percent will be shown
            }

        });// end seekbar change



    }// end on create





}// end main activity































