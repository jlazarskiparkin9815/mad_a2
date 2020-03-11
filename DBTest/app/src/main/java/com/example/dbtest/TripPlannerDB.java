package com.example.dbtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TripPlannerDB {
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
    public void openReadableDB() { db = dbHelper.getReadableDatabase(); } // TEMPORARILY PUBLIC
    // Gets a SQLiteDatabase object for writing
    public void openWritableDB() { db = dbHelper.getWritableDatabase(); } // TEMPORARILY PUBLIC

    // Insert into the database
    public long insertTrip(Trip theTrip) {
        long rowID = 0;

        String jsonObjStr = null;
        if (theTrip != null) {
            // Convert to List<DT> to a JSON object
            jsonObjStr = dtListToJson(theTrip);

            // Add the Trip data to a ContentValues object
            ContentValues dbData = new ContentValues();
            dbData.put(TRIP_NAME, theTrip.getName());
            dbData.put(TRIP_START_DATE, theTrip.getStart().toString());
            dbData.put(TRIP_END_DATE, theTrip.getEnd().toString());
            dbData.put(TRIP_DT_LIST, jsonObjStr);

            // Open database for writing
            this.openWritableDB();

            // Insert the data into the database
            try {
                rowID = db.insertOrThrow(TRIP_TABLE, null, dbData);
            }
            catch (SQLException e) {
                // Log the exception
            }

            // Close the connection
            this.closeConnection();
        }

        return rowID;
    }

    // Closes a database connection
    public void closeConnection() { // TEMPORARILY PUBLIC
        if (db != null) {
            db.close();
        }
    }

    // Closes a Cursor object (Cursor is used to read data)
    public void closeCursor(Cursor c) {  // TEMPORARILY PUBLIC
        if (c != null) {
            c.close();
        }
    }


    // Converts a ArrayList<DT> to a JSON string
    private String dtListToJson(Trip theTrip) {
        Gson gsonObj = new Gson(); // Create Gson object
        String jsonString = gsonObj.toJson(theTrip.getDt_list(), dtListType);

        return jsonString;
    }

    // Converts a JSON string into an ArrayList<DT>
    public ArrayList<DT> jsonToDtList(String jsonString) { // TEMPORARILY PUBLIC
        Gson gsonObj = new Gson();
        ArrayList<DT> dtList = gsonObj.fromJson(jsonString, dtListType);

        return dtList;
    }
}
