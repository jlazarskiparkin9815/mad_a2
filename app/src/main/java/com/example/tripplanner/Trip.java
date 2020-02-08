/*
 * CLASS   : Trip
 * PURPOSE : This class models the Trip. It contains all data related
 *           to the Trip, including: name of the trip, start date, end date,
 *           and a List of DT objects. The DT objects contain information
 *           about each Destination/Transit for the Trip (refer to DT class
 *           documentation for more information).
 */

package com.example.tripplanner;

import android.app.Activity;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class Trip {
        private static final int kDateLength = 10;
        private List<DT> dt_list;
        private String name;
        private Date startDate;
        private Date endDate;

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
                dt_list = new LinkedList<>();
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
        public static void validateDate(Activity displayMsgActivity, String dateStartStr) {
                // Check if the Date format is invalid and display an error message if it is
                if (dateStartStr.length() >= kDateLength && Pattern.matches("\\d\\d/\\d\\d/\\d\\d\\d\\d", dateStartStr) == false) {
                        Toast.makeText(displayMsgActivity.getApplicationContext(), "Date format must be dd/mm/YYYY", Toast.LENGTH_LONG).show();
                }
        }

        /* ACCESSORS FOR DATA MEMBERS*/
        public String getName() {return this.name;}
        public Date getStart() {return this.startDate;}
        public Date getEnd() {return this.endDate;}
        public List<DT> getDt_list() {return this.dt_list;}

        /* MUTATORS FOR DATA MEMBERS */
        public void setName(String name) {this.name = name;}
        public void setStart(Date date) {this.startDate = date;}
        public void setEnd(Date date) {this.endDate = date;}
}
