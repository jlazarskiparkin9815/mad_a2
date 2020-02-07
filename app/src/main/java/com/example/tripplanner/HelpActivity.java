package com.example.tripplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;

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

    public String findIndex(String[] checkArrr, String[] retArr, String match)
    {
        for (int i = 0; i < checkArrr.length; i++)
        {
            if (checkArrr[i].equals(match))
            {
                return retArr[i];
            }
        }
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
        TextView textView = findViewById(R.id.textView);
        textView.setText(findIndex(getResources().getStringArray(R.array.help_topics), getResources().getStringArray(R.array.help_desc), s.toString()));
    }

    @Override
    public boolean onSupportNavigateUp(){ // actionbar back button
        finish();
        return true;
    }

}

