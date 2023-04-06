package com.example.biblestudyapp;

import static android.content.ContentValues.TAG;

import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.grpc.okhttp.internal.proxy.HttpUrl;
import io.grpc.okhttp.internal.proxy.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BibleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BibleFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public BibleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BibleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BibleFragment newInstance(String param1, String param2) {
        BibleFragment fragment = new BibleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private static final String apikey = "6c736f46a605d01365e639df97b2ee89";

    private static Map<String,String> verseMap;
    private static Map<String, String> bibleIds;

    public interface BibleIDsCallback {
        void onBibleIDsReceived();
    }

    private void retrieveBibleIds(BibleIDsCallback callback) {
        System.out.println("In RetrieveBibleIDS!!!!!");

        String url = "https://api.scripture.api.bible/v1/bibles";
        new HttpGetTask(callback).execute(url);
    }

    private class HttpGetTask extends AsyncTask<String, Void, String> {

        private final BibleIDsCallback callback;

        public HttpGetTask(BibleIDsCallback callback) {
            this.callback = callback;
        }
        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            String result = null;
            try {
                URL urlObj = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(10000); // 10 seconds
                conn.setRequestProperty("api-key", apikey);
                conn.connect();
                int responseCode = conn.getResponseCode();
                if(responseCode >= 400){
                    System.out.println(params[0]);
                    System.out.println("Error: " + responseCode);
                    System.out.println(conn.getResponseMessage());
                    return "1";

                }
                System.out.println("Connection!!!!!");

                InputStream in = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                result = sb.toString();
                in.close();
                reader.close();
                conn.disconnect();
            } catch (Exception e) {
                Log.e(TAG, "Error: " + e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            // Use the result of the HTTP request
            try {
                if(result == null){
                    return;
                }
                System.out.println("FillingMap!!!!!");

                JSONObject json = new JSONObject(result);
                JSONArray data = json.getJSONArray("data");
                bibleIds = new HashMap<>();
                for (int i = 0; i < data.length(); i++) {
                    JSONObject bible = data.getJSONObject(i);
                    String id = bible.getString("id");
                    String name = bible.getString("name");
                    bibleIds.put(name,id);
                }
                callback.onBibleIDsReceived();
                // Do something with the map of Bible IDs
            } catch (JSONException e) {
                Log.e(TAG, "Error parsing JSON: " + e.getMessage());
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bible, container, false);
        String trans = "KJV";
        String reference ="GEN.1";
        System.out.println("BEFORECALL!!!!!");
        verseMap = new HashMap<String,String>();
        verseMap.put("ASV", "06125adad2d5898a-01");
        verseMap.put("KJV", "de4e12af7f28f599-02");
        verseMap.put("Reina Valera 1909", "592420522e16049f-01");
        verseMap.put("The Holy Bible in Simple Spanish", "b32b9d1b64b4ef29-01");
        verseMap.put("VBL", "482ddd53705278cc-01");
        verseMap.put("grcF35", "5e29945cf530b0f6-01");
        verseMap.put("Vietnamese Bible 1934", "1b878de073afef07-01");
        verseMap.put("Open Vietnamese Contemporary Bible", "5cc7093967a0a392-01");
        verseMap.put("Thai KJV", "2eb94132ad61ae75-01");
        verseMap.put("Open Kiswahili Contemporary Version", "611f8eb23aec8f13-01");
        verseMap.put("Swedish Core Bible", "fa4317c59f0825e0-01");
        verseMap.put("Gdansk Bible", "1c9761e0230da6e0-01");
        verseMap.put("Catholic Dutch Bible 1939", "ead7b4cc5007389c-01");
        verseMap.put("Diodati Bible", "41f25b97f468e10b-01");
        verseMap.put("Plain Indonesian Translation", "2dd568eeff29fb3c-02");
        verseMap.put("Indian Revised Version (Hindi)", "1e8ab327edbce67f-01");
        verseMap.put("Open Hebrew Living New Testament", "a8a97eebae3c98e4-01");
        verseMap.put("Elderfelder Translation", "f492a38d0e52db0f-01");
        verseMap.put("German Luther Bible", "926aa5efbc5e04e2-01");
        verseMap.put("German Unrevised elberfelder Bible", "95410db44ef800c1-01");
        verseMap.put("Czech Kralická Bible 1613", "c61908161b077c4c-01");
        verseMap.put("Indian Revised Version (Bengali)", "4c3eda00cd317568-01");
        verseMap.put("NTPrv", "17c44f6c89de00db-01");
        verseMap.put("New Arabic Version", "b17e246951402e50-01");


        retrieveBibleIds(new BibleIDsCallback() {
            @Override
            public void onBibleIDsReceived() {
                System.out.println("Recieved!!!!!");
                getBible(trans,reference,view);
            }
        });

        return view;
    }

    public void getBible(String trans,String reference, View bible) {
        System.out.println("Getting the verse!!!!!");
        //String urlParameters = "?content-type=json&include-chapter-numbers=false&include-verse-numbers=true&include-footnotes=false&include-footnote-body=false&include-audio-link=false&include-book-titles=false";
        reference = parseRef(reference);
        String request = "https://api.scripture.api.bible/v1/bibles/" + verseMap.get(trans) + "/chapters/" + reference;
        //WebView webView = (WebView) getView().findViewById(R.id.webview);
        new GetVerse(bible).execute(request);
    }


    private class GetVerse extends AsyncTask<String, Void, String> {
        // ...
        private View view;

        public GetVerse(View view) {
            this.view = view;
        }
        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            String chapterContent = null;

            try {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("api-key", apikey);

                int responseCode = con.getResponseCode();
                if(responseCode >= 400){
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
                // Create a new TextView to display the chapter content
                TextView textView = new TextView(view.getContext());
                textView.setTextSize(18);
                textView.setPadding(16, 16, 16, 16);
                textView.setText(Html.fromHtml(chapterContent));

                // Split the chapter content into separate verses using a regular expression
                String[] verses = chapterContent.split("<br>\\s*");

                // Add a click listener to each verse
                for (int i = 0; i < verses.length; i++) {
                    final int verseNumber = i + 1;
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Handle verse click
                            Toast.makeText(view.getContext(), "Verse " + verseNumber + " clicked", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                // Add the TextView to the layout
                LinearLayout layout = view.findViewById(R.id.layout);
                layout.addView(textView);
            } else {
                // Handle error case
                //Toast.makeText(context, "Error retrieving chapter", Toast.LENGTH_SHORT).show();
            }
        }

    }



    public void nonESVPrintText(JSONObject datas) throws JSONException {
        JSONArray content = datas.getJSONObject("data").getJSONArray("content");
        String pastetext = "";
        String reference = datas.getJSONObject("data").getString("reference");
        String html = "<h2>" + reference + "</h2><br>";
        for (int i = 0; i < content.length(); i++) {
            JSONArray items = content.getJSONObject(i).getJSONArray("items");
            for (int j = 0; j < items.length(); j++) {
                JSONObject item = items.getJSONObject(j);
                if (item.getString("type").equals("tag") && item.getString("name").equals("verse")) {
                    html += "<span>" + pastetext + "</span>";
                    pastetext = "";
                }
                if (item.getString("type").equals("text")) {
                    pastetext += item.getString("text");
                } else {
                    JSONArray subItems = item.getJSONArray("items");
                    for (int k = 0; k < subItems.length(); k++) {
                        JSONObject subItem = subItems.getJSONObject(k);
                        if (item.getString("name").equals("verse")) {
                            pastetext += "<b>" + subItem.getString("text") + "</b> ";
                        } else {
                            pastetext += subItem.getString("text");
                        }
                    }
                }
            }
        }
        html += "<span>" + pastetext + "</span>";
        System.out.println(html);
        // Add code to display the HTML on a webpage or save it to a file
    }

    public String parseRef(String ref) {
        Map<String, String> bookMap = new HashMap<String, String>();
        bookMap.put("Genesis", "GEN");
        bookMap.put("Exodus", "EXO");
        bookMap.put("Leviticus", "LEV");
        String nums = "1234567890";
        ref = ref.replace(":", ".");
        String[] myArray = ref.split("%20");
        //System.out.println(ref);
        //System.out.println(Arrays.toString(myArray));
        if (myArray.length == 1) {
            return ref;
        } else {
            String toReturn;
            int i;
            if (nums.indexOf(myArray[0]) == -1) {
                toReturn = bookMap.get(myArray[0]);
                i = 1;
            } else {
                toReturn = bookMap.get(myArray[0] + " " + myArray[1]);
                i = 2;
            }
            for (; i < myArray.length; i++) {
                toReturn += ("." + myArray[i]);
            }
            System.out.println(toReturn);
            return toReturn;
        }
    }
}
