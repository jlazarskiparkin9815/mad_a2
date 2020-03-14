/*
    FILE             : TripPlannerDBHelper
    PROJECT          : PROG3150 - Assignment 2
    PROGRAMMER       : Conor Barr, Eric Emerson, Jack Parkinson, Maxim Mikheev, Rick Bloemert
    FIRST VERSION    : 2020-03-09
    DESCRIPTION      :
        This class assists with communication of various database components.
*/

package com.example.tripplanner;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TripPlannerDBHelper extends SQLiteOpenHelper {
<<<<<<< HEAD

=======
    /*
        FUNCTION    : TripPlannerDBHelper()
        DESCRIPTION :
            Constructor for the TripPlannerDBHelper class.
        PARAMETERS  :
            Context context: Provides information about the application environment.
            String name: The name of the database that's being communicated with.
            SQLiteDatabase.CursorFactory factory: Allows return Cursor objects in the
                TripPlannerDB class.
            int version: The database version
        RETURNS     : N/A
    */
>>>>>>> 76092cec7c6b10e0bb8758614b7b35d96767715d
    public TripPlannerDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /*
        FUNCTION    : onCreate()
        DESCRIPTION :
            Constructor for the TripPlannerDBHelper class.
        PARAMETERS  :
            SQLiteDatabase database: The object that TripPlannerDB uses to
                manage the database (update, delete, insert, etc.).
        RETURNS     : N/A
    */
    @Override
    public void onCreate(SQLiteDatabase database) {
        // Create the Trip table
        database.execSQL(TripPlannerDB.CREATE_TRIP_TABLE);
    }

    /*
        FUNCTION    : TripPlannerDBHelper()
        DESCRIPTION :
            Constructor for the TripPlannerDBHelper class.
        PARAMETERS  :
            SQLiteDatabase database: The object that TripPlannerDB uses to
                manage the database (update, delete, insert, etc.).
            int currentVersion: The current version of the database
            int newDBVersion: The new version of the database
        RETURNS     : N/A
    */
    @Override
    public void onUpgrade(SQLiteDatabase database, int currentVersion, int newDBVersion) {

    }
}
