package com.example.tripplanner;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

// Data members:
//  - type (whether it's a Destination or Transit) [enum]
//  - name (the name that the user gives the Destination/Trip)[string]
//      Destination example: Hotel, Motel, etc.
//      Transit: Car, Bus, or Train
public class DT extends AppCompatActivity {
    public enum DT_Type {
        DESTINATION,
        TRANSIT
    }

    private DT_Type type; // Whether the trip is a Destination or Transit (or not selected)
    private String name; // The name given to the Destination or Transit
    private EditText nameBox;
    private Spinner typeSpinner;

    // Constructor that sets all data members to default
    public DT (String _name) {
        this.type = DT_Type.DESTINATION;
        this.name = _name;
    }

    // Constructor for the DT class which takes a DT_Type and name
    public DT (DT_Type _type, String _name) {
        this.type = _type;
        this.name = _name;
    }

    /* ACCESSORS / MUTATORS */
    public String getName() {
        return this.name;
    }
    public DT_Type getDT_Type() {
        return this.type;
    }

    /* ADDITIONAL METHODS */
    // Creates a all elements needed to add a new Destination/Transit.
    // Elements include: DT object, EditText, and Spinner.
    // LinearLayout mainLayout: The LinearLayout that's holding the
    //      LinearLayout this method creates
    public static DT CreateDestinationTransit(AppCompatActivity activity, LinearLayout mainLayout, String name, DT_Type newtype) {
        // Create the DT object
        final DT newDT = new DT(name); // Object needs to be 'final' to be accessed within the listeners

        // Create the new layout and add it to the main layout
        LinearLayout subLayout = UIManager.createLinearLayout(activity);
        mainLayout.addView(subLayout);

        // Create the EditText and add it to the sub-layout
        newDT.nameBox = UIManager.createEditText(activity);
        subLayout.addView(newDT.nameBox);
        newDT.nameBox.setText(name);

        // Create the Spinner and add it to the sub-layout
        newDT.typeSpinner = UIManager.createSpinner(activity);
        subLayout.addView(newDT.typeSpinner);
        switch(newtype){
            case DESTINATION:
                newDT.typeSpinner.setSelection(0);
                break;
            case TRANSIT:
                newDT.typeSpinner.setSelection(1);
                break;
        }

        // Add a listener for the EditText
        newDT.nameBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                // Update the name of the DT object
                newDT.name = text.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Add a listener for the Spinner
        newDT.typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                // Assign to the DT_Type enum based on the selected item
                if (selectedItem.equals("Destination")) {
                    newDT.type = DT_Type.DESTINATION;
                }
                else if (selectedItem.equals("Transit")) {
                    newDT.type = DT_Type.TRANSIT;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return newDT;
    }
}
