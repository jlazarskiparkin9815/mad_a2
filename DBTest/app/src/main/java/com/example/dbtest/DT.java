/*
    FILE             : DT
    PROJECT          : PROG3150 - Assignment 1
    PROGRAMMER       : Conor Barr, Eric Emerson, Jack Parkinson, Maxim Mikheev, Rick Bloemert
    FIRST VERSION    : 2020-02-02
    DESCRIPTION      :
        This is the object that's used to hold Destinations/Transits
        that are created in the StartTripActivity. Destinations are
        places that the user is staying on their Trip and Transit
        are methods of transportation.
*/

package com.example.dbtest;

import androidx.appcompat.app.AppCompatActivity;

public class DT extends AppCompatActivity {

    transient public final static int ID_NOT_SET = -1;

    public enum DT_Type {
        DESTINATION,
        TRANSIT
    }

    private int dtID;
    private DT_Type type; // Whether the trip is a Destination or Transit (or not selected)
    private String name; // The name given to the Destination or Transit

    /*
        FUNCTION: DT
        DESCRIPTION: Constructor for the DT class. Allows specify a name (e.g. Motel/Hotel,
                     or Car/Bus).
        PARAMETERS:
            String _name: The name of the Destination or Transit.
        RETURNS: N/A
    */
    public DT (String _name) {
        this.type = DT_Type.DESTINATION;
        this.name = _name;
    }

    /*
        FUNCTION: getName()
        DESCRIPTION: Accessor for 'private String name'. Allows retrieving
                     the value of the data member.
        PARAMETERS: N/A
        RETURNS: String: The value of the 'String name'.
    */
    public String getName() { return this.name; }

    /*
        FUNCTION: getType()
        DESCRIPTION: Accessor for 'private DT_Type type'. Allows retrieving
                     the value of the data member.
        PARAMETERS: N/A
        RETURNS: DT_Type: The value of the 'DT_Type type'.
    */
    public DT_Type getType() { return this.type; }


    /*
        FUNCTION: getID()
        DESCRIPTION: Accessor for 'private int dtID'. Allows retrieving
                     the value of the data member.
        PARAMETERS: N/A
        RETURNS: int: The value of the 'int dtID'.
    */
    public int getID(){ return this.dtID; }

    /*
    FUNCTION: getName()
    DESCRIPTION: Accessor for 'private String name'. Allows retrieving
                 the value of the data member.
    PARAMETERS: N/A
    RETURNS: String: The value of the 'String name'.
*/
    public void setName(String newName) { this.name = newName; }

    /*
        FUNCTION: getType()
        DESCRIPTION: Accessor for 'private DT_Type type'. Allows retrieving
                     the value of the data member.
        PARAMETERS: N/A
        RETURNS: DT_Type: The value of the 'DT_Type type'.
    */
    public void setType(DT_Type newType) { this.type = newType; }


    /*
        FUNCTION: getID()
        DESCRIPTION: Accessor for 'private int dtID'. Allows retrieving
                     the value of the data member.
        PARAMETERS: N/A
        RETURNS: int: The value of the 'int dtID'.
    */
    public void setID(int newDtID){ this.dtID = newDtID; }
}
