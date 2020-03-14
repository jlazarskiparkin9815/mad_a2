package com.example.tripplanner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TripListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);

        getSupportActionBar().setTitle("Trip List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TripPlannerDB TripDB = new TripPlannerDB(this);
        ArrayList<Trip> TripList = TripDB.getAllTrips();

        for(Iterator<Trip> trip = TripList.iterator(); trip.hasNext();){

            Trip newTrip = trip.next();

            LinearLayout mainLayout = findViewById(R.id.tripList);
            LinearLayout subLayout = UIManager.createLinearLayout(this);

            TextView tripText = new TextView(this);
            tripText.setText(newTrip.getName());
            tripText.setWidth(UIManager.calcDP(tripText, 240));
            tripText.setGravity(Gravity.CENTER);

            Button tripView = new Button(this);
            tripView.setText("View");

            Button tripEdit = new Button(this);
            tripEdit.setText("Edit");

            mainLayout.addView(subLayout);
            subLayout.addView(tripText);
            subLayout.addView(tripView);
            subLayout.addView(tripEdit);

        }

        final Date defaultDate = new Date(2000, 1, 1);

        Button createTrip = (Button) findViewById(R.id.buttonCreateTrip);
        createTrip.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //startTripActivity.newTrip = new Trip("Unknown Trip", defaultDate, defaultDate);
                startActivity(new Intent(TripListActivity.this, StartTripActivity.class));
            }
        });

    }
}
