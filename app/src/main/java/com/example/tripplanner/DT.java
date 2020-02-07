package com.example.tripplanner;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

// Data members:
//  - type (whether it's a Destination or Transit) [enum]
//  - name (the name that the user gives the Destination/Trip)[string]
//      Destination example: Hotel, Motel, etc.
//      Transit: Car, Bus, or Train
public class DT extends AppCompatActivity {
    public enum DT_Type {
        NOT_SELECTED,
        DESTINATION,
        TRANSIT
    }

    private DT_Type type; // Whether the trip is a Destination or Transit (or not selected)
    private String name; // The name given to the Destination or Transit
    private EditText nameBox;
    private Spinner typeSpinner;

    // Constructor that sets all data members to default
    public DT (String _name) {
        this.type = DT_Type.NOT_SELECTED;
        this.name = _name;
    }

    // Constructor for the DT class which takes a DT_Type and name
    public DT (DT_Type _type, String _name) {
        this.type = _type;
        this.name = _name;
    }

    /* ADDITIONAL METHODS */
    // Creates a all elements needed to add a new Destination/Transit.
    // Elements include: DT object, EditText, and Spinner.
    // LinearLayout mainLayout: The LinearLayout that's holding the
    //      LinearLayout this method creates
    public static DT CreateDestinationTransit(final AppCompatActivity activity, LinearLayout mainLayout, String name) {
        // Create the DT object
        final DT newDT = new DT(name);

        // Create the new layout and add it to the main layout
        LinearLayout subLayout = UIManager.createLinearLayout(activity);
        mainLayout.addView(subLayout);

        // Create the EditText and add it to the sub-layout
        EditText newEditText = UIManager.createEditText(activity);
        subLayout.addView(newEditText);

        // Create the Spinner
        Spinner newSpinner = UIManager.createSpinner(activity);
        subLayout.addView(newSpinner);

        // Create listeners for the EditText and Spinner (all 3 methods must be overridden)
        newEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                // Assign the new name to the DT object
                newDT.name = text.toString();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Add the EditText and Spinner to the new DT object
        newDT.nameBox = newEditText;
        newDT.typeSpinner = newSpinner;

        return newDT;
    }
}
