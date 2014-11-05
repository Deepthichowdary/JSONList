package com.example.deepthi.jsonlistview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class ListViewActivity extends Activity {
    // private SimpleAdapter adapter;
    MySimpleAdapter adapter;
    private JSONArray mComments = null;
    JSONArray Jlist;
    ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    HashMap<String, Object> map = new HashMap<String, Object>();
    Bitmap bmp;
    int buttonClicked;
    ListView lv;
    String[] from = {"image", "id", "name"};
    int[] to = {R.id.image, R.id.id, R.id.name};
    private static LayoutInflater inflater = null;
    // public ImageLoader imageLoader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        lv = (ListView) findViewById(R.id.list1);
        buttonClicked=getIntent().getIntExtra("button", -1);
        try {
            new GetConnections().execute();
        } catch (Exception e) {
            Log.i("OnCreate", "Exception during execute()...");
            e.printStackTrace();
        }

    }


    class GetConnections extends AsyncTask<Void, Void, Void> {

        private Exception exception;
        HttpURLConnection connection = null;
        InputStream is = null;
        String line;
        String response;
        //JSONParser parser = new JSONParser();

        @Override
        protected void onPreExecute() {

            Log.i("GetConnections", "Loading image...");
        }

        @Override
        protected Void doInBackground(Void... param) {

            try {
                URL url = setURL();
                // Creating new JSON Parser
                JSONParser jParser = new JSONParser();

               connection = (HttpURLConnection) url.openConnection();
               connection.setRequestMethod("GET");
                connection.connect();
                is = connection.getInputStream();
                BufferedReader theReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuilder sb = new StringBuilder();
                while ((line = theReader.readLine()) != null){
                   sb.append(line + "\n");
                }
                response = sb.toString();
                JSONObject jsonObj = new JSONObject(response);

                Jlist = jsonObj.getJSONArray("list");
                for (int i = 0; i < Jlist.length(); i++) {
                    JSONObject mJsonObj = Jlist.getJSONObject(i);
                    updateMap(mJsonObj);
                }
            } catch (JSONException e){
                e.printStackTrace();
            } catch (Exception e) {
                Log.i("GetConnection", "Error in doInBackgroung" + e);

            }

            return null;
        }


        @Override
        protected void onPostExecute(Void param) {
            adapter = new MySimpleAdapter(getApplicationContext(), list,
                    R.layout.list_row, new String[]{}, new int[]{});
            lv.setAdapter(adapter);
        }

    }

    private URL setURL() {
        try {
            if (buttonClicked == 1) {
                return new URL("http://54.215.205.214/dept1");

            } else
                return new URL("http://54.215.205.214/dept2");
        }catch(java.net.MalformedURLException e){
            Log.i("setURL","Errpr While Setting the URL"+e);
        }
        return null;


    }

    private void updateMap(JSONObject mJsonObj) {

        try {

            String id = mJsonObj.getString("age");
            String name = mJsonObj.getString("name");
            String Url = mJsonObj.getString("url");
            map.put("id", id);
            map.put("name", name);
            map.put("url", Url);
            list.add(map);

        }  catch(Exception e){
            Log.i("GetConnection","Error in doInBackgroung"+e);
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
