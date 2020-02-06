package com.example.tripplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StartTripActivity extends AppCompatActivity {

    private LinearLayout mainLayout; // The vertical layout that's used to hold the DT entries

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_trip);

        // Get references to UI elements and store them in data members
        mainLayout = findViewById(R.id.dtItemsPanel);

        // Create the click listener for the Add button
        Button addDTBtn = findViewById(R.id.addDTBtn);
        addDTBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "Add button clicked!", Toast.LENGTH_LONG);
                toast.show();
            }
        });
        // Create the click listener for the Start Trip button
        Button startTripBtn = findViewById(R.id.startTripBtn);
        startTripBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Toast toast = Toast.makeText(getApplicationContext(), "Start Trip button clicked!", Toast.LENGTH_LONG);
               toast.show();
           }
        });

        // Create a new LinearLayout
        LinearLayout newLayout = new LinearLayout(this);
        newLayout.setOrientation(LinearLayout.HORIZONTAL);
        newLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                                                LinearLayout.LayoutParams.WRAP_CONTENT));

        // Create an EditText for testing and add it to the new LinearLayout
        EditText newEditText = new EditText(this);
        newEditText.setLayoutParams(new LinearLayout.LayoutParams(UIManager.calcDP(newEditText, 200), ViewGroup.LayoutParams.WRAP_CONTENT));
        newEditText.setText(R.string.test_string);
        newLayout.addView(newEditText);

        // Add the new LinearLayout to the main LinearLayout
        mainLayout.addView(newLayout);

        DT tripEntry = new DT(DT.DT_Type.NOT_SELECTED, "Starting location");

        /********** TESTING CODE ********/
        //Toast.makeText(getApplicationContext(), tripEntry.getName(), Toast.LENGTH_SHORT).show();
        //tripEntry.setName("Test name");
        //Toast.makeText(getApplicationContext(), tripEntry.getName(), Toast.LENGTH_SHORT).show();
    }
/*
    private int calcDP(View view, int dp) {
        float scale = view.getContext().getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }
 */
}
