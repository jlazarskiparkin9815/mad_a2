package com.example.tripplanner;

import java.util.Date;
import java.util.List;

// Data members:
//  - Destination/Transits [List]
//  - name [string]
//  - start date [date]
//  - end date [date]
public class Trip {
        private List<DT> dt_list;
        private String name;
        private Date startDate;
        private Date endDate;

        public List<DT> getDt_list() {
                return this.dt_list;
        }
}
