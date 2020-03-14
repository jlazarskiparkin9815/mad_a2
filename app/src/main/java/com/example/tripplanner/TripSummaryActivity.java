/*
    FILE             : TripSummaryActivity
    PROJECT          : PROG3150 - Assignment 2
    PROGRAMMER       : Conor Barr, Eric Emerson, Jack Parkinson, Maxim Mikheev, Rick Bloemert
    FIRST VERSION    : 2020-03-09
    DESCRIPTION      :
        This screen displays a message that tells the user that they've arrived at their
        destination and then allows them to go back to the MainActivity.
*/

package com.example.tripplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TripSummaryActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_summary);

        // Create listener for Restart button
        Button restartButton = (Button) findViewById(R.id.button_restart);

        // When the user clicks the button, go back to the start of the program
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete the Trip (it's finished now)
                TripPlannerDB database = new TripPlannerDB(getApplicationContext());
                int tripID = UIManager.getNavigationCode(TripSummaryActivity.this, "tripID");
                database.deleteTrip(tripID);

                // Go back to the main screen
                startActivity(new Intent(TripSummaryActivity.this, MainActivity.class));
            }
        });
    }

}
