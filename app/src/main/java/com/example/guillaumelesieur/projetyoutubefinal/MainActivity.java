package com.example.guillaumelesieur.projetyoutubefinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillaumelesieur on 13/03/2017.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchView youtubeSearch = (SearchView) this.findViewById(R.id.searchYoutube);


        youtubeSearch.setActivated(true);
        youtubeSearch.setQueryHint("Youtube search");
        youtubeSearch.onActionViewExpanded();
        youtubeSearch.clearFocus();
        youtubeSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit (String query) {
                boolean success = searchOnYoutube(query);

                return success;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }






    protected void fillResults(String jsonResponse) throws JSONException {

        JSONObject json = new JSONObject(jsonResponse);
        JSONArray items = json.getJSONArray("items");

        final List<Video> videosListView = new ArrayList<Video>();

        for (int i = 0; i < items.length(); i++) {
            JSONObject videoObject = items.getJSONObject(i);
            JSONObject videoSnippet = videoObject.getJSONObject("snippet");
            JSONObject videoId = videoObject.getJSONObject("id");
            String id = videoId.getString("videoId");
            String title = videoSnippet.getString("title");
            String date = videoSnippet.getString("publishedAt").substring(0, 10);
            String author = videoSnippet.getString("channelTitle");
            String description = videoSnippet.getString("description");

            String thumbnails = videoSnippet.getJSONObject("thumbnails").getJSONObject("high").getString("url");

            videosListView.add(new Video(title, description, thumbnails, id, date, author));


        }

        AdapterVideo adapter = new AdapterVideo(MainActivity.this, videosListView);

        ListView youtubeList = (ListView) this.findViewById(R.id.listResults);
        youtubeList.setAdapter(adapter);

    }

    protected boolean searchOnYoutube(String query) {
        String requestAPI = "https://www.googleapis.com/youtube/v3/search";
        String partAPI = "?part=id,snippet";
        String queryAPI = "&q=" + query.replace(' ', '+');
        String typeAPI = "&type=video";
        String keyAPI = "&key=AIzaSyASh7IJhAIA8ThENsKezGSKBnU2tKpdm-Y";

        String url = requestAPI + partAPI + queryAPI + typeAPI + keyAPI;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            fillResults(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR with request");
            }
        });

        queue.add(stringRequest);

        return false;
    }
}

