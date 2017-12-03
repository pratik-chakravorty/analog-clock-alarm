package com.pratik.analogclock.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.widget.Toast;

/**
 * Created by Pratik on 11/28/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent arg1) {
        Toast.makeText(context, "Alarm received!", Toast.LENGTH_LONG).show();
        Intent startIntent = new Intent(context,RingtonePlayingService.class);
        context.startService(startIntent);


    }


}
