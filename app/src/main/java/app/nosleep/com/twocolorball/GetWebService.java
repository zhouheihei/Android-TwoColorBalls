package app.nosleep.com.twocolorball;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by nosleep on 16-12-10.
 * 日期：16-12-10下午1:44
 * author:zzh
 * override:
 */

public class GetWebService extends Service {

    BaseSQLiteOpenHelper mHelper =null;
    BaseUtils BU=null;
    String datetime ;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Log.i("====","执行了");
                    BU.TaskCheng();
                    break;
            }
        }
    };
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("====","第一次");
    }
    /**
     * string类型时间转换为date
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(null!=intent){
            Log.v("Service","onStartCommand()");
            datetime=intent.getStringExtra("date");
            Log.v("Service","服务已开启，下次执行："+datetime);
            mHelper=new BaseSQLiteOpenHelper(this);
            BU=new BaseUtils(this);
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(1);
                }
            };
            Timer timer = new Timer(true);
            timer.schedule(task,strToDateLong(datetime));
        }else{
            Log.v("Service","Intent 为空");
        }
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        Log.v("Service","onDestroy()");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v("Service","onBind()");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v("Service","onUnbind()");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.v("Service","onRebind()");
        super.onRebind(intent);
    }
}
