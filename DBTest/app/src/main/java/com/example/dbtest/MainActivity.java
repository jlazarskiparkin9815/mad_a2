package com.example.dbtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

        public static final String[] startDates = {
                "01/10/3333",
                "02/10/3333",
                "03/10/3333",
                "04/10/3333",
                "05/10/3333",
        };
        public static final String[] endDates = {
                "01/10/3333",
                "02/10/3333",
                "03/10/3333",
                "04/10/3333",
                "05/10/3333",
        };
        public static final String[] dtNames = {
                "Test DT #1",
                "Test DT #2",
                "Test DT #3",
                "Test DT #4",
                "Test DT #5",
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
                    new SimpleDateFormat("dd/MM/yyyy").parse(Tester.startDates[i], new ParsePosition(0)),
                    new SimpleDateFormat("dd/MM/yyyy").parse(Tester.endDates[i], new ParsePosition(0))
                    );

            // Add the test DT
            for (int j = 0; j < Tester.numTests; j++) {
                DT dt = newTrip.CreateDestinationTransit(this, null, Tester.dtNames[j], DT.DT_Type.DESTINATION, DT.ID_NOT_SET);
                newTrip.addDestination(dt);
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

        // READING TEST
        ArrayList<Trip> trips = db.getAllTrips();
        if (trips != null) {
            // Test displaying the data
            for (int i = 0; i < trips.size(); i++) {
                Toast.makeText(this, Integer.toString(trips.get(i).getID()), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, trips.get(i).getName(), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, trips.get(i).getStart().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, trips.get(i).getEnd().toString(), Toast.LENGTH_SHORT).show();
                for (int j = 0; j < trips.get(i).getDt_list().size(); j++) {
                    Toast.makeText(this, "id = " + trips.get(i).getDt_list().get(j).getID(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "name = " + trips.get(i).getDt_list().get(j).getName(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "type = " + trips.get(i).getDt_list().get(j).getType(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        else {
            Toast.makeText(this, "No trips in the database...", Toast.LENGTH_SHORT);
        }

        // Test reading Trip by index
        Trip t = db.getSingleTrip(1);
        Toast.makeText(this, Integer.toString(t.getID()), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, t.getName(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, t.getStart().toString(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, t.getEnd().toString(), Toast.LENGTH_SHORT).show();

        t = db.getSingleTrip(3);
        Toast.makeText(this, Integer.toString(t.getID()), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, t.getName(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, t.getStart().toString(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, t.getEnd().toString(), Toast.LENGTH_SHORT).show();
    }
}
