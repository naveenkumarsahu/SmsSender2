package com.example.mpe.smssender;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText monumber,msg;
    Button pick,send;
    String monumber1;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        monumber= (EditText) findViewById(R.id.monumber);
        msg = (EditText) findViewById(R.id.editText);
        pick= (Button) findViewById(R.id.monumberbtn);
        send= (Button) findViewById(R.id.send);
        pick.setOnClickListener(this);
        send.setOnClickListener(this);
    }
    public void onClick(View view) {
     if (view.getId()==R.id.monumberbtn) {
         Intent i=new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
         startActivityForResult(i,1);
     }
else if (view.getId()==R.id.send){
         smsapi();
         monumber.setText(" ");
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
                            monumber.setText(cNumber);
                        }
                        //   String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));



                    }

                }

                break;
        }

    }
    public void smsapi(){
        String authkey = "124423Af7WzIdBB7m57cbda85";
        String mobiles = monumber.getText().toString();
        String senderId = "12345678";
        String message = msg.getText().toString();
        String route="default";
        URLConnection myURLConnection=null;
        URL myURL=null;
        BufferedReader reader=null;
        String encoded_message= URLEncoder.encode(message);
        String mainUrl="https://control.msg91.com/api/sendhttp.php?";
        StringBuilder sbPostData= new StringBuilder(mainUrl);
        sbPostData.append("authkey="+authkey);
        sbPostData.append("&mobiles="+mobiles);
        sbPostData.append("&message="+encoded_message);
        sbPostData.append("&route="+route);
        sbPostData.append("&sender="+senderId);
        mainUrl = sbPostData.toString();
        try
        {
            myURL = new URL(mainUrl);
            myURLConnection = myURL.openConnection();
            myURLConnection.connect();
            reader= new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));

            String response;
            while ((response = reader.readLine()) != null)
                //print response
                Log.d("RESPONSE", ""+response);
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
