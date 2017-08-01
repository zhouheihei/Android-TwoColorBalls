package app.nosleep.com.twocolorball;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by nosleep on 16-12-10.
 * 日期：16-12-10下午1:28
 * author:zzh
 * override:
 */

public class AlarmReceiver extends BroadcastReceiver {
    BaseSQLiteOpenHelper mHelp=null;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("short")){
            Toast.makeText(context, "short alarm", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context, "repeating alarm",Toast.LENGTH_LONG).show();
        }
    }
}
