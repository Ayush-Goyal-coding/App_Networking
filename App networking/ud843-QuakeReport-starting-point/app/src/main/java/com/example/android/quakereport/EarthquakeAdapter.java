package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by My on 25/03/17.
 */

public class EarthquakeAdapter extends ArrayAdapter <Earthquake> {

    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquake)
    {
        super(context, 0, earthquake);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        /*
        creating views if no view is available
         */
        if (listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list,parent,false);
        }

        Earthquake current = getItem(position);    //creating a earthquake object to set it
        /*
        setting magnitude text view
         */
        TextView mag = (TextView)listItemView.findViewById(R.id.mag);
        DecimalFormat formatter = new DecimalFormat("0.0");

        mag.setText(formatter.format(current.getMagitude()));
        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) mag.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(((int) current.getMagitude()));

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);


        TextView locationTextView = (TextView)listItemView.findViewById(R.id.date);
        locationTextView.setText(current.getDate());


        TextView placeTextView = (TextView)listItemView.findViewById(R.id.place);
        String location = current.getLocation();
        if (location.contains("of"))
        {
            String locationName =location.substring( location.indexOf("of")+3,location.length());
            placeTextView.setText(locationName);
        }
        else
        placeTextView.setText(current.getLocation());

        TextView timeTextView = (TextView)listItemView.findViewById(R.id.time);
        timeTextView.setText(current.getTime());


        return listItemView;

    }

    public int getMagnitudeColor(int mag)
    {
        int magnitudeColor;
        switch (mag) {
            case 1:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude1);
                break;
            case 2:
                magnitudeColor = ContextCompat.getColor(getContext(),R.color.magnitude2);
                break;
            case 3:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude3);
                break;
            case 4:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude4);
                break;
            case 5:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude5);
                break;
            case 6:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude6);
                break;
            case 7:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude7);
                break;
            case 8:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude8);
                break;
            case 9:
                magnitudeColor = ContextCompat.getColor(getContext(), R.color.magnitude9);
                break;
            default:
                magnitudeColor = ContextCompat.getColor(getContext(),R.color.magnitude10plus);
            break;
        }
        return magnitudeColor;
    }
}
