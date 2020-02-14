package com.example.mustakahmedlab3;

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
    RadioButton Crisp, Thick, Soggy, AtRes, Pickup, Deliver;
    RadioGroup Crust, toGo;

     double Total, size, area ;

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

        Price = findViewById(R.id.price);

        // crust variables
        Crust =findViewById(R.id.crustType);

        Thick = findViewById(R.id.Thicky);
        Soggy = findViewById(R.id.soggy);
        Crisp = findViewById(R.id.Crispy);

        // to go choices
        toGo = findViewById(R.id.toGO);

        AtRes = findViewById(R.id.Dinein);
        Pickup = findViewById(R.id.Pickup);
        Deliver = findViewById(R.id.Delivery);



        PizzaSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {

            public void onStopTrackingTouch(SeekBar bar)
            {
                int value = bar.getProgress();
                Inches.setText(value+ "in");// the value of the seekBar progress
                makePrice();

            }

            public void onStartTrackingTouch(SeekBar bar)
            {
                int value = bar.getProgress();
                Inches.setText(value+ "in");// the value of the seekBar progress
                makePrice();
            }

            public void onProgressChanged(SeekBar bar,
                                          int paramInt, boolean paramBoolean)
            {
                int value = bar.getProgress();
                Inches.setText(value+ "in");// the value of the seekBar progress
                makePrice();
                //Inches.setText("" + paramInt + "%"); // here in textView the percent will be shown
            }

        });// end seekbar change

        Crust.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                makePrice();

            }
        }); // curst changes

        toGo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                makePrice();

            }
        });

        Anch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                makePrice();

            }
        });

        Pineapple.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                makePrice();

            }
        });

        Okra.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                makePrice();

            }
        });

        Garlic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                makePrice();

            }
        });
    }// end on create

    public void makePrice()
    {
        double topingCount = 0.0;
        size = PizzaSize.getProgress();
        area = (Math.PI)*(Math.pow((size/2), 2 ));


        Total = 0;

        // check what crust
        if(Thick.isChecked()){
            Total+=2.50;
        }
        if(Soggy.isChecked()){
            Total+=5.00;
        }
        if(Crisp.isChecked()){
            Total+=0.0;
        }

//        // check topings count
        if (Anch.isChecked())
        {
            topingCount+=1;
        }
        if (Pineapple.isChecked())
        {
            topingCount+=1;
        }
        if (Garlic.isChecked())
        {
            topingCount+=1;
        }
        if (Okra.isChecked())
        {
            topingCount+=1;
        }
//          check togo or no

        if(Deliver.isChecked())
        {
            Total+=3.00;
        }
        if(AtRes.isChecked()||Pickup.isChecked())
        {
            Total +=0.0;
        }
        // make price on size
        double priceBasedOnSize = area * 0.05;

        // make toping price

        double topPrice = topingCount*0.05*area;

        Total += priceBasedOnSize;
        Total += topPrice;

        Price.setText(String.format("$"+"%.2f",Total));

    } // end make price
}// end main activity
