package com.example.tripplanner;

// Data members:
//  - type (whether it's a Destination or Transit) [enum]
//  - name (the name that the user gives the Destination/Trip)[string]
//      Destination example: Hotel, Motel, etc.
//      Transit: Car, Bus, or Train
public class DT {
    public enum DT_Type {
        NOT_SELECTED,
        DESTINATION,
        TRANSIT
    }

    private DT_Type type; // Whether the trip is a Destination or Transit (or not selected)
    private String name; // The name given to the Destination or Transit

    // Constructor for the DT class
    public DT (DT_Type _type, String _name) {
        this.type = _type;
        this.name = _name;
    }

    /* Accessors/Mutators */
    public DT_Type getType() {
        return this.type;
    }

    public void setType(DT_Type _type)
    {
        this.type = _type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String _name) {
        this.name = _name;
    }
}
