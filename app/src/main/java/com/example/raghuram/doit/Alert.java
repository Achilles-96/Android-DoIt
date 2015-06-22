package com.example.raghuram.doit;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class Alert extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        databasehandler db = new databasehandler(this);
        Log.d("inside:", "alert");
        ArrayList<name> list = db.listall();
        int len = list.size();
        TextView textView = (TextView) findViewById(R.id.alertme);
        Log.d("inside:", textView.getText().toString());
        if (len == 0) {

            textView.setTextSize(30);
            textView.setText("NO JOBS");
            return;
        }
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        r.stop();

        textView.setTextSize(30);
        textView.setText("");
        for (int i = 0; i < len; i++) {
            String old = textView.getText().toString();
            name Name = list.get(i);
            Date date = new Date();
            String mystring = Name.getEnd_date();
            Date date1 = new Date();
            try {
                date1 = new SimpleDateFormat("yyyy - MM - dd \n HH - mm - ss", Locale.ENGLISH).parse(mystring);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Log.d("inside:", "lol");
            if (date1.getTime() - date.getTime() >= 0) {

                String new1 = Integer.toString(Name.getId());
                new1 = new1 + " " + Name.getname() + " " + Name.getEnd_date() + "\n";
                old = old + new1;
                textView.setText(old);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alert, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
