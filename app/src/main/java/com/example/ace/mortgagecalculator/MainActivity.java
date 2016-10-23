package com.example.ace.mortgagecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity
{
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();
    private static final NumberFormat regularFormat = NumberFormat.getNumberInstance();
    private static final NumberFormat decimalFormat = new DecimalFormat("##.##%");

    private double houseAmount = 0.0;
    private double downPayment = 0.0;
    private double interest = 0.0;
    private int term = 20;

    // Affected Label TextViews
    private TextView priceTextView;
    private TextView downTextView;
    private TextView interestTextView;
    private TextView loanLabelTextView;

    // Currency TextViews
    private TextView totalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get reference to program TextViews
        priceTextView = (TextView)findViewById(R.id.priceTextView);
        downTextView = (TextView)findViewById(R.id.downTextView);
        loanLabelTextView = (TextView)findViewById(R.id.loanLabelTextView);
        interestTextView = (TextView)findViewById(R.id.interestTextView);
        totalTextView = (TextView)findViewById(R.id.totalTextView);

        totalTextView.setText(currencyFormat.format(0));

        // set EditTexts and their TextWatchers
        EditText priceEditText = (EditText)findViewById(R.id.priceEditText);
        priceEditText.addTextChangedListener(priceEditTextWatcher);

        EditText downEditText = (EditText)findViewById(R.id.downEditText);
        downEditText.addTextChangedListener(downEditTextWatcher);

        EditText interestEditText = (EditText)findViewById(R.id.interestEditText);
        interestEditText.addTextChangedListener(interestEditTextWatcher);

        // set SeekBar and onSeekBarChangeListener
        SeekBar loanSeekBar = (SeekBar)findViewById(R.id.loanSeekBar);
        loanSeekBar.setOnSeekBarChangeListener(loanSeekBarListener);
    }

    private void calculate()
    {
        loanLabelTextView.setText(regularFormat.format(term));

        double principle = houseAmount - downPayment;
        double rate = interest/12;
        int loanLength = term * 12;
        double total = principle*((rate *(Math.pow((1 + rate),loanLength)))/(Math.pow((1 + rate),loanLength) - 1));

        totalTextView.setText(currencyFormat.format(total));
    }

    private final SeekBar.OnSeekBarChangeListener loanSeekBarListener = new SeekBar.OnSeekBarChangeListener()
    {
        // update percent, then call calculate
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            term = (progress + 1) * 5; // set percent based on progress
            calculate(); // calculate and display tip and total
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private final TextWatcher priceEditTextWatcher = new TextWatcher()
    {
        //called when the user modifies the bill amount
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            try // get bill amount and display currency formatted value
            {
                houseAmount = Double.parseDouble(s.toString())/ 100.0;
                priceTextView.setText(currencyFormat.format(houseAmount));
            }
            catch (NumberFormatException e) // if s is empty or non-numeric
            {
                priceTextView.setText("");
                houseAmount = 0.0;
            }

            calculate();    // update the tip and total TextViews
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private final TextWatcher downEditTextWatcher = new TextWatcher()
    {
        //called when the user modifies the bill amount
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            try // get bill amount and display currency formatted value
            {
                downPayment = Double.parseDouble(s.toString())/ 100.0;
                downTextView.setText(currencyFormat.format(downPayment));
            }
            catch (NumberFormatException e) // if s is empty or non-numeric
            {
                downTextView.setText("");
                downPayment = 0.0;
            }

            calculate();    // update the tip and total TextViews
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private final TextWatcher interestEditTextWatcher = new TextWatcher()
    {
        //called when the user modifies the bill amount
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            try // get bill amount and display currency formatted value
            {
                interest = Double.parseDouble(s.toString())/100;
                interestTextView.setText(decimalFormat.format(interest));
            }
            catch (NumberFormatException e) // if s is empty or non-numeric
            {
                interestTextView.setText("");
                interest = 0.0;
            }

            calculate();    // update the tip and total TextViews
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
