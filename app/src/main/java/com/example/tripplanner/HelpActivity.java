package com.example.tripplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // back button

        Spinner helpTopicSelection = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(HelpActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.help_topics));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        helpTopicSelection.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp(){ // actionbar back button
        finish();
        return true;
    }

}

