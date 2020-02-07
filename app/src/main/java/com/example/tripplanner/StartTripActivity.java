package com.example.tripplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class StartTripActivity extends AppCompatActivity {

    private LinearLayout mainLayout; // The vertical layout that's used to hold the DT entries

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_trip);

        // Get references to UI elements and store them in data members
        mainLayout = findViewById(R.id.dtItemsPanel);

        /* CREATE LISTENERS */
        // Create the click listener for the Add button
        Button addDTBtn = findViewById(R.id.addDTBtn);
        addDTBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "Add button clicked!", Toast.LENGTH_SHORT);
                toast.show();
                DT newDT = DT.CreateDestinationTransit(StartTripActivity.this, mainLayout, getString(R.string.dt_msg));
            }
        });
        // Create the click listener for the Start Trip button
        Button startTripBtn = findViewById(R.id.startTripBtn);
        startTripBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Toast toast = Toast.makeText(getApplicationContext(), "Start Trip button clicked!", Toast.LENGTH_SHORT);
               toast.show();
           }
        });

        /********** TESTING CODE ********/
        //Toast.makeText(getApplicationContext(), tripEntry.getName(), Toast.LENGTH_SHORT).show();
        //tripEntry.setName("Test name");
        //Toast.makeText(getApplicationContext(), tripEntry.getName(), Toast.LENGTH_SHORT).show();
    }
}
