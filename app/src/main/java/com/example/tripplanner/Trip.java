/*
 * CLASS   : Trip
 * PURPOSE : This class models the Trip. It contains all data related
 *           to the Trip, including: name of the trip, start date, end date,
 *           and a List of DT objects. The DT objects contain information
 *           about each Destination/Transit for the Trip (refer to DT class
 *           documentation for more information).
 */

package com.example.tripplanner;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Trip {
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

        /* ACCESSORS FOR DATA MEMBERS*/
        public String getName() {return this.name;}
        public Date getStart() {return this.startDate;}
        public Date getEnd() {return this.endDate;}
        public List<DT> getDt_list() {return this.dt_list;}
}
