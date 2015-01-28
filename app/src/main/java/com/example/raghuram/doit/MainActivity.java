package com.example.raghuram.doit;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends ActionBarActivity {

    private int idcount=1;
    databasehandler db=new databasehandler(this);

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=new Intent(this,alarmreceiver.class);
        pendingIntent=PendingIntent.getBroadcast(this,0,intent,0);
    }

    public void alert(View view)
    {
        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        //int interval=1000*60*60*24;
        int interval=1000*60*60*24;
        Date date=new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,8);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        Date date1=calendar.getTime();
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),interval,pendingIntent);
        Log.d("yes:","alarm set");
    }

    public void addtoDB(View view)
    {
        name Name=new name();
        EditText edit=(EditText)findViewById(R.id.edit);
        EditText edit2=(EditText)findViewById(R.id.edit_date);
        String a=edit.getText().toString();
        String b=edit2.getText().toString();
        int days=Integer.parseInt(b);
        Date date=new Date();

        String timeStamp = new SimpleDateFormat("yyyy - MM - dd \n HH - mm - ss").format(date.getTime());
        //Log.d("old:",timeStamp);
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR,days);
        Date newdate=cal.getTime();
        String timeStamp2=new SimpleDateFormat("yyyy - MM - dd \n HH - mm - ss").format(newdate.getTime());
        //Log.d("new:",timeStamp2);
        Name.setname(a, timeStamp, timeStamp2);
        db.addGoal(Name);

        idcount++;
    }

    public void getfromDB(View view)
    {
        EditText edit=(EditText)findViewById(R.id.edit2);
        String a=edit.getText().toString();
        int id=Integer.parseInt(a);
        name Name=db.getGoal(id);
        TextView textView=(TextView)findViewById(R.id.showname);
        TextView textView2=(TextView)findViewById(R.id.showdate);
        textView.setTextSize(30);
        textView.setText(Name.getname());
        textView2.setTextSize(30);
        textView2.setText(Name.getdate() + "\n\n" + Name.getEnd_date());
    }
    public void getall(View view)
    {
        ArrayList<name> list=db.listall();
        int len=list.size();
        TextView textView2=(TextView)findViewById(R.id.showname);
        textView2.setText("");
        if(len==0)
        {
            TextView textView=(TextView)findViewById(R.id.showdate);
            textView.setTextSize(30);
            textView.setText("NO JOBS");
            return;
        }
        TextView textView=(TextView)findViewById(R.id.showdate);
        textView.setTextSize(30);
        textView.setText("");
        for(int i=0;i<len;i++)
        {
            String old=textView.getText().toString();
            String new1=Integer.toString(list.get(i).getId());
            new1 = new1 + " " + list.get(i).getname() + " : " + list.get(i).getEnd_date() + "\n\n";
            old = old + new1;
            textView.setText(old);
        }
    }

    public void clear(View view)
    {
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