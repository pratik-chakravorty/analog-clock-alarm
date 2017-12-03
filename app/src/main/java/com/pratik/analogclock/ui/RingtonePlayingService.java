package com.pratik.analogclock.ui;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;

/**
 * Created by Pratik on 12/2/2017.
 */

public class RingtonePlayingService extends Service {
    private Ringtone ringtone;
    private Vibrator vibrator;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(this,ringtoneUri);
        ringtone.play();
        vibrator = (Vibrator)getBaseContext()
                .getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(5000);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        ringtone.stop();
        vibrator.cancel();
    }
}
