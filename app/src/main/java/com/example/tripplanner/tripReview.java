package com.example.tripplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class tripReview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_review);

        final TextView helloTextView = (TextView) findViewById(R.id.tripBox);
        // helloTextView.setText(R.string.user_greeting);

        //--------EDIT BUTTON--------//
        Button editButton = (Button) findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(new Intent(tripReview.this, HelpActivity.class)); // EDIT "HelpActivity" to the trip maker
            }
        });

        //--------FINSIH BUTTON--------//
        Button finishButton = (Button) findViewById(R.id.finishButton);
        finishButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(new Intent(tripReview.this, HelpActivity.class)); // EDIT "HelpActivity" to the final screen
            }
        });

    }



}
