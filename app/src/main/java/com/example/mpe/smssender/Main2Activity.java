package com.example.mpe.smssender;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG = "MainActivity 2  ";
RelativeLayout rll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        rll=(RelativeLayout) findViewById(R.id.rll);
        MyTask mt = new MyTask();
        mt.execute("http://192.168.0.108:8000/Requestes/");
    }

class MyTask extends AsyncTask<String, String, JSONObject> {

    String result = " ";
    ArrayList<String> groups=new ArrayList();

    protected JSONObject doInBackground(String... strings) {
        try {
            URL u1 = new URL(strings[0]);
            InputStream is = u1.openStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String s1 = br.readLine();
            while (s1 != null) {
                result += s1;
                s1 = br.readLine();
            }

        } catch (Exception e) {
            Log.e("erooorrrrrrr...", "ondoInBackground", e);

        }
        Log.i(TAG,"response1"+result);
        try {
            return new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject json) {
        super.onPostExecute(json);
        try {
            Log.i(TAG, json.toString());
//            JSONObject jsonObject = new JSONObject("");
            JSONArray jsonArray = json.getJSONArray("Groups");
            Log.i(TAG, "Json array" + jsonArray.toString());
            Log.i(TAG, " length==" + jsonArray.length());


            int i = 0;

            for (i=0;i<jsonArray.length();i++){
                groups.add(i,jsonArray.getString(i));
                Log.d(TAG,jsonArray.getString(i));
            }
           // Toast.makeText(Main2Activity.this,String.valueOf(groups),Toast.LENGTH_LONG);
            setData(groups);

        } catch (Exception e) {
            Log.e("eroor22", "onPostExcecute", e);
        }
    }
}


    void setData(ArrayList<String> groups){

        Iterator itr=groups.iterator();//getting Iterator from arraylist to traverse elements
        TextView textView=new TextView(Main2Activity.this);
        textView.setText(String.valueOf(groups));

rll.addView(textView);


    }}
