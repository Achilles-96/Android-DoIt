package com.example.raghuram.doit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by raghuram on 24/1/15.
 */
public class alarmreceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, Alert.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
        Toast.makeText(context, "i am in", Toast.LENGTH_SHORT).show();
    }
}
