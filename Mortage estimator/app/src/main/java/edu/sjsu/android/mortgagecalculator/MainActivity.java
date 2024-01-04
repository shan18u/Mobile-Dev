package edu.sjsu.android.mortgagecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Description: This App Calculates the mortgage of the loan that was taken.
 * Takes the input of interest rate, Amount, and Loan Term
 * Author name: Shivansh Chhabra
 * Last modified date: 09/26/2023
 * Creation date: 09/21/ 2023
 **/


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView interestRate_textView;
    private EditText amtBorrowed_editText;
    private TextView mortgageCalculated_textView;

    private SeekBar interestRate_seekBar;

    private RadioButton loanTerm15;

    private RadioButton loanTerm20;
    private RadioButton loanTerm30;

    private Button calculateButton;

    private CheckBox loansAndinsurance_checkBox;

    // Variables for storing mortgage calculation values
    double Principle;
    double N;
    double J;
    double T;
    double interestRate = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        interestRate_textView = findViewById(R.id.interestRate_textView);
        amtBorrowed_editText = findViewById(R.id.amtBorrowed_editText);
        interestRate_seekBar = findViewById(R.id.interestRate_seekBar);

        // Set up a listener for the interest rate SeekBar
        interestRate_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            float progressChangedValue = 10;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = (float)progress;
                interestRate = (float) progress;
                interestRate_textView.setText("Interest Rate: " + progressChangedValue + "%");
                hideSoftKeyboard(amtBorrowed_editText);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                hideSoftKeyboard(amtBorrowed_editText);
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                interestRate_textView.setText("Interest Rate: " + progressChangedValue + "%");
                Toast.makeText(MainActivity.this, "Seek bar progress is :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();
                hideSoftKeyboard(amtBorrowed_editText);
            }
        });

        // Initialize radio buttons for loan term options
        loanTerm15 = findViewById(R.id.loanTerm15_radioButton);
        loanTerm20 = findViewById(R.id.loanTerm20_radioButton);
        loanTerm30 = findViewById(R.id.loanTerm30_radioButton);
        setOnClickListener(loanTerm15);
        setOnClickListener(loanTerm20);
        setOnClickListener(loanTerm30);

        calculateButton = findViewById(R.id.calculate_button);
        calculateButton.setOnClickListener(this);

        mortgageCalculated_textView = findViewById(R.id.mortgageOutput_textView);

        loansAndinsurance_checkBox = findViewById(R.id.loansAndTaxes_checkBox);
    }


    // Helper method to set click listeners for radio buttons
    private void setOnClickListener(View view){
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
            }
        });
    }


    @Override
    public void onClick(View view) {
        hideSoftKeyboard(amtBorrowed_editText);

        //Principle Amount
        if(amtBorrowed_editText.getText().toString().equals("")){
            Toast.makeText(this, "Please enter a valid Amount",
                    Toast.LENGTH_LONG).show(); return;
        }
        else {
            try{
                Principle = Double.parseDouble(amtBorrowed_editText.getText().toString());  //Principle Amount
            }
            catch (Exception e){
                Toast.makeText(this, "Please enter a valid Amount",
                        Toast.LENGTH_LONG).show(); return;
            }
        }

        // Calculate the monthly interest rate
        J = interestRate/1200;

        //Checking the Term and Months of Loan
        if(loanTerm15.isChecked()){
            N = 15*12;
        }
        else if(loanTerm20.isChecked()){
            N = 20*12;
        }
        else if(loanTerm30.isChecked()){
            N = 30*12;
        }
        else {
            Toast.makeText(this, "Please select the Loan term",
                    Toast.LENGTH_LONG).show(); return;
        }

        //Loans And Insurance Check
        if(loansAndinsurance_checkBox.isChecked()){
            T = 0.001 * Principle;
        }
        else{
            T = 0;
        }
        System.out.println("T: " + T);
        //Loans And Insurance Check Ends

        // Calculate the monthly mortgage payment
        String calculatedMortgage = Calculate();
        mortgageCalculated_textView.setText("Your Monthly Mortgage is:\n " + calculatedMortgage + "\n");
    }



    private String Calculate(){
        double M = 0;
        if(interestRate == 0){
            M = (Principle/N)+T;
        }
        else{
            M = (Principle * (J/(1-(Math.pow(1+J, -N))))) + T;
        }
        String outputRounded = String.format("%.2f", M);
        return outputRounded;
    }

    public void hideSoftKeyboard(View view){
        InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



    private void showKeyboard() {
        InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(amtBorrowed_editText, 0);
    }

}