package com.example.android.quakereport;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by My on 25/03/17.
 */

public class Earthquake {

    private double magnitude;
    private String location;
    private long date;
    private String url;
    public Earthquake (double mMagnitude,String mLocation,long mDate,String mUrl)
    {
        magnitude = mMagnitude;
        location = mLocation;
        date = mDate;
        url = mUrl;
    }

    public double getMagitude()
    {
        return magnitude;
    }
    public String getDate()
    {
        Date dateObject = new Date(date);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM, yyyy");
        String dateToDisplay = dateFormatter.format(dateObject);

        return (dateToDisplay);
    }
    public String getLocation()
    {
        return location;
    }
    public String getTime()
    {
        Date dateObject = new Date(date);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("h:mm a");
        return dateFormatter.format(dateObject);
    }
    public String getUrl()
    {
        return url;
    }
}
