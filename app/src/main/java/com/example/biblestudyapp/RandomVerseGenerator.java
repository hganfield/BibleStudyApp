package com.example.biblestudyapp;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.OkHttpClient;

public class RandomVerseGenerator {

    private static final String apikey = "6c736f46a605d01365e639df97b2ee89";

    private static final String API_BASE_URL = "https://api.scripture.api.bible/v1/bibles/de4e12af7f28f599-02";

    private static String responseData;
    private final OkHttpClient httpClient;

    public RandomVerseGenerator() {
        httpClient = new OkHttpClient();

    }

    public String generateRandomVerse() {
        String url = API_BASE_URL + "/chapters/" + "20.6";
        new GetVerse().execute(url);


        return "responseData";
    }

    public String generateRandomVerseId() {
        int random = (int) (Math.random() * 31099) + 1;
        return String.valueOf(random);
    }

    public class GetVerse extends AsyncTask<String, Void, String> {

        public GetVerse() {

        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            String chapterContent = null;

            try {
                URL obj = new URL(url);
                System.out.println(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("api-key", "6c736f46a605d01365e639df97b2ee89");

                int responseCode = con.getResponseCode();
                if (responseCode >= 400) {
                    System.out.println(strings[0]);
                    System.out.println("Error: " + responseCode);
                    System.out.println(con.getResponseMessage());
                    return "1";

                }
                System.out.println("Success Getting verse!!!!!");
                System.out.println("Success: " + responseCode);

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();
                con.disconnect();

                JSONObject jsonObject = new JSONObject(response.toString());
                chapterContent = jsonObject.getJSONObject("data").getString("content");

            } catch (Exception e) {
                Log.e(TAG, "Error retrieving chapter content: " + e.getMessage());
            }

            return chapterContent;
        }

        @Override
        protected void onPostExecute(String chapterContent) {
            super.onPostExecute(chapterContent);
            if (chapterContent != null) {

            } else {
                // Handle error case
            }
        }

    }
}
