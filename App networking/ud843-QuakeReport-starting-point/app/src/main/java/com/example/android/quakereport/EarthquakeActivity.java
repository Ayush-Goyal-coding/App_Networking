/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {


    private static final String URL_OF_SITE = "https://earthquake.usgs.gov/fdsnws/event/1/query";;
    private EarthquakeAdapter mAdapter;
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private TextView mEmptyStateTextView;
    private ProgressBar SpinnerProgress;


        @Override
        public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            String minMagnitude = sharedPrefs.getString(
                    getString(R.string.settings_min_magnitude_key),
                    getString(R.string.settings_min_magnitude_default));
            Uri baseUri = Uri.parse(URL_OF_SITE);
            Uri.Builder uriBuilder = baseUri.buildUpon();

            uriBuilder.appendQueryParameter("format", "geojson");
            uriBuilder.appendQueryParameter("limit", "10");
            uriBuilder.appendQueryParameter("minmag", minMagnitude);
            uriBuilder.appendQueryParameter("orderby", "time");

            return new EarthquakeLoader(this, uriBuilder.toString());
        }

        @Override
        public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
            //  we can clear out our existing data.
            mAdapter.clear();
            Toast.makeText(this, "Done",
                    Toast.LENGTH_SHORT).show();
            if (earthquakes != null && !earthquakes.isEmpty()) {
                mAdapter.addAll(earthquakes);
            }
            mEmptyStateTextView.setText(R.string.Empty);
          SpinnerProgress.setVisibility(View.GONE);
        }

        @Override
        public void onLoaderReset(Loader<List<Earthquake>> loader) {
            // Loader reset, so we can clear out our existing data.
            mAdapter.clear();
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.earthquake_activity);



        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        // Create a new {@link ArrayAdapter} of earthquakes
      // final EarthquakeAdapter adapter = new  EarthquakeAdapter(this,earthquakes);
      //  NetworkUpdate task = new NetworkUpdate();
    //     task.execute();

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());
            SpinnerProgress = (ProgressBar) findViewById(R.id.spinner);
            mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

            //CHECK for internet connection
            ConnectivityManager cm =
                    (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();

           if (isConnected==false)
            {
                mEmptyStateTextView.setText(R.string.NoNet);
                SpinnerProgress.setVisibility(View.GONE);
            }
else
            {

                // Get a reference to the LoaderManager, in order to interact with loaders.
                LoaderManager loaderManager = getLoaderManager();
                // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                // because this activity implements the LoaderCallbacks interface).
                loaderManager.initLoader(1, null, this);

                //   SpinnerProgress.setVisibility(View.VISIBLE);


                earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                        // Find the current earthquake that was clicked on
                        Earthquake currentEarthquake = mAdapter.getItem(position);


                        // Convert the String URL into a URI object (to pass into the Intent constructor)
                        Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());
                        // Create a new intent to view the earthquake URI
                        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                        // Send the intent to launch a new activity
                        startActivity(websiteIntent);
                    }
                });

                earthquakeListView.setAdapter(mAdapter);

                earthquakeListView.setEmptyView(mEmptyStateTextView);// if adapter is empty set it to empty view

            }

            }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


        }




