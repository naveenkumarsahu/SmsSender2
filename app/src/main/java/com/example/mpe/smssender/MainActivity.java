package com.example.mpe.smssender;
<<<<<<< HEAD
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

=======

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonToken;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener ,AdapterView.OnItemSelectedListener{
    EditText msg;
    Button send;
   Spinner sp;

    String monumber1;
    String[] group = { "group1", "group2", "group3", "group3", "group5","group6"  };
    ArrayList<String> mo;
>>>>>>> dfa36c2048e9b338c26f58a5c7c7b27d8ebf0169



    class MyTask extends AsyncTask<String, String, String> {

<<<<<<< HEAD
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
=======
        String res=" ";
        protected String doInBackground(String... strings) {
            try {
                URL u1 = new URL(strings[0]);
                InputStream is=u1.openStream();
                InputStreamReader isr=new InputStreamReader(is);
                BufferedReader br=new BufferedReader(isr);
                String s1=br.readLine();
                while (s1!=null){
                    res +=s1;
                    s1=br.readLine();
                }

            } catch (Exception e) {
                Log.e("erooorrrrrrr...","nnnnnnnaveeeeen",e);
>>>>>>> dfa36c2048e9b338c26f58a5c7c7b27d8ebf0169

            }
            return res;
        }
<<<<<<< HEAD

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

=======
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONArray ja=new JSONArray(s);


                String one=ja.optString(0);
                String two=ja.optString(1);
                mo.add(0,one);
                mo.add(1,two);

            }
            catch (Exception e){
                Log.e("eroor22","aaya",e);
            }}
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        sp= (Spinner) findViewById(R.id.spinner);
        sp.setOnItemSelectedListener(this);
        MyTask mt=new MyTask();
        mt.execute("http://127.0.0.1:8000/Requestes/?testing=true");



        msg = (EditText) findViewById(R.id.editText);
        send= (Button) findViewById(R.id.send);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,group);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        sp.setAdapter(aa);
        send.setOnClickListener(this);
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(),group[i],Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public void onClick(View view) {

 if (view.getId()==R.id.send){
         smsapi();

         Toast.makeText(MainActivity.this,"Message Sent Succesfully ",Toast.LENGTH_LONG).show();
         msg.setText(" ");
     }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (1):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c =  managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String id =c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                        String hasPhone =c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,
                                    null, null);
                            phones.moveToFirst();
                            String  cNumber = phones.getString(phones.getColumnIndex("data1"));

                        }
                        //   String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));



                    }

                }

                break;
        }

    }
    public void smsapi(){
 int i;
        for( i=0;i<1;i++) {
            String authkey = "124423Af7WzIdBB7m57cbda85";
            String mobiles = mo.get(i);
            String senderId = "12345678";
            String message = msg.getText().toString();
            String route = "default";
            URLConnection myURLConnection = null;
            URL myURL = null;
            BufferedReader reader = null;
            String encoded_message = URLEncoder.encode(message);
            String mainUrl = "https://control.msg91.com/api/sendhttp.php?";
            StringBuilder sbPostData = new StringBuilder(mainUrl);
            sbPostData.append("authkey=" + authkey);
            sbPostData.append("&mobiles=" + mobiles);
            sbPostData.append("&message=" + encoded_message);
            sbPostData.append("&route=" + route);
            sbPostData.append("&sender=" + senderId);
            mainUrl = sbPostData.toString();
            try {
                myURL = new URL(mainUrl);
                myURLConnection = myURL.openConnection();
                myURLConnection.connect();
                reader = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));

                String response;
                while ((response = reader.readLine()) != null)
                    //print response
                    Log.d("RESPONSE", "" + response);
                reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
>>>>>>> dfa36c2048e9b338c26f58a5c7c7b27d8ebf0169
}
