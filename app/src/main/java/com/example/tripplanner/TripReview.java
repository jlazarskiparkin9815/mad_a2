/*
    FILE             : TripReview
    PROJECT          : PROG3150 - Assignment 1
    PROGRAMMER       : Conor Barr, Eric Emerson, Jack Parkinson, Maxim Mikheev, Rick Bloemert
    FIRST VERSION    : 2020-02-02
    DESCRIPTION      :
        This screen displays the start date followed by each destination or transit on the trip then the ending date.
*/

package com.example.tripplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class TripReview extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState/*, Trip userTrip*/) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_review);

        Trip newTrip = StartTripActivity.newTrip;
        final SimpleDateFormat dateAllFormat = new SimpleDateFormat("dd/MM/yyyy");
        LinearLayout dtItemsPanel = findViewById(R.id.dtItemsPanel);

        TextView startText = new TextView(this);
        startText.setText("- " + newTrip.getName() + " Start (" + dateAllFormat.format(newTrip.getStart()) + ") -");
        startText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        startText.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        startText.setTextSize(24);
        dtItemsPanel.addView(startText);

        //Loops through each destination in the list.
        for(int i = 0; i < newTrip.getDt_list().size(); i++){
            TextView newText = new TextView(this);
            switch(newTrip.getDt_list().get(i).getDT_Type()){
                case DESTINATION:
                    newText.setText("- DESTINATION -\n" + newTrip.getDt_list().get(i).getName());
                    break;
                case TRANSIT:
                    newText.setText("- TRANSIT -\n" + newTrip.getDt_list().get(i).getName());
                    break;
            }
            newText.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            newText.setTextSize(24);
            newText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            dtItemsPanel.addView(newText);
        }

        TextView endText = new TextView(this);
        endText.setText("- " + newTrip.getName() + " End (" + dateAllFormat.format(newTrip.getEnd()) + ") -");
        endText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        endText.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        endText.setTextSize(24);
        dtItemsPanel.addView(endText);

        //--------FINISH BUTTON--------//
        Button finishButton = findViewById(R.id.finishButton);
        finishButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                finish();
                startActivity(new Intent(TripReview.this, TripSummaryActivity.class));
            }
        });

    }



}
