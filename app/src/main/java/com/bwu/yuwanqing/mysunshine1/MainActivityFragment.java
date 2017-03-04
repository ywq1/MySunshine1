package com.bwu.yuwanqing.mysunshine1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
        //These two need to be declared outside the try/catch
        //so that they can be closed in the finalllu block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader =null;//bufferedreader可以一次读取大量数据
        //Will contain the raw JSON response as a string .
        String forecastJsonStr = null;
        try {
            //Construct the URL for the OpenWeatherMap query
            //Possible parameters are available at OWM'SForecast API  page, at http://openweathermap.org/API#forecast
            URL url = new URL("https://api.thinkpage.cn/v3/weather/now.json?key=wqs1abygssm6ql2x&location=beijing&language=zh-Hans&unit=c");//URL对象
            //Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();//使用URL打开一个链接
            urlConnection.setRequestMethod("GET");//使用get请求
            urlConnection.connect();
            //Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();//获取输入流，此时才真正建立连接
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                //Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {//readline每次读回来都是一行一行
                //Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                //But it does make debugging a *lot* easier if you print out the completed buffer for debugging.
                buffer.append(line + "\n");//附加
            }
            if (buffer.length() == 0) {
                //Stream was empty. No point in parsing.
                return null;
            }
            forecastJsonStr = buffer.toString();
        } catch (IOException e) {//若出现异常会报错
            Log.e("PlaceholderFragment", "Error", e);
            //If the code didn't successful get the weather data, there's no point in attempting to parse it.
            return null;
        } finally{//不管执不执行try和catch单一定会执行finally
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                }catch (final IOException e){
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
        return rootView;
    }
}
