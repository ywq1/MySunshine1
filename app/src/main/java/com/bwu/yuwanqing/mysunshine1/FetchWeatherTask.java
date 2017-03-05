package com.bwu.yuwanqing.mysunshine1;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yuwanqing on 2017-03-05.
 */
//获取天气任务
    //异步任务（启动任务执行的输入参数、后台任务执行的进度、后台计算结果的类型
public class FetchWeatherTask extends AsyncTask<Void, Void, Void> {
    private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();//返回源代码中的基础类的简单名臣
    @Override
    protected Void doInBackground(Void... params) {//接收输入参数，返回计算结果
        //These two need to be declared outside the try/catch
        //so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;
        try {
            //Construct the URL for the OpenWeatherMap query
            //Possible parameters are available at OWM'SForecast API  page, at http://openweathermap.org/API#forecast
            URL url = new URL("https://api.thinkpage.cn/v3/weather/daily.json?key=wqs1abygssm6ql2x&location=beijing&language=zh-Hans&unit=c&start=0&days=5");//URL对象
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
            Log.e(LOG_TAG, "Error", e);
            //If the code didn't successful get the weather data, there's no point in attempting to parse it.
            return null;
        } finally {//不管执不执行try和catch单一定会执行finally
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return null;
    }
}
