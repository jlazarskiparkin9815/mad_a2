/*
    CLASS: StartTripActivity
    PURPOSE: This Activity displays the Start Trip screen. It allows
             the user to specify the name of their Trip and a start/end
             date. The user can also enter any number of Destinations/Transits.
 */

package com.example.tripplanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.List;

import static com.example.tripplanner.DT.CreateDestinationTransit;

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
        final NestedScrollView scrollView = findViewById(R.id.dtItemsScroll);
        final TextView itemsAdded = findViewById(R.id.itemsAddedCount);

        mainLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                itemsAdded.setText(getItemsAddedStr());
            }
        });
        itemsAdded.setText(getResources().getString(R.string.itemsAddedCountStart));
        // Create the Trip (must add DT objects to it as they get created)
        Date defaultDate = new Date(2000, 1, 1); // tmp Date used for testing
        if(newTrip == null){
            newTrip = new Trip("Untitled Trip", defaultDate, defaultDate);
        }else{
            tripName.setText(newTrip.getName());
            dateStart.setText(dateAllFormat.format(newTrip.getStart()));
            dateEnd.setText(dateAllFormat.format(newTrip.getEnd()));

            List<DT> tmpList = newTrip.getDt_list();
            for(int i = 0; i < tmpList.size(); i++)
            {
                CreateDestinationTransit(StartTripActivity.this, mainLayout, tmpList.get(i).getName(), tmpList.get(i).getDT_Type(), tmpList.get(i).getID());
            }
        }



        /* -------------- CREATE LISTENERS -------------- */
        // Create the click listener for the Add button
        addDTBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the DT object and add Trip List
                DT newDT = CreateDestinationTransit(StartTripActivity.this, mainLayout, "", DT.DT_Type.DESTINATION, -1);
                newTrip.addDestination(newDT);
                scrollView.fullScroll(View.FOCUS_DOWN); // scroll down
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
                DT.idCounter = 0;
                startActivity(new Intent(StartTripActivity.this, MainActivity.class));
            }
        });

        startTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newTrip.getDt_list().size() == 0)
                {
                    new AlertDialog.Builder(StartTripActivity.this)
                            .setTitle("Attention")
                            .setMessage("At least 1 destination/transit must be added prior to continuing.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
                else
                {
                    startActivity(new Intent(StartTripActivity.this, TripReview.class));
                }

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){ // actionbar back button
        finish();
        return true;
    }

    public String getItemsAddedStr()
    {
        if (newTrip.getDt_list().size() == 0)
        {
            return getResources().getString(R.string.itemsAddedCountStart);
        }
        else
        {
            return newTrip.getDt_list().size() + " " + getResources().getString(R.string.itemsAddedCountSuffix);
        }
    }
}
