package com.example.dbtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static class Tester {
        public static final int numTests = 5;
        public static final int[] ids = {1, 2, 3, 4, 5};
        public static final String[] names = {
                "Trip #1",
                "Trip #2",
                "Trip #3",
                "Trip #4",
                "Trip #5",
        };
        public static final Date[] startDates = {
                new Date(2020, 3, 21),
                new Date(2017, 10, 19),
                new Date(1998, 7, 11),
                new Date(1974, 1, 9),
                new Date(2003, 5, 28),
        };
        public static final Date[] endDates = {
                new Date(2020, 3, 28),
                new Date(2017, 10, 31),
                new Date(1998, 7, 22),
                new Date(1974, 1, 15),
                new Date(2003, 6, 03),
        };
        public static final DT[] dts = {
                new DT("Test DT #1"),
                new DT("Test DT #2"),
                new DT("Test DT #3"),
                new DT("Test DT #4"),
                new DT("Test DT #5"),
        };
    }
    private static List<Trip> tripList;
    public static Context context; // debugging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this; // debugging

        this.deleteDatabase(TripPlannerDB.DB_NAME); // Delete database   -   DEBUGGING

        // load test data into a list of Trip objects
        ArrayList<Trip> testTrips = getTestTrips();

        // Test the database
        testDB(testTrips);
    }

    // Gets all Trips used for testing and returns them
    private ArrayList<Trip> getTestTrips() {
        ArrayList<Trip> trips = new ArrayList<>();

        for (int i = 0; i < Tester.numTests; i++) {
            Trip newTrip = new Trip(
                    Tester.names[i],
                    Tester.startDates[i],
                    Tester.endDates[i]
                    );

            // Add the test DT
            for (int j = 0; j < Tester.numTests; j++) {
                newTrip.addDestination(Tester.dts[j]);
            }

            trips.add(newTrip);
        }

        return trips;
    }

    // Test database code
    private void testDB(ArrayList<Trip> testTrips) {
        // open db connection
        TripPlannerDB db = new TripPlannerDB(this);
        // WRITING TEST
        for (int i = 0; i < testTrips.size(); i++) {
            // insert trip into the database
            long rowID = db.insertTrip(testTrips.get(i));

            // Check for errors
            if (rowID == TripPlannerDB.INSERT_ERROR) {
                Toast.makeText(this, "Error inserting Trip #" + (i + 1), Toast.LENGTH_SHORT).show();
            }
        }

        // READING TEST  -  still need to write a method for this
        db.openReadableDB();
        Cursor c = db.db.query(TripPlannerDB.TRIP_TABLE, null, null,
                null, null, null, null);
        // display data
        int[] ids = new int[10];
        String[] names = new String[10];
        String[] startDates = new String[10];
        String[] endDates = new String[10];
        ArrayList<DT> dt_list = null;
        int i = 0;
        if (c.getCount() == 0) {
            Toast.makeText(this, "Trip table is empty...", Toast.LENGTH_SHORT).show();
        }
        else {
            while (c.moveToNext() == true) {
                ids[i] = c.getInt(TripPlannerDB.TRIP_ID_COL);
                names[i] = c.getString(TripPlannerDB.TRIP_NAME_COL);
                startDates[i] = c.getString(TripPlannerDB.TRIP_START_DATE_COL);
                endDates[i] = c.getString(TripPlannerDB.TRIP_END_DATE_COL);
                dt_list = db.jsonToDtList(c.getString(TripPlannerDB.TRIP_DT_LIST_COL));

                Toast.makeText(this, Integer.toString(ids[i]), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, names[i], Toast.LENGTH_SHORT).show();
                Toast.makeText(this, startDates[i], Toast.LENGTH_SHORT).show();
                Toast.makeText(this, endDates[i], Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "id = " + dt_list.get(i).getID(), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "name = " + dt_list.get(i).getName(), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "type = " + dt_list.get(i).getType().toString(), Toast.LENGTH_SHORT).show();

                i++;
            }
        }

        // Close the Cursor and database connection
        db.closeConnection();
        db.closeCursor(c);
    }
}
