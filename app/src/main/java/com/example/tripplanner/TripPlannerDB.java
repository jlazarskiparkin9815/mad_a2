/*
    FILE             : TripPlannerDB
    PROJECT          : PROG3150 - Assignment 2
    PROGRAMMER       : Conor Barr, Eric Emerson, Jack Parkinson, Maxim Mikheev, Rick Bloemert
    FIRST VERSION    : 2020-03-09
    DESCRIPTION      :
        This file communicates with the database within Android. This database is created by
        and only accessed by this application. This class uses the DBHelper class to open
        reading/writing connections to the database.
*/

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

    public SQLiteDatabase db; // Allows opening database connections and executing queries // TEMPORARILY PUBLIC
    private TripPlannerDBHelper dbHelper; // Used to create the database
    private Type dtListType;


    /*
        FUNCTION    : TripPlannerDB()
        DESCRIPTION : Constructor for the TripPlannerDB class.
        PARAMETERS  :
            Context context: Provides information about the application
                environment. Use the getApplicationContext() method when
                calling this method and pass the return value for this
                parameter.
        RETURNS     : N/A
    */
    public TripPlannerDB(Context context) {
        // Create the database helper object
        dbHelper = new TripPlannerDBHelper(context, DB_NAME, null, DB_VERSION);
        dtListType = new TypeToken<ArrayList<DT>>(){}.getType();
    }

    /*
        FUNCTION    : openWritableDB()
        DESCRIPTION : Gets a SQLiteDatabase object for reading.
        PARAMETERS  :
            none
        RETURNS     :
            int statusCode: OPEN_SUCCESS if the database connection was
                opened successfully, and OPEN_FAIL if it wasn't.
    */
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

    /*
        FUNCTION    : openWritableDB()
        DESCRIPTION : Opens the database connection for writing.
        PARAMETERS  :
            none
        RETURNS     :
            int statusCode: OPEN_SUCCESS if the database connection was
                opened successfully, and OPEN_FAIL if it wasn't.
    */
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

    /*
        FUNCTION    : insertTrip()
        DESCRIPTION : Inserts a Trip into the database.
        PARAMETERS  :
            Trip theTrip: The Trip object that's being inserted.
        RETURNS     :
            long rowID: The row ID of the Trip that was inserted.
    */
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

    /*
        FUNCTION    : updateTrip()
        DESCRIPTION : Updates a Trip in the database.
        PARAMETERS  :
            Trip theTrip: The Trip that's being updated
        RETURNS     : N/A
    */
    public void updateTrip(Trip theTrip) {
        // Create the where clause (specifies the Trip that's being updated)
        String whereClause = TRIP_ID + "= ?";
        String[] whereArgs = {Integer.toString(theTrip.getID())};

        // Fill a ContentValues object with data
        ContentValues updatedData = fillContentValues(theTrip);

        // Open the database for writing
        openWritableDB();

        // Update the Trip and then close the connection
        db.update(TRIP_TABLE, updatedData, whereClause, whereArgs);
        closeConnection();
    }

    /*
        FUNCTION    : deleteTrip()
        DESCRIPTION : Deletes a Trip from the database.
        PARAMETERS  :
            int id: The ID of the Trip that's being deleted.
        RETURNS     : N/A
    */
    public void deleteTrip(int id) {
        // Create the where clause (specifies the Trip that's being deleted)
        String whereClause = TRIP_ID + "= ?";
        String[] whereArgs = {Integer.toString(id)};

        // Open the database for writing
        openWritableDB();

        // Delete the Trip and then close the connection
        db.delete(TRIP_TABLE, whereClause, whereArgs);
        closeConnection();
    }

    /*
        FUNCTION    : getAllTrips()
        DESCRIPTION : Get all Trips from the database.
            The Trips are returned as an ArrayList.
        PARAMETERS  :
            none
        RETURNS     :
            ArrayList<Trip> tripList: The list of Trips that
                was pulled from the database.
    */
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

    /*
        FUNCTION    : closeConnection()
        DESCRIPTION : Closes a database connection.
        PARAMETERS  : void
        RETURNS     : N/A
    */
    private void closeConnection() {
        if (db != null) {
            db.close();
        }
    }

    /*
        FUNCTION    : closeCursor()
        DESCRIPTION : Closes a Cursor object (a Cursor object is
            used to read data from the database).
        PARAMETERS  :
            Cursor c: The Cursor object that's being closed.
        RETURNS     : N/A
    */
    private void closeCursor(Cursor c) {  // TEMPORARILY PUBLIC
        if (c != null) {
            c.close();
        }
    }

    /*
        FUNCTION    : cursorToTrip()
        DESCRIPTION :
            Gets data from a Cursor and creates a Trip object.
            The Trip object is returned.
        PARAMETERS  :
            Cursor c: Contains data pulled from the database.
        RETURNS     :
            Trip: The Trip object that was created from the data in 'Cursor c'.
    */
    private Trip cursorToTrip(Cursor c) {
        // Get dates
        final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMMM dd HH:mm:ss z yyyy");
        Date startDate = dateFormat.parse(c.getString(TripPlannerDB.TRIP_START_DATE_COL), new ParsePosition(0));
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

    /*
        FUNCTION    : fillContentValues()
        DESCRIPTION :
            Fills a ContentValues object with Trip data.
            ContentValues objects are used to insert data into the
            database (i.e. update rows and columns).
        PARAMETERS  :
            Trip theTrip: The Trip data that's being used to
                fill the ContentValues object.
        RETURNS     :
            ContentValues dbData: The ContentValues object that was
                created.
    */
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

    /*
        FUNCTION    : dtListToJson()
        DESCRIPTION : Converts a ArrayList<DT> to a JSON string.
        PARAMETERS  :
            Trip theTrip: The Trip object that's being converted
                to a JSON string.
        RETURNS     :
            String jsonString: The JSON string which contains
                the Trips dt_list.
    */
    private String dtListToJson(Trip theTrip) {
        Gson gsonObj = new Gson(); // Create Gson object
        String jsonString = gsonObj.toJson(theTrip.getDt_list(), dtListType);

        return jsonString;
    }

    /*
        FUNCTION    : jsonToDtList()
        DESCRIPTION : Converts a JSON string into an ArrayList<DT>.
        PARAMETERS  :
            String jsonString: The JSON string that's being converted
                to ArrayList<DT>.
        RETURNS     :
            ArrayList<DT>: The list of DT objects
    */
    private ArrayList<DT> jsonToDtList(String jsonString) { // TEMPORARILY PUBLIC
        Gson gsonObj = new Gson();
        ArrayList<DT> dtList = gsonObj.fromJson(jsonString, dtListType);

        return dtList;
    }
}
