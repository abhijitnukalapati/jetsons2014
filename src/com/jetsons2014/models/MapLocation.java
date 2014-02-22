package com.jetsons2014.models;

/**
 * Created by bradtop on 2/22/14.
 */
public class MapLocation {

    private long mLongitude;
    private long mLatitude;
    private String mName;
    private String mStoreName;

    public MapLocation(long longitude, long latitude, String name) {
        mLongitude = longitude;
        mLatitude = latitude;
        mName = name;
    }

    public long getLongitude() {
        return mLongitude;
    }

    public long getLatitude() {
        return mLatitude;
    }

    public String getName() {
        return mName;
    }
    
    public String getStoreName() {
        return mStoreName;
    }
}
