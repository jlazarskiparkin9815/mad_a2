/*
    FILE             : HelpActivity
    PROJECT          : PROG3150 - Assignment 2
    PROGRAMMER       : Conor Barr, Eric Emerson, Jack Parkinson, Maxim Mikheev, Rick Bloemert
    FIRST VERSION    : 2020-03-09
    DESCRIPTION      :
        This screen displays helpful information on the usage of the application.
*/


package com.example.tripplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity implements TextWatcher {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // back button

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.help_topics));
        AutoCompleteTextView editTextFilledExposedDropdown = findViewById(R.id.filled_exposed_dropdown);
        editTextFilledExposedDropdown.setAdapter(adapter);
        editTextFilledExposedDropdown.addTextChangedListener(this); // each time item selected, change textbox
    }

    /*
        FUNCTION: findIndex
        DESCRIPTION: Compares indexes in an array to see if the strings match.
        PARAMETERS: String checkArr[]: The Array to compare.
                    String retArr[]: The array string to return.
                    String match: The string to check and match.
        RETURNS: String: Returns the corresponding string.
    */
    public String findIndex(String[] checkArrr, String[] retArr, String match)
    {
        for (int i = 0; i < checkArrr.length; i++)
        {
            if (checkArrr[i].equals(match))
            {
                Log.v("HelpActivity", "findIndex returned: " + retArr[i]);
                return retArr[i];
            }
        }
        Log.v("HelpActivity", "findIndex failed to return anything.");
        return "";
    }

    @Override
    public void afterTextChanged(Editable s)
    {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
        Log.v("HelpActivity", "Text Changed in Help selector");
        TextView textView = findViewById(R.id.textView);
        textView.setText(findIndex(getResources().getStringArray(R.array.help_topics), getResources().getStringArray(R.array.help_desc), s.toString()));
    }

    @Override
    public boolean onSupportNavigateUp(){ // actionbar back button
        finish();
        Log.v("HelpActivity", "Transition to MainActivity");
        return true;
    }

}

