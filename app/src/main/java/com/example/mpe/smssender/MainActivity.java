package com.example.mpe.smssender;
import android.app.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    EditText msg;
    Button send;
    ArrayList<String>Groupsitems;
    ArrayList<String> groups;
    LinearLayout ll;
    CheckBox cb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Groupsitems=new ArrayList<String>();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        groups = new ArrayList<String>();
        MyTask mt = new MyTask();
        mt.execute("http://192.168.0.108:8000/userdata/");
        ll = (LinearLayout) findViewById(R.id.rll);
        ScrollView sv=new ScrollView(MainActivity.this);
        msg=new EditText(MainActivity.this);
        msg.setHint("Type your Sms");
        send=new Button(MainActivity.this);
        send.setText("Send Sms");
        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    smsapi();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                msg.setText(" ");
                msg.setHint("Enter your msg here..");
                //Toast.makeText(MainActivity.this,"Sucessfully sent", Toast.LENGTH_LONG).show();

            }

        });
    }
    public void smsapi() throws UnsupportedEncodingException {
        //JSONArray json_array=new JSONArray(Groupsitems);

        String data = URLEncoder.encode("Groupid", "UTF-8")
                + "=" + URLEncoder.encode(String.valueOf(Groupsitems), "UTF-8");
        data += "&" + URLEncoder.encode("textmsg", "UTF-8") + "="
                + URLEncoder.encode(msg.getText().toString(), "UTF-8");
        String text = "";
        BufferedReader reader = null;
        try {
            URL url = new URL("http://192.168.0.108:8000/Requestes/");


            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }


            text = sb.toString();
            Toast.makeText(MainActivity.this, text.toString(), Toast.LENGTH_SHORT).show();
            Log.d(TAG,"POST"+text);

        } catch (Exception ex) {

        } finally {
            try {

                reader.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


    }




    class MyTask extends AsyncTask<String, String, String> {

        String res = " ";
        ArrayList<String> groups=new ArrayList();

        protected String doInBackground(String... strings) {
            try {
                URL u1 = new URL(strings[0]);
                InputStream is = u1.openStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String s1 = br.readLine();
                while (s1 != null) {
                    res += s1;
                    s1 = br.readLine();
                }

            } catch (Exception e) {
                Log.e("erooorrrrrrr...", "nnnnnnnaveeeeen", e);

            }
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                Log.i(TAG, s);
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("Groups");
                Log.i(TAG, "Json array" + jsonArray.toString());
                Log.i(TAG, " length==" + jsonArray.length());


                int i = 0;

                for (i=0;i<jsonArray.length();i++){
                    groups.add(i,jsonArray.getString(i));
                    Log.d(TAG,jsonArray.getString(i));
                }

                setData(groups);

            } catch (Exception e) {
                Log.e("eroor22", "aaya", e);
            }
        }
    }


    void setData(ArrayList<String> groups){

        Iterator itr=groups.iterator();//getting Iterator from arraylist to traverse elements

        while(itr.hasNext()){

            cb=new CheckBox(MainActivity.this);
            int id=100;
            cb.setId(id++);
            cb.setText(itr.next().toString());
            cb.setOnClickListener(getOnClickDoSomething(cb));
            ll.addView(cb);
        }
        Log.i(TAG, String.valueOf(ll.getChildCount()));

        ll.addView(msg);
        ll.addView(send);

    }

    private View.OnClickListener getOnClickDoSomething(final CheckBox checkbox) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("*************id******" , String.valueOf(checkbox.getId()));
                Log.i("and text***" , checkbox.getText().toString());
                Groupsitems.add(checkbox.getText().toString());
            }
        };



    }

}
