package com.example.tripplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.content.Intent;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class StartTripActivity extends AppCompatActivity {
    private LinearLayout mainLayout; // The vertical layout that's used to hold the DT entries
    public static Trip newTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_trip);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // back button

        // Get references to UI elements and store them in data members
        mainLayout = findViewById(R.id.dtItemsPanel);
        Button addDTBtn = findViewById(R.id.addDestButton);
        Button startTripBtn = findViewById(R.id.startTripButton);
        Button cancelTripBtn = findViewById(R.id.cancelTripButton);

        final EditText tripName = findViewById(R.id.editTripName);
        final EditText dateStart = findViewById(R.id.dateStart);
        final EditText dateEnd = findViewById(R.id.dateEnd);
        final SimpleDateFormat dateAllFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Create the Trip (must add DT objects to it as they get created)
        Date defaultDate = new Date(2000, 1, 1); // tmp Date used for testing
        if(newTrip == null){
            newTrip = new Trip("Untitled Trip", defaultDate, defaultDate);
        }else{
            tripName.setText(newTrip.getName());
            dateStart.setText(dateAllFormat.format(newTrip.getStart()));
            dateEnd.setText(dateAllFormat.format(newTrip.getEnd()));
        }



        /* -------------- CREATE LISTENERS -------------- */
        // Create the click listener for the Add button
        addDTBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the DT object and add Trip List
                DT newDT = DT.CreateDestinationTransit(StartTripActivity.this, mainLayout, getString(R.string.dt_msg), DT.DT_Type.DESTINATION);
                newTrip.addDestination(newDT);
            }
        });

        tripName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newTrip.setName(tripName.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dateStart.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Get the date from the EditText and turn it into a Date object
                String dateStartStr = dateStart.getText().toString();
                ParsePosition dateParse = new ParsePosition(0);
                Date dateStartObj = dateAllFormat.parse(dateStartStr, dateParse);

                // Check if the Date format is invalid and display an error message if it is
                Trip.validateDate(StartTripActivity.this, dateStartStr);

                newTrip.setStart(dateStartObj);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dateEnd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Get the date from the EditText and turn it into a Date object
                String dateEndStr = dateEnd.getText().toString();
                ParsePosition dateParse = new ParsePosition(0);
                Date dateEndObj = dateAllFormat.parse(dateEndStr, dateParse);

                // Check if the Date format is invalid and display an error message if it is
                Trip.validateDate(StartTripActivity.this, dateEndStr);

                newTrip.setEnd(dateEndObj);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        cancelTripBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                newTrip = null;
                finish();
            }
        });

        startTripBtn.setOnClickListener(new StartTripOnClickListener(StartTripActivity.this));
    }

    @Override
    public boolean onSupportNavigateUp(){ // actionbar back button
        finish();
        return true;
    }
}
