package com.example.tripplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TripListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);

        getSupportActionBar().setTitle("Trip List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LinearLayout mainLayout = findViewById(R.id.tripList);

        TripPlannerDB TripDB = new TripPlannerDB(this);
        ArrayList<Trip> TripList = TripDB.getAllTrips();

        for(int i = 0; i < TripList.size(); i++) {
            final Trip newTrip = TripList.get(i);

            LinearLayout subLayout = UIManager.createLinearLayout(this);

            TextView tripText = new TextView(this);
            tripText.setText(newTrip.getName());
            tripText.setWidth(UIManager.calcDP(tripText, 240));
            tripText.setGravity(Gravity.CENTER);
            tripText.setTextSize(20);

            Button tripView = new Button(this);
            tripView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Serialize the Trip that's being viewed (call the serializeTrip() method, it's in the Trip class)
                    Trip.serializeTrip(newTrip, TripListActivity.this);

                    // Navigate to the TripReview activity
                    startActivity(new Intent(TripListActivity.this, TripReview.class));
                }
            });
            tripView.setText(R.string.button_view);
            tripView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            tripView.setTextColor(ContextCompat.getColor(this, R.color.design_default_color_background));

            Button tripEdit = new Button(this);
            tripEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Serialize the Trip that's being viewed (call the serializeTrip() method, it's in the Trip class)
                    Trip.serializeTrip(newTrip, TripListActivity.this);

                    // Navigate to the TripReview activity
                    UIManager.navigateAndSendMode(TripListActivity.this, StartTripActivity.class, StartTripActivity.EDIT_MODE);
                }
            });
            tripEdit.setText(R.string.button_edit);
            tripEdit.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            tripEdit.setTextColor(ContextCompat.getColor(this, R.color.design_default_color_background));

            mainLayout.addView(subLayout);
            subLayout.addView(tripText);
            subLayout.addView(tripView);
            subLayout.addView(tripEdit);
            subLayout.setPadding(0, 16, 0, 0);

        }

        /* CREATE BUTTON */
        Button createTrip = (Button) findViewById(R.id.buttonCreateTrip);
        createTrip.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                // Navigate to the StartTripActivity and send a code that tells it to create a Trip
                UIManager.navigateAndSendMode(TripListActivity.this, StartTripActivity.class, StartTripActivity.CREATE_MODE);
            }
        });
    }
}
