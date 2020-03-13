package com.example.tripplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TripPlannerDB {
    private static final int OPEN_SUCCESS = 1;
    private static final int OPEN_FAIL = -1;

    // Error codes
    public static final int INSERT_ERROR = -1;

    // DATABASE
    public static final String DB_NAME = "trip_planner.db";
    public static final int DB_VERSION = 1;

    // TRIP TABLE
    public static final String TRIP_TABLE = "trip";

    public static final String TRIP_ID = "id";
    public static int TRIP_ID_COL = 0;

    public static final String TRIP_NAME = "name";
    public static int TRIP_NAME_COL = 1;

    public static final String TRIP_START_DATE = "start_date";
    public static int TRIP_START_DATE_COL = 2;

    public static final String TRIP_END_DATE = "end_date";
    public static int TRIP_END_DATE_COL = 3;

    public static final String TRIP_DT_LIST = "dt_list"; // JSON object converted to a String
    public static int TRIP_DT_LIST_COL = 4;

    // Create/drop statements
    public static final String CREATE_TRIP_TABLE =
            "CREATE TABLE " + TRIP_TABLE + " (" +
            TRIP_ID         + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TRIP_NAME       + " TEXT NOT NULL, " +
            TRIP_START_DATE + " DATE, " +
            TRIP_END_DATE   + " DATE, " +
            TRIP_DT_LIST    + " TEXT NOT NULL);";

    public static final String DROP_TRIP_TABLE = "DROP TABLE IF EXISTS " + TRIP_TABLE;

    // Database and DBHelper
    public SQLiteDatabase db; // Allows opening database connections and executing queries // TEMPORARILY PUBLIC
    private TripPlannerDBHelper dbHelper; // Used to create the database
    private Type dtListType;

    // Constructor for the TripPlannerDB class
    public TripPlannerDB(Context context) {
        // Create the database helper object
        dbHelper = new TripPlannerDBHelper(context, DB_NAME, null, DB_VERSION);
        dtListType = new TypeToken<ArrayList<DT>>(){}.getType();
    }

    // Gets a SQLiteDatabase object for reading
    public int openReadableDB() {
        int statusCode = OPEN_SUCCESS;

        try {
            db = dbHelper.getReadableDatabase();
        }
        catch (SQLiteException dbReadableException) {
            statusCode = OPEN_FAIL;
            // Log the exception
        }

        return statusCode;
    }

    // Gets a SQLiteDatabase object for writing
    public int openWritableDB()
    {
        int statusCode = OPEN_SUCCESS;

        try {
            db = dbHelper.getWritableDatabase();
        }
        catch (SQLiteException dbWriteableException) {
            statusCode = OPEN_FAIL;
            // Log the exception
        }

        return statusCode;
    }

    // Insert into the database
    public long insertTrip(Trip theTrip) {
        long rowID = 0;

        if (theTrip != null) {
            ContentValues dbData = fillContentValues(theTrip);

            // Open database for writing
            if (this.openWritableDB() == OPEN_SUCCESS) {
                // Insert the data into the database
                try {
                    rowID = db.insertOrThrow(TRIP_TABLE, null, dbData);
                } catch (SQLException e) {
                    // Log the exception
                }
            }

            // Close the connection
            this.closeConnection();
        }

        return rowID;
    }

    // Gets a single Trip when given an index in the database
    public Trip getSingleTrip(int tripID) {
        // Open the database for reading
        openReadableDB();

        // Query the database for the Trip
        String selection = TRIP_ID + "= ?";
        String[] selectionArgs = { Integer.toString(tripID) };
        Cursor cursor = db.query(TRIP_TABLE, null, selection, selectionArgs,
                null, null, null);

        // Convert the Cursor to a Trip
        Trip theTrip = null;
        if (cursor.moveToFirst()) {
            theTrip = cursorToTrip(cursor);
        }

        // Close connections and return the Trip
        closeCursor(cursor);
        closeConnection();
        return theTrip;
    }

    // Get all Trips from the database
    public ArrayList<Trip> getAllTrips() {
        ArrayList<Trip> tripList = new ArrayList<>();

        // Open the database for reading
        openReadableDB();

        // Query the database for all rows and columns in the Trip table
        Cursor cursor = db.query(TRIP_TABLE, null, null,
                null, null, null, null);
        while (cursor.moveToNext() == true) {
            // Convert Cursor to Trip
            Trip theTrip = cursorToTrip(cursor);

            // Add the Trip object to the list
            tripList.add(theTrip);
        }

        closeCursor(cursor);
        closeConnection();
        return tripList;
    }

    // Closes a database connection
    private void closeConnection() { // TEMPORARILY PUBLIC
        if (db != null) {
            db.close();
        }
    }

    // Closes a Cursor object (Cursor is used to read data)
    private void closeCursor(Cursor c) {  // TEMPORARILY PUBLIC
        if (c != null) {
            c.close();
        }
    }

    // Gets data from a Cursor and creates a Trip object.
    // The Trip object is returned.
    private Trip cursorToTrip(Cursor c) {
        // Get dates
        final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMMM dd HH:mm:ss z yyyy");
        Date startDate = dateFormat.parse(c.getString(TripPlannerDB.TRIP_END_DATE_COL), new ParsePosition(0));
        Date endDate = dateFormat.parse(c.getString(TripPlannerDB.TRIP_END_DATE_COL), new ParsePosition(0));

        // Create the Trip object
        return new Trip(
                c.getInt(TripPlannerDB.TRIP_ID_COL),
                c.getString(TripPlannerDB.TRIP_NAME_COL),
                startDate,
                endDate,
                jsonToDtList(c.getString(TripPlannerDB.TRIP_DT_LIST_COL))
        );
    }

    // Fills a ContentValues object with Trip data.
    // The ContentValues object is returned.
    private ContentValues fillContentValues(Trip theTrip) {
        ContentValues dbData = new ContentValues();

        // Create JSON string from the dt_list
        String jsonObjStr = dtListToJson(theTrip);

        // Add the Trip data to a ContentValues object
        dbData.put(TRIP_NAME, theTrip.getName());
        dbData.put(TRIP_START_DATE, theTrip.getStart().toString());
        dbData.put(TRIP_END_DATE, theTrip.getEnd().toString());
        dbData.put(TRIP_DT_LIST, jsonObjStr);

        return dbData;
    }

    // Converts a ArrayList<DT> to a JSON string
    private String dtListToJson(Trip theTrip) {
        Gson gsonObj = new Gson(); // Create Gson object
        String jsonString = gsonObj.toJson(theTrip.getDt_list(), dtListType);

        return jsonString;
    }

    // Converts a JSON string into an ArrayList<DT>
    private ArrayList<DT> jsonToDtList(String jsonString) { // TEMPORARILY PUBLIC
        Gson gsonObj = new Gson();
        ArrayList<DT> dtList = gsonObj.fromJson(jsonString, dtListType);

        return dtList;
    }
}
