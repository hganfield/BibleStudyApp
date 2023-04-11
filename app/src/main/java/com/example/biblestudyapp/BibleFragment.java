package com.example.biblestudyapp;

import static android.content.ContentValues.TAG;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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

    public static Map<String, String> bookMap;

    public static Map<String, Integer> bookChapterMap;

    public static String book_ref;

    public static String chapter_ref;

    public static String reference;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bible, container, false);
        String trans = "KJV";

        //System.out.println("BEFORECALL!!!!!");

        verseMap = new HashMap<String, String>();
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

        bookMap = new HashMap<String, String>() {{
            put("Genesis", "GEN");
            put("Exodus", "EXO");
            put("Leviticus", "LEV");
            put("Numbers", "NUM");
            put("Deuteronomy", "DEU");
            put("Joshua", "JOS");
            put("Judges", "JDG");
            put("Ruth", "RUT");
            put("1 Samuel", "1SA");
            put("2 Samuel", "2SA");
            put("1 Kings", "1KI");
            put("2 Kings", "2KI");
            put("1 Chronicles", "1CH");
            put("2 Chronicles", "2CH");
            put("Ezra", "EZR");
            put("Nehemiah", "NEH");
            put("Ester", "EST");
            put("Job", "JOB");
            put("Psalm", "PSA");
            put("Proverbs", "PRO");
            put("Ecclesiastes", "ECC");
            put("Song of Solomon", "SNG");
            put("Isaiah", "ISA");
            put("Jeremiah", "JER");
            put("Lamentations", "LAM");
            put("Ezekiel", "EZK");
            put("Daniel", "DAN");
            put("Hosea", "HOS");
            put("Joel", "JOL");
            put("Amos", "AMO");
            put("Obadiah", "OBA");
            put("Jonah", "JON");
            put("Micah", "MIC");
            put("Nahum", "NAM");
            put("Habakkuk", "HAB");
            put("Zephaniah", "ZEP");
            put("Haggai", "HAG");
            put("Zechariah", "ZEC");
            put("Malachi", "MAL");
            put("Matthew", "MAT");
            put("Mark", "MRK");
            put("Luke", "LUK");
            put("John", "JHN");
            put("Acts", "ACT");
            put("Romans", "ROM");
            put("1 Corinthians", "1CO");
            put("2 Corinthians", "2CO");
            put("Galatians", "GAL");
            put("Ephesians", "EPH");
            put("Philippians", "PHP");
            put("Colossians", "COL");
            put("1 Thessalonians", "1TH");
            put("2 Thessalonians", "2TH");
            put("1 Timothy", "1TI");
            put("2 Timothy", "2TI");
            put("Titus", "TIT");
            put("Philemon", "PHM");
            put("Hebrews", "HEB");
            put("James", "JAS");
            put("1 Peter", "1PE");
            put("2 Peter", "2PE");
            put("1 John", "1JN");
            put("2 John", "2JN");
            put("3 John", "3JN");
            put("Jude", "JUD");
            put("Revelation", "REV");
        }};

        Spinner books_selection = view.findViewById(R.id.spinnerbooks);
        Spinner chapter_selection = view.findViewById(R.id.spinnerchapters);
        String[] books = bookMap.keySet().toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, books);
        books_selection.setAdapter(adapter);

        bookChapterMap = new HashMap<>();
        bookChapterMap.put("Genesis", 50);
        bookChapterMap.put("Exodus", 40);
        bookChapterMap.put("Leviticus", 27);
        bookChapterMap.put("Numbers", 36);
        bookChapterMap.put("Deuteronomy", 34);
        bookChapterMap.put("Joshua", 24);
        bookChapterMap.put("Judges", 21);
        bookChapterMap.put("Ruth", 4);
        bookChapterMap.put("1 Samuel", 31);
        bookChapterMap.put("2 Samuel", 24);
        bookChapterMap.put("1 Kings", 22);
        bookChapterMap.put("2 Kings", 25);
        bookChapterMap.put("1 Chronicles", 29);
        bookChapterMap.put("2 Chronicles", 36);
        bookChapterMap.put("Ezra", 10);
        bookChapterMap.put("Nehemiah", 13);
        bookChapterMap.put("Ester", 10);
        bookChapterMap.put("Job", 42);
        bookChapterMap.put("Psalm", 150);
        bookChapterMap.put("Proverbs", 31);
        bookChapterMap.put("Ecclesiastes", 12);
        bookChapterMap.put("Song of Solomon", 8);
        bookChapterMap.put("Isaiah", 66);
        bookChapterMap.put("Jeremiah", 52);
        bookChapterMap.put("Lamentations", 5);
        bookChapterMap.put("Ezekiel", 48);
        bookChapterMap.put("Daniel", 12);
        bookChapterMap.put("Hosea", 14);
        bookChapterMap.put("Joel", 3);
        bookChapterMap.put("Amos", 9);
        bookChapterMap.put("Obadiah", 1);
        bookChapterMap.put("Jonah", 4);
        bookChapterMap.put("Micah", 7);
        bookChapterMap.put("Nahum", 3);
        bookChapterMap.put("Habakkuk", 3);
        bookChapterMap.put("Zephaniah", 3);
        bookChapterMap.put("Haggai", 2);
        bookChapterMap.put("Zechariah", 14);
        bookChapterMap.put("Malachi", 4);
        bookChapterMap.put("Matthew", 28);
        bookChapterMap.put("Mark", 16);
        bookChapterMap.put("Luke", 24);
        bookChapterMap.put("John", 21);
        bookChapterMap.put("Acts", 28);
        bookChapterMap.put("Romans", 16);
        bookChapterMap.put("1 Corinthians", 16);
        bookChapterMap.put("2 Corinthians", 13);
        bookChapterMap.put("Galatians", 6);
        bookChapterMap.put("Ephesians", 6);
        bookChapterMap.put("Philippians", 4);
        bookChapterMap.put("Colossians", 4);
        bookChapterMap.put("1 Thessalonians", 5);
        bookChapterMap.put("2 Thessalonians", 3);
        bookChapterMap.put("1 Timothy", 6);
        bookChapterMap.put("2 Timothy", 4);
        bookChapterMap.put("Titus", 3);
        bookChapterMap.put("Philemon", 1);
        bookChapterMap.put("Hebrews", 13);
        bookChapterMap.put("James", 5);
        bookChapterMap.put("1 Peter", 5);
        bookChapterMap.put("2 Peter", 3);
        bookChapterMap.put("1 John", 5);


        books_selection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chapter_selection.setVisibility(view.VISIBLE);
                String book = adapterView.getItemAtPosition(i).toString();
                book_ref = bookMap.get(book);
                System.out.println(book_ref);
                int number_of_books = bookChapterMap.get(book);
                String[] chapters;
                if (number_of_books == 1) {
                    chapters = new String[]{"1"};
                } else {
                    chapters = new String[number_of_books];
                    for (int j = 1; j <= number_of_books; j++) {
                        chapters[j - 1] = String.valueOf(j);
                    }
                }

                ArrayAdapter<String> Chapteradapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, chapters);
                Chapteradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                chapter_selection.setAdapter(Chapteradapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        chapter_selection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String chapter = adapterView.getItemAtPosition(i).toString();
                chapter_ref = chapter;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        System.out.println(book_ref);
        System.out.println(chapter_ref);

        System.out.println(reference);

        Button retrieve = (Button) view.findViewById(R.id.retrieve);
        View view2 = view;
        retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference = book_ref + "." + chapter_ref;
                System.out.println("Recieved!!!!!");
                getBible(trans, reference, view2);
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
                LinearLayout layout = view.findViewById(R.id.layoutText);
                layout.removeAllViews();
                // Create a new TextView to display the chapter content

                // Split the chapter content into separate verses using a regular expression
                String[] verses = chapterContent.split("<br>\\s*");

                // Add a click listener to each verse
                View verseView = null;
                for (int i = 0; i < verses.length; i++) {
                    final int verseNumber = i + 1;
                    verseView = getLayoutInflater().inflate(R.layout.verse_layout, null);
                    TextView verseTextView = verseView.findViewById(R.id.verseTextView);
                    verseTextView.setText(Html.fromHtml(verses[i]));
                    verseTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Handle verse click
                            Toast.makeText(view.getContext(), "Verse " + verseNumber + " clicked", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                // Add the TextView to the layout
                layout.addView(verseView);
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
