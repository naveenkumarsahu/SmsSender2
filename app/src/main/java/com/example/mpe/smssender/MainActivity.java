package com.example.mpe.smssender;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText monumber,msg;
    Button pick,send;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
         startActivityForResult(i,0);
     }
else {

     }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
