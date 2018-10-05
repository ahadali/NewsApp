package com.example.android.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

final class QueryUtils {

    private static final String LOG_TAG = MainActivity.class.getName();

    /**
     * This is a private constructor which no one should used from outside the class.
     * It just meant to fetch News data.
     */
    private QueryUtils() {
    }

    /**
     * Query the Guardians dataset and return a list of {@link News} objects.
     */
    static List<News> fetchNewsData(String requestsUrl) throws JSONException {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        URL url = createUrl(requestsUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("LOG_TAG", "Problem making the HTTP request.", e);
        }

        return extractNewsFromJson(jsonResponse);

    }

    /* Method for creating URL for fetching Data */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    // Making HTTP Request
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Converting the {@link InputStream} into a String through BufferedReader which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    // Extract list of News from fetched Data
    private static List<News> extractNewsFromJson(String newsJSON) throws JSONException {

        // if JSON string is empty then we return null in this case.
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        List<News> news = new ArrayList<>();

        String title, sectionName, pubDate, url, author;

        try {

            JSONObject baseJsonResponse = new JSONObject(newsJSON);

            JSONObject responseObject = baseJsonResponse.getJSONObject("response");

            JSONArray resultsArray = responseObject.getJSONArray("results");

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject currentNews = resultsArray.getJSONObject(i);

                title = currentNews.getString("webTitle");

                sectionName = currentNews.getString("sectionName");

                pubDate = currentNews.getString("webPublicationDate");

                url = currentNews.getString("webUrl");

                // Parsing the tags array for retrieving author name
                try {
                    JSONArray tagsArray = currentNews.getJSONArray("tags");
                    JSONObject firstTag = tagsArray.getJSONObject(0);
                    author = firstTag.getString("webTitle");
                } catch (Exception e) {
                    // If author name is not available in JSON Response.
                    author = "Not Available";
                }

                // Removing extra characters from date through regex
                pubDate = pubDate.replaceAll("[a-zA-Z]", " ");

                news.add(new News(title, url, sectionName, pubDate, author));

            }

        } catch (JSONException e) {
            /* If there is problem parsing the JSONResponse then the error is thrown here
            and it is displayed via a log message.
             */
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }

        return news;

    }

}
