/*
* CLASS   : StartTripOnClickListener
* PURPOSE : This class overrides the OnClickListener.
*/

package com.example.tripplanner;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class StartTripOnClickListener implements View.OnClickListener {
    private AppCompatActivity activity;

    /*
        FUNCTION: StartTripOnClickListener
        DESCRIPTION: Constructor for the StartTripOnClickListener class.
        PARAMETERS:
            AppCompatActivity _activity: The source Activity (the Activity that you're
                navigating from).
        RETURNS: N/A
    */
    public StartTripOnClickListener(AppCompatActivity _activity) {
        this.activity = _activity;
    }

    /*
    FUNCTION: onClick
    DESCRIPTION: Overridden onClick() method that navigates to the TripReview activity.
    PARAMETERS:
        View v: The View that was clicked.
    RETURNS: void
    */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity.getApplicationContext(), TripReview.class);
        activity.startActivity(intent);
    }
}
