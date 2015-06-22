package com.example.raghuram.doit;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends ActionBarActivity {

    private int idcount = 1;
    databasehandler db = new databasehandler(this);

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, alarmreceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        setupUI(findViewById(R.id.parent));
    }

    public void alert(View view) {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int interval = 1000 * 60 * 60 * 24;
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date1 = calendar.getTime();
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
        Log.d("yes:", "alarm set");
    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard();
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }

    public void addtoDB(View view) {
        name Name = new name();
        EditText edit = (EditText) findViewById(R.id.edit);
        EditText edit2 = (EditText) findViewById(R.id.edit_date);
        String a = edit.getText().toString();
        String b = edit2.getText().toString();
        if (a.isEmpty()) {
            Toast.makeText(this, "Job field cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }
        int days;
        try {
            days = Integer.parseInt(b);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Only numbers in days field", Toast.LENGTH_LONG).show();
            return;
        }

        Date date = new Date();

        String timeStamp = new SimpleDateFormat("yyyy - MM - dd \n HH - mm - ss").format(date.getTime());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, days);
        Date newdate = cal.getTime();
        String timeStamp2 = new SimpleDateFormat("yyyy - MM - dd \n HH - mm - ss").format(newdate.getTime());
        Name.setname(a, timeStamp, timeStamp2);
        db.addGoal(Name);

        idcount++;
    }

    public void getfromDB(int id) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lister);
        linearLayout.removeAllViews();
        ((TextView) findViewById(R.id.showdate)).setText("");
        name Name = db.getGoal(id);
        TextView textView2 = (TextView) findViewById(R.id.showdate);
        textView2.setTextSize(30);
        textView2.setText(Name.getdate() + "\n\n" + Name.getEnd_date());
        setupUI(findViewById(R.id.parent));
    }

    public void getall(View view) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lister);
        linearLayout.removeAllViews();
        ((TextView) findViewById(R.id.showdate)).setText("");
        ArrayList<name> list = db.listall();
        int len = list.size();
        if (len == 0) {
            TextView textView = (TextView) findViewById(R.id.showdate);
            textView.setTextSize(30);
            textView.setText("NO JOBS");
            return;
        }

        for (int i = 0; i < len; i++) {
            final TextView textView = new TextView(this);
            String cont = list.get(i).getname() + "\t:\t" + list.get(i).getEnd_date() + "\n";
            textView.setTextSize(30);
            textView.setText(cont);
            textView.setClickable(true);
            textView.setId(list.get(i).id);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getfromDB(v.getId());
                }
            });

            linearLayout.addView(textView);
        }
        setupUI(findViewById(R.id.parent));
    }

    public void clear(View view) {
        db.clearit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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