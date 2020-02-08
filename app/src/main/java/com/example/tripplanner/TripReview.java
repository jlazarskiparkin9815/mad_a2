package com.example.tripplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TripReview extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState/*, Trip userTrip*/) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_review);

        final TextView tripInfo = (TextView) findViewById(R.id.tripBox);
        final TextView tripDeatils = (TextView) findViewById(R.id. TripDetails);
        String tripString = "Name: " + StartTripActivity.newTrip.getName() + "\nStart Date: " + StartTripActivity.newTrip.getStart() + "\nEnd Date: " + StartTripActivity.newTrip.getEnd();
        String tripDetailString = "\n#      DT Type            Name\n";

        for(int i = 1; i <= StartTripActivity.newTrip.getDt_list().size(); i++)
        {
            tripDetailString += i + ".  " + StartTripActivity.newTrip.getDt_list().get(i - 1).getDT_Type() + "  " + StartTripActivity.newTrip.getDt_list().get(i - 1).getName() + "\n";
        }

        tripInfo.setText(tripString); // EDIT to insert the data
        tripDeatils.setText(tripDetailString);
        tripInfo.setText(tripString);                                        // EDIT to insert the data
        //--------EDIT BUTTON--------//
        Button editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(new Intent(TripReview.this, StartTripActivity.class));
            }
        });

        //--------FINISH BUTTON--------//
        Button finishButton = findViewById(R.id.finishButton);
        finishButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(new Intent(TripReview.this, TripSummaryActivity.class));
            }
        });

    }



}
