package com.example.dbtest;

import androidx.appcompat.app.AlertDialog;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        runWriteTest(db, testTrips);

        // READING TEST
        //runReadTest(db);

        // UPDATE TEST
        runUpdateTest(db);
    }

    private void runWriteTest(TripPlannerDB db, ArrayList<Trip> testTrips) {
        for (int i = 0; i < testTrips.size(); i++) {
            // insert trip into the database
            long rowID = db.insertTrip(testTrips.get(i));

            // Check for errors
            if (rowID == TripPlannerDB.INSERT_ERROR) {
                Toast.makeText(this, "Error inserting Trip #" + (i + 1), Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void runReadTest(TripPlannerDB db) {
        ArrayList<Trip> trips = db.getAllTrips();
        StringBuilder sb = new StringBuilder();
        if (trips != null) {
            // Test displaying the data
            for (int i = 0; i < trips.size(); i++) {
                sb.append("\nTrip #" + (i + 1) + "\n");
                sb.append("*******\n");
                sb.append("\t" + trips.get(i).getID());
                sb.append("\n\t" + trips.get(i).getName());
                sb.append("\n\t" + trips.get(i).getStart().toString());
                sb.append("\n\t" + trips.get(i).getEnd().toString() + "\n");
                for (int j = 0; j < trips.get(i).getDt_list().size(); j++) {
                    sb.append("\n\t\tDT #" + (j + 1));
                    sb.append("\n\t\t*****");
                    sb.append("\n\t\t" + trips.get(i).getDt_list().get(j).getID());
                    sb.append("\n\t\t" + trips.get(i).getDt_list().get(j).getName());
                    sb.append("\n\t\t" + trips.get(i).getDt_list().get(j).getType());
                    sb.append("\n");
                }
            }
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setMessage(sb.toString());
            AlertDialog d = b.create();
            d.show();
        }
        else {
            Toast.makeText(this, "No trips in the database...", Toast.LENGTH_SHORT);
        }

        // Test reading Trip by index
        StringBuilder sb2 = new StringBuilder();
        Trip t = db.getSingleTrip(1);
        sb2.append("Trip #1\n");
        sb2.append("*******\n");
        sb2.append("\n\t" + t.getID());
        sb2.append("\n\t" + t.getName());
        sb2.append("\n\t" + t.getStart());
        sb2.append("\n\t" + t.getEnd());

        t = db.getSingleTrip(3);
        sb2.append("\nTrip #2\n");
        sb2.append("*******\n");
        sb2.append("\n\t" + t.getID());
        sb2.append("\n\t" + t.getName());
        sb2.append("\n\t" + t.getStart());
        sb2.append("\n\t" + t.getEnd());

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setMessage(sb.toString());
        AlertDialog d = b.create();
        d.show();
    }

    private void runUpdateTest(TripPlannerDB db) {
        // Get the Trip
        Trip trip = db.getSingleTrip(3);

        StringBuilder sb = new StringBuilder();
        sb.append("Test #3 - Before Update");
        sb.append("\n\tid = " + trip.getID());
        sb.append("\n\tname = " + trip.getName());
        sb.append("\n\tstart = " + trip.getStart());
        sb.append("\n\tend = " + trip.getEnd());

        // Update the Trip
        trip.setName("UPDATED name for Trip 3 :)");

        db.updateTrip(trip);

        // Get the updated trip
        Trip updatedTrip = db.getSingleTrip(3);
        sb.append("\n\nTest #3 - After Update");
        sb.append("\n\tid = " + updatedTrip.getID());
        sb.append("\n\tname = " + updatedTrip.getName());
        sb.append("\n\tstart = " + updatedTrip.getStart());
        sb.append("\n\tend = " + updatedTrip.getEnd());

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setMessage(sb.toString());
        AlertDialog d = b.create();
        d.show();    }
	}
	