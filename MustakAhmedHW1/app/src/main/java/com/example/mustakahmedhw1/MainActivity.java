package com.example.mustakahmedhw1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    EditText iniBill;
    TextView tenPerTip, fifPerTip,twenPerTip, tenPerTot, fifPerTot,twenPerTot, customTip, customTot , displayProgress ;
    SeekBar  customTipSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniBill = findViewById(R.id.iniBill);

        tenPerTip = findViewById(R.id.tip10);
        fifPerTip = findViewById(R.id.tip15);
        twenPerTip = findViewById(R.id.tip20);

        tenPerTot = findViewById(R.id.totalTen);
        fifPerTot = findViewById(R.id.totalfif);
        twenPerTot = findViewById(R.id.totalTwenty);

        customTip = findViewById(R.id.displayCustomTip);
        customTot = findViewById(R.id.displayCustomTotal);

        customTipSlider = findViewById(R.id.slider);
        displayProgress = findViewById(R.id.displayProgress);




        customTipSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int value = seekBar.getProgress();
                displayProgress.setText(value + "%");

                double tip = ((double) customTipSlider.getProgress()/100) + 1.00;

                try {

                    double bill = Double.parseDouble(iniBill.getText().toString());
                    double customTipValue = calcTip(bill, tip);
                    double customTotValue = calcTotal(bill, tip);

                    customTip.setText(String.format("%.2f",customTipValue));
                    customTot.setText(String.format("%.2f",customTotValue));


                }

                catch(NumberFormatException ex)

                {
                    tenPerTip.setText("0.00");
                    fifPerTip.setText("0.00");
                    twenPerTip.setText("0.00");

                    tenPerTot.setText("0.00");
                    fifPerTot.setText("0.00");
                    twenPerTot.setText("0.00");

                    customTip.setText("0.00");
                    customTot.setText("0.00");

                    return;
                }




            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                int value = seekBar.getProgress();
                displayProgress.setText(value + "%");

                try {
                    double tip = ((double) customTipSlider.getProgress()/100) + 1.00;
                    double bill = Double.parseDouble(iniBill.getText().toString());

                    double customTipValue = calcTip(bill, tip);
                    double customTotValue = calcTotal(bill, tip);

                    customTip.setText(String.format("%.1f",customTipValue));
                    customTot.setText(String.format("%.2f",customTotValue));


                }

                catch(NumberFormatException ex)
                {
                    tenPerTip.setText("0.00");
                    fifPerTip.setText("0.00");
                    twenPerTip.setText("0.00");

                    tenPerTot.setText("0.00");
                    fifPerTot.setText("0.00");
                    twenPerTot.setText("0.00");

                    customTip.setText("0.00");
                    customTot.setText("0.00");

                    return;
                }





            }


            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int value = seekBar.getProgress();
                displayProgress.setText(value + "%");

                try {
                    double tip = ((double) customTipSlider.getProgress()/100) + 1.00;
                    double bill = Double.parseDouble(iniBill.getText().toString());
                    double customTipValue = calcTip(bill, tip);
                    double customTotValue = calcTotal(bill, tip);

                    customTip.setText(String.format("%.1f",customTipValue));
                    customTot.setText(String.format("%.2f",customTotValue));


                }

                catch(NumberFormatException ex)
                {
                    tenPerTip.setText("0.00");
                    fifPerTip.setText("0.00");
                    twenPerTip.setText("0.00");

                    tenPerTot.setText("0.00");
                    fifPerTot.setText("0.00");
                    twenPerTot.setText("0.00");

                    customTip.setText("0.00");
                    customTot.setText("0.00");

                    return;
                }



            }
        });






        // make it so that changing the value of the bill changes the numbers on everything

        iniBill.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                try {

                    double bill = Double.parseDouble(iniBill.getText().toString());

                    double tenPer = 1.1;
                    double fifPer = 1.15;
                    double twenPer = 1.2;

                    double tip10 = calcTip(bill, tenPer);
                    double tip15 = calcTip(bill, fifPer);
                    double tip20 = calcTip(bill, twenPer);

                    double total10 = calcTotal(bill, tenPer);
                    double total15 = calcTotal(bill, fifPer);
                    double total20 = calcTotal(bill, twenPer);

                    tenPerTip.setText(String.format("%.2f",tip10));
                    fifPerTip.setText(String.format("%.2f",tip15));
                    twenPerTip.setText(String.format("%.2f",tip20));

                    tenPerTot.setText(String.format("%.2f",total10));
                    fifPerTot.setText(String.format("%.2f",total15));
                    twenPerTot.setText(String.format("%.2f",total20));

                    double tip = ((double) customTipSlider.getProgress()/100) + 1.00;
                    double customTipValue = calcTip(bill, tip);
                    double customTotValue = calcTotal(bill, tip);
                    customTip.setText(String.format("%.2f",customTipValue));
                    customTot.setText(String.format("%.2f",customTotValue));




                }
                catch(NumberFormatException ex){
                    tenPerTip.setText("0.00");
                    fifPerTip.setText("0.00");
                    twenPerTip.setText("0.00");

                    tenPerTot.setText("0.00");
                    fifPerTot.setText("0.00");
                    twenPerTot.setText("0.00");

                    customTip.setText("0.00");
                    customTot.setText("0.00");
                    return;
                }



            }

            @Override
            public void afterTextChanged(Editable editable) {

                try {

                    double bill = Double.parseDouble(iniBill.getText().toString());

                    double tenPer = 1.1;
                    double fifPer = 1.15;
                    double twenPer = 1.2;

                    double tip10 = calcTip(bill, tenPer);
                    double tip15 = calcTip(bill, fifPer);
                    double tip20 = calcTip(bill, twenPer);

                    double total10 = calcTotal(bill, tenPer);
                    double total15 = calcTotal(bill, fifPer);
                    double total20 = calcTotal(bill, twenPer);

                    tenPerTip.setText(String.format("%.2f",tip10));
                    fifPerTip.setText(String.format("%.2f",tip15));
                    twenPerTip.setText(String.format("%.2f",tip20));

                    tenPerTot.setText(String.format("%.2f",total10));
                    fifPerTot.setText(String.format("%.2f",total15));
                    twenPerTot.setText(String.format("%.2f",total20));

                    double tip = ((double) customTipSlider.getProgress()/100) + 1.00;
                    double customTipValue = calcTip(bill, tip);
                    double customTotValue = calcTotal(bill, tip);
                    customTip.setText(String.format("%.2f",customTipValue));
                    customTot.setText(String.format("%.2f",customTotValue));


                }
                catch (NumberFormatException ex )
                {
                    tenPerTip.setText("0.00");
                    fifPerTip.setText("0.00");
                    twenPerTip.setText("0.00");

                    tenPerTot.setText("0.00");
                    fifPerTot.setText("0.00");
                    twenPerTot.setText("0.00");

                    customTip.setText("0.00");
                    customTot.setText("0.00");
                    return;
                }


            }
        });


    } // end on create



    // have a listener for bill total so that if the bill total changes , it call all the
    // and each method will place new tips and totals in their boxes
    // add a listener so that the tips and totals of custom change when progress bar is changed


    // calculate tip method
    public double  calcTip(double bill,double percent){

        double tip ;
        double total ;

        total = bill * percent;
        tip = (total-bill);

        return tip;

    } // end calc tip

    // calc total method

    public double calcTotal(double bill,double percent){

        double total ;

        total = bill * percent;


        return total;

    }// end calc total







} // end of main activity
