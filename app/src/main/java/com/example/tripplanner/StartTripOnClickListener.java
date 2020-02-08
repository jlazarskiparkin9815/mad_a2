/*
* CLASS   : StartTripOnClickListener
* PURPOSE : This class overrides the OnClickListener. It's intended use
*           is to pass an object to the TripReview page.
 */

package com.example.tripplanner;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class StartTripOnClickListener implements View.OnClickListener {
    private AppCompatActivity activity;
    private Trip trip;

    /*
        FUNCTION: StartTripOnClickListener
        DESCRIPTION: Constructor for the StartTripOnClickListener class.
        PARAMETERS:
            AppCompatActivity _activity: The source Activity (the Activity that you're
                navigating from).
            Trip _trip: The Trip object that's being passed to the TripPlanner page.
        RETURNS: N/A
    */
    public StartTripOnClickListener(AppCompatActivity _activity, Trip _trip) {
        this.trip = _trip;
        this.activity = _activity;
    }

    /*
    FUNCTION: onClick
    DESCRIPTION: Overridden onClick() method. Allows passing a Trip object
                 to the TripReview Activity.
    PARAMETERS:
        View v: The View that was clicked.
    RETURNS: void
    */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
        intent.putExtra("dt_list", trip);
        activity.startActivity(intent);
    }
}
