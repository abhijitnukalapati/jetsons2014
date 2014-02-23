package com.jetsons2014.models;

/**
 * Created by bradtop on 2/22/14.
 */
public class MapLocation {

    private double mLongitude;
    private double mLatitude;
    private String mName;
    private String mSecondaryText;

    public MapLocation(double longitude, double latitude, String name, String secondary) {
        mLongitude = longitude;
        mLatitude = latitude;
        mName = name;
        mSecondaryText = secondary;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public String getName() {
        return mName;
    }
    
    public String getSecondary() {
        return mSecondaryText;
    }
}
