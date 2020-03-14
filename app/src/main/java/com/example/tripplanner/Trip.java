/*
    FILE             : Trip
    PROJECT          : PROG3150 - Assignment 1
    PROGRAMMER       : Conor Barr, Eric Emerson, Jack Parkinson, Maxim Mikheev, Rick Bloemert
    FIRST VERSION    : 2020-02-02
    DESCRIPTION      :
        This class models the Trip. It contains all data related
        to the Trip, including: name of the trip, start date, end date,
        and a List of DT objects. The DT objects contain information
        about each Destination/Transit for the Trip (refer to DT class
        documentation for more information).
*/

package com.example.tripplanner;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class Trip implements Serializable {
        transient public static final String SERIALIZATION_FILENAME = "/trip.ser";

        private ArrayList<DT> dt_list;
        private int id;
        private String name;
        private Date startDate;
        private Date endDate;
        transient private int dtIDCounter; // 'transient' means it won't be serialized (i.e. won't be converted to JSON)

        /*
                FUNCTION: Trip
                DESCRIPTION: Trip class constructor.
                PARAMETERS: String new_name: The name of the trip.
                            Date new_startDate: The trip's start date.
                            Date new_endDate: The trip's end date.
                RETURNS: N/A
         */
        public Trip(String new_name, Date new_startDate, Date new_endDate){
                name = new_name;
                startDate = new_startDate;
                endDate = new_endDate;
                dt_list = new ArrayList<>();
        }

        // Used for retrieving Trips from the database
        public Trip(int _id, String _name, Date _startDate, Date _endDate, ArrayList<DT> _dt_list) {
                this.id = _id;
                this.name = _name;
                this.startDate = _startDate;
                this.endDate = _endDate;
                this.dt_list = _dt_list;
        }

        /*
                FUNCTION: addDestination
                DESCRIPTION: Adds a destination to the trip.
                PARAMETERS: DT new_dest: The destination to add to the list.
                RETURNS: N/A
         */
        public void addDestination(DT new_dest){
                dt_list.add(new_dest);
        }

        /*
                FUNCTION: validateDate
                DESCRIPTION: Checks whether a date was valid. An error message is
                             displayed if the date is invalid.
                PARAMETERS: String dateStartStr: The date that's being checked.
                            Activity displayMsgActivity: The Activity where the message is
                                being displayed
                RETURNS: void
        */
        public static boolean validateDate(String dateStartStr) {
                boolean result = true;

                // Check if the Date format is invalid and display an error message if it is
                if (Pattern.matches("\\d\\d/\\d\\d/\\d\\d\\d\\d", dateStartStr) == false) {
                        result = false;
                }

                return result;
        }

        /*
            FUNCTION: CreateDestinationTransit()
            DESCRIPTION: Creates a all elements needed to add a new Destination/Transit.
                         Elements that are created include: DT object, EditText, and Spinner.
            PARAMETERS:
                AppCompatActivity activity: The Activity that the UI elements are being displayed on
                LinearLayout mainLayout: The LinearLayout that contains the LinearLayout created
                                         in this method.
                Trip theTrip: The Trip object that the DT is being added to
                String name: The name that's being assigned to the DT object
                DT_Type newType: The type of DT object that's being created (either Destination or Transit)
                int id: The ID of the DT object that's being created. If this DT is being created for the
                        first time, then pass DT.ID_NOT_SET for this parameter.
            RETURNS:
                DT: The Dt object that was created
        */
        public DT CreateDestinationTransit(AppCompatActivity activity, final LinearLayout mainLayout, String name, DT.DT_Type newType, int id) {
                // Create the DT object
                final DT newDT = new DT(name); // Object needs to be 'final' to be accessed within the listeners

                // Assign an ID to the DT object or reuse the existing one
                if (id == DT.ID_NOT_SET) {
                        // Increments the DT id.
                        newDT.setID(this.dtIDCounter);
                        this.dtIDCounter++;
                }
                else {
                        newDT.setID(id);
                }

                // Create the new layout and add it to the main layout
                final LinearLayout subLayout = UIManager.createLinearLayout(activity);
                mainLayout.addView(subLayout);

                // Create the EditText and add it to the sub-layout
                final EditText nameBox = UIManager.createEditText(activity);
                subLayout.addView(nameBox);
                nameBox.setText(name);

                // Create the Spinner and add it to the sub-layout
                final Spinner typeSpinner = UIManager.createSpinner(activity);
                subLayout.addView(typeSpinner);
                switch(newType){
                        case DESTINATION:
                                typeSpinner.setSelection(0);
                                break;
                        case TRANSIT:
                                typeSpinner.setSelection(1);
                                break;
                }

                final Button delButton = UIManager.createButton(activity);
                subLayout.addView(delButton);
                delButton.setTag(newDT.getID());
                delButton.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
                delButton.setTextColor(ContextCompat.getColor(activity, R.color.design_default_color_background));

                delButton.setOnClickListener(new View.OnClickListener(){
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
                nameBox.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence text, int start, int before, int count) {
                                // Update the name of the DT object
                                newDT.setName(nameBox.getText().toString());
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                });

                // Add a listener for the Spinner
                typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String selectedItem = parent.getItemAtPosition(position).toString();

                                // Assign to the DT_Type enum based on the selected item
                                if (selectedItem.equals("Destination")) {
                                        newDT.setType((DT.DT_Type.DESTINATION));
                                }
                                else if (selectedItem.equals("Transit")) {
                                        newDT.setType((DT.DT_Type.TRANSIT));
                                }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                });

                return newDT;
        }

        /*
                FUNCTION: serializeTrip
                DESCRIPTION: Serializes a Trip object and saves it to a '.ser' file. This
                        allows the Trip object to be passed between Activities. Use this method
                        when navigating between Activities if you need to pass a Trip object.
                PARAMETERS:
                        Trip theTrip: The Trip object that's being serialized.
                        Activity activity: The Activity that the Trip object is being
                                passed from. This is just used to get the absolute path
                                that's needed to save the '.ser' file.
                RETURNS: N/A
        */
        public void serializeTrip(Trip theTrip, Activity activity) {
                try {
                        // Open streams
                        FileOutputStream fos = new FileOutputStream(activity.getFilesDir().getPath() + SERIALIZATION_FILENAME);
                        ObjectOutputStream oos = new ObjectOutputStream(fos);

                        // Save the object to the file
                        oos.writeObject(theTrip);

                        // Close the streams
                        fos.close();
                        oos.close();
                }
                catch (Exception e) {
                        // Log
                }

        }

        /*
                FUNCTION: deserializeTrip
                DESCRIPTION: Deserializes a Trip object from a '.ser' file.
                        Use to retrieve data sent by the serializeTrip() method.
                PARAMETERS:
                         Activity activity: The Activity that's retrieving the Trip
                                object. This is used to fine the absolute file path
                                to the '.ser' file.
                RETURNS:
                        Trip theTrip: The Trip object that was deserialized.
        */
        public Trip deserializeTrip(Activity activity) {
                Trip theTrip = null;

                try {
                        // Open streams for input
                        FileInputStream fis = new FileInputStream(activity.getFilesDir().getPath() + SERIALIZATION_FILENAME);
                        ObjectInputStream ois = new ObjectInputStream(fis);

                        // Read from the stream
                        theTrip = (Trip) ois.readObject();

                        // Close the streams
                        fis.close();
                        ois.close();
                }
                catch (Exception e) {
                        // Log
                }

                return theTrip;
        }

        /* ACCESSORS FOR DATA MEMBERS*/
        public int getID() {return this.id;}
        public int getDtIDCounter() {return this.dtIDCounter;}
        public String getName() {return this.name;}
        public Date getStart() {return this.startDate;}
        public Date getEnd() {return this.endDate;}
        public ArrayList<DT> getDt_list() {return this.dt_list;}

        /* MUTATORS FOR DATA MEMBERS */
        public void setID(int id) {this.id = id;}
        public void setDtIDCounter(int dtIDCounter) {this.dtIDCounter = dtIDCounter;}
        public void setName(String name) {this.name = name;}
        public void setStart(Date date) {this.startDate = date;}
        public void setEnd(Date date) {this.endDate = date;}
}
