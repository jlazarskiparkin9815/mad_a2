/*
 * CLASS   : DT
 * PURPOSE : This is the object that's used to hold Destinations/Transits
 *           that are created in the StartTripActivity. Destinations are
 *           places that the user is staying on their Trip and Transit
 *           are methods of transportation.
 */

package com.example.tripplanner;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Button;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Iterator;

// Data members:
//  - type (whether it's a Destination or Transit) [enum]
//  - name (the name that the user gives the Destination/Trip)[string]
//      Destination example: Hotel, Motel, etc.
//      Transit: Car, Bus, or Train

public class DT extends AppCompatActivity {

    public static int idCounter = 0;

    public enum DT_Type {
        DESTINATION,
        TRANSIT
    }

    private DT_Type type; // Whether the trip is a Destination or Transit (or not selected)
    private String name; // The name given to the Destination or Transit
    private EditText nameBox;
    private Spinner typeSpinner;
    private Button delButton;
    private int dtID;

    /*
        FUNCTION: DT
        DESCRIPTION: Constructor for the DT class. Allows specify a name (e.g. Motel/Hotel,
                     or Car/Bus).
        PARAMETERS:
            String _name: The name of the Destination or Transit.
        RETURNS: N/A
    */
    public DT (String _name) {
        this.type = DT_Type.DESTINATION;
        this.name = _name;
    }

    /*
        FUNCTION: DT
        DESCRIPTION: Accessor for 'private String name'. Allows retrieving
                     the value of the data member.
        PARAMETERS: N/A
        RETURNS: String: The value of the 'String name'.
    */
    public String getName() {
        return this.name;
    }

    /*
        FUNCTION: DT
        DESCRIPTION: Accessor for 'private DT_Type type'. Allows retrieving
                     the value of the data member.
        PARAMETERS: N/A
        RETURNS: DT_Type: The value of the 'String name'.
    */
    public DT_Type getDT_Type() {
        return this.type;
    }
    public int getID(){
         return this.dtID;
    }

    /*
        FUNCTION: CreateDestinationTransit()
        DESCRIPTION: Creates a all elements needed to add a new Destination/Transit.
                     Elements that are created include: DT object, EditText, and Spinner.
        PARAMETERS:
            AppCompatActivity activity: The Activity that the UI elements are being displayed on
            LinearLayout mainLayout: The LinearLayout that contains the LinearLayout created
                                     in this method.
            String name: The name that's being assigned to the DT object
            DT_Type newType: The type of DT object that's being created (either Destination or Transit)
        RETURNS:
            DT: The Dt object that was created
    */
    public static DT CreateDestinationTransit(AppCompatActivity activity, final LinearLayout mainLayout, String name, DT_Type newtype, int id) {
        // Create the DT object
        final DT newDT = new DT(name); // Object needs to be 'final' to be accessed within the listeners

        if (id == -1){
            // Increments the DT id.
            newDT.dtID = DT.idCounter;
            DT.idCounter++;
        }else{
            newDT.dtID = id;
        }

        // Create the new layout and add it to the main layout
        final LinearLayout subLayout = UIManager.createLinearLayout(activity);
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

        newDT.delButton = UIManager.createButton(activity);
        subLayout.addView(newDT.delButton);
        newDT.delButton.setTag(newDT.getID());
        newDT.delButton.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        newDT.delButton.setTextColor(ContextCompat.getColor(activity, R.color.design_default_color_background));

        newDT.delButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                for(int i = 0; i < StartTripActivity.newTrip.getDt_list().size(); i++){
                    int tempID = StartTripActivity.newTrip.getDt_list().get(i).getID();
                    int realID = (int)v.getTag();
                    if(tempID == realID){
                        StartTripActivity.newTrip.getDt_list().remove(i);
                        break;
                    }
                }
                mainLayout.removeView((View)v.getParent());
            }
        });

        // Add a listener for the EditText
        newDT.nameBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                // Update the name of the DT object
                newDT.name = newDT.nameBox.getText().toString();
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
