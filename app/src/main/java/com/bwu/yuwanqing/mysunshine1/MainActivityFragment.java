package com.bwu.yuwanqing.mysunshine1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuwanqing on 2017-03-04.
 */
public class MainActivityFragment extends Fragment {
    ArrayAdapter<String> mForecastAdapter;
    public MainActivityFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Create some dummy data for the ListView. Here's a sample weekly forecast
        String[] data = {
                "Mon 2/11 - Sunny - 31/17",
                "Tue 2/12 - Foggy - 21/8",
                "Wed 2/13 - Cloudy - 22/17",
                "Thurs 2/14 - Rainy - 18/11",
                "Fri 2/15 - Foggy -21/10",
                "Sat 2/16 - TRAPPED IN WEATHERSTATION -23/18",
                "Sun 2/17 - Sunny - 20/7"
        };
        List<String> weekForecast = new ArrayList<String>(Arrays.asList(data));
        //Now that we have some dummy forecast data, create an ArrayAdapter.
        //The ArrayAdapter will take data from a source (like ore dummy forecast) and
        //use it to populate the ListView it's attached to.
        mForecastAdapter =
                new ArrayAdapter<String>(
                        getActivity(),
                        R.layout.list_item_forecast,
                        R.id.list_item_forecast_textview,
                        weekForecast);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        //Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(mForecastAdapter);
        return rootView;
    }
}
