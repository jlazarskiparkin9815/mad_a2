package com.example.tripplanner;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

// Data members:
//  - Destination/Transits [List]
//  - name [string]
//  - start date [date]
//  - end date [date]
public class Trip implements Serializable {
        private transient List<DT> dt_list;
        private transient String name;
        private transient Date startDate;
        private transient Date endDate;

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
        public String getName() {return this.name;}
        public Date getStart() {return this.startDate;}
        public Date getEnd() {return this.endDate;}
        public List<DT> getDt_list() {return this.dt_list;}
}
