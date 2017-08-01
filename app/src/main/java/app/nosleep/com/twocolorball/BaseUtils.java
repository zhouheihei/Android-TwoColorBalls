package app.nosleep.com.twocolorball;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import okhttp3.Call;

/**
 * Created by nosleep on 16-12-9.
 * 日期：16-12-9上午9:13
 * author:zzh
 * override: 基础工具类
 */

public class BaseUtils {
    Context mContext;
    BaseSQLiteOpenHelper mHelp;
    BaseApp mApp;

    public BaseUtils(Context pContext) {
        this.mContext = pContext;
        this.mHelp = new BaseSQLiteOpenHelper(mContext);
        mApp=new BaseApp();
    }

    public String getShareP(String key, Context pcontext) {
        SharedPreferences sp = pcontext.getSharedPreferences("info", 0);
        String token = sp.getString(key, "");
        return token;
    }

    public void setShareP(String key, String value, Context pcontext) {
        SharedPreferences sp = pcontext.getSharedPreferences("info", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 获取下期开奖结果
     *
     * @param datestr 2016-11-01
     * @return String[] 0 date 1week
     */
    public String[] getDate(String datestr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekarr = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        String[] datearr = new String[2];
        try {
            Date date = sdf.parse(datestr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            Log.v("week", week + "!!!!!");
            if (week == 4) {
                calendar.add(Calendar.DATE, +3);
            } else {
                calendar.add(Calendar.DATE, +2);
            }
            datearr[0] = sdf.format(calendar.getTime());
            datearr[1] = weekarr[calendar.get(Calendar.DAY_OF_WEEK) - 1];
            Log.v("week", sdf.format(calendar.getTime()));
            Log.v("week", calendar.get(Calendar.DAY_OF_WEEK) - 1 + "");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return datearr;
    }

    /**
     * 数据同步
     * 1.拉去网络数据
     * 2.获取本地最新数据
     * 3.匹配数据是否同步
     * A.1.同步，进行奖金验证 2.更新数据库信息 3.添加下期数据
     * B.1.不同步。
     * 4.发送通知信息
     */
    public void ChengeData(final ChengDataCallBack cdc) {
        OkHttpUtils.get().url(mContext.getString(R.string.sycn_data_url)).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                cdc.errgetweb();
            }

            @Override
            public void onResponse(String response, int id) {
                HTMLUtils hu;
                try {
                    hu = new HTMLUtils();
                    String[] data = hu.getFirst(response);
                    NumberModle _number = mHelp.getNumberByFirst();
                    Log.v("start", "兑奖ID" + _number.getId());
                    if (null != _number) {
                        if (data[0].equals(_number.getOpenDay())) {
                            //兑奖
                            int _money = chengeByBalls(data[1], _number.getId() + "");
                            int openday = Integer.parseInt(data[0]) + 1;
                            String[] opendate = getDate(data[3]);
                            mHelp.updateByNumber(_number.getId() + "", new String[]{"n_opennumber", "n_bingomoney"}, new String[]{data[1], _money + ""});
                            mHelp.insertToNumbers(openday + "", opendate[0], opendate[1], "0", "01,01,01,01,01,01,01");
                            if (_money == 0) {
                                sendNotiFicaTion("双色球开奖了！", data[0] + "期开奖:" + data[1], data[1], mContext, MainActivity.class);
                            } else if (_money == 100000) {
                                sendNotiFicaTion("双色球开奖了！", "恭喜您！您中得二等奖！", data[1], mContext, MainActivity.class);
                            } else if (_money == 1000000) {
                                sendNotiFicaTion("双色球开奖了！", "恭喜您！您中得一等奖！", data[1], mContext, MainActivity.class);
                            } else {
                                sendNotiFicaTion("双色球开奖了！", "您的中奖金额为：" + _money, data[1], mContext, MainActivity.class);
                            }
                            Log.v("Toast", "兑奖完毕，下期见！");
                        } else {
                            Log.v("Toast", "还未开奖，请等待！");
                        }
                        cdc.wingetweb();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public void getByData(final GetDataCallBack cdc,String firstnumber,String newnumber) {
        String url = "http://baidu.lecai.com/lottery/draw/list/50?type=range&start="+firstnumber+"&end="+newnumber;
        Log.v("url",url);
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                cdc.errgetweb();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    HTMLUtils hu = new HTMLUtils();
                    List<String[]> list = hu.getAll(response);
                    List<NumberModle> _number =new ArrayList<NumberModle>();
                    for (int i = 0; i < list.size(); i++) {
                        NumberModle _n = new NumberModle();
                        Log.v("LIST", "期号" + list.get(i)[0]);
                        Log.v("LIST", "号码" + list.get(i)[1]);
                        Log.v("LIST", "星期" + list.get(i)[2]);
                        Log.v("LIST", "日期" + list.get(i)[3]);
                        _n.setId(i);
                        _n.setOpenNumbers(list.get(i)[1]);
                        _n.setOpenDay(list.get(i)[0]);
                        _n.setOpenDate(list.get(i)[3]);
                        _n.setOpenWeek(list.get(i)[2]);
                        _number.add(_n);
                    }
                    cdc.wingetweb(_number);
                } catch (Exception ex) {

                }

            }
        });
    }

    //兑奖
    public int chengeByBalls(String openNumber, String nid) {
        Log.v("Open", "开始验证号码！！");
        List<NoteModle> list = mHelp.getNoteAll(nid);
        String redballs = openNumber.substring(0, openNumber.lastIndexOf(","));
        String blueball = openNumber.substring(openNumber.lastIndexOf(",") + 1);
        Log.v("Open", "开奖结果：红色球：" + redballs + " 蓝色球：" + blueball);
        int money = 0;
        for (int i = 0; i < list.size(); i++) {
            int num = 0;
            int bluenum = 0;
            NoteModle note = list.get(i);
            if (redballs.indexOf(note.getNumone()) >= 0) {
                num = num + 1;
            }
            if (redballs.indexOf(note.getNumtwo()) > 0) {
                num = num + 1;
            }
            if (redballs.indexOf(note.getNumthree()) > 0) {
                num = num + 1;
            }
            if (redballs.indexOf(note.getNumfour()) > 0) {
                num = num + 1;
            }
            if (redballs.indexOf(note.getNumfive()) > 0) {
                num = num + 1;
            }
            if (redballs.indexOf(note.getNumsix()) > 0) {
                num = num + 1;
            }
            if (blueball.equals(note.getNumseven())) {
                bluenum = bluenum + 1;
            }
            if (num == 0 && bluenum == 1) {
                money = money + 5 * note.getMultiple();
            }
            if (num == 1 && bluenum == 1) {
                money = money + 5 * note.getMultiple();
            }
            if (num == 2 && bluenum == 1) {
                money = money + 5 * note.getMultiple();
            }
            if (num == 3 && bluenum == 1) {
                money = money + 10 * note.getMultiple();
            }
            if (num == 4 && bluenum == 0) {
                money = money + 10 * note.getMultiple();
            }
            if (num == 4 && bluenum == 1) {
                money = money + 200 * note.getMultiple();
            }
            if (num == 5 && bluenum == 0) {
                money = money + 200 * note.getMultiple();
            }
            if (num == 5 && bluenum == 1) {
                money = money + 3000 * note.getMultiple();
            }
            if (num == 6 && bluenum == 0) {
                money = 100000;
                break;
            }
            if (num == 6 && bluenum == 1) {
                money = 1000000;
                break;
            }
        }
        Log.v("Open", "本期中奖金额：" + money);
        return money;
    }

    //发送通知
    public void sendNotiFicaTion(String title, String content, String opennumber, Context pContext, Class<?> cls) {
        Log.v("Notification", "发送通知");
        NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext);
        mBuilder.setSmallIcon(R.mipmap.logo)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.logo))
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(getDefalutIntent(PendingIntent.FLAG_UPDATE_CURRENT, cls))
                .setDefaults(Notification.DEFAULT_ALL)
                .setTicker("开奖结果:" + opennumber)
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setOngoing(false);
        Notification notify = mBuilder.build();
        notify.iconLevel = 1000;
        //        notify.flags|=Notification.FLAG_AUTO_CANCEL;
        manager.notify(1, notify);
        Log.v("Notification", "发送通知完毕！");
    }

    public PendingIntent getDefalutIntent(int flags, Class<?> cls) {
        Intent intent = new Intent(mContext, cls.getClass());
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(
                1,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        return pendingIntent;
    }

    public void getNewNumberHtml(final GetNewDataCallBack cdc) {
        String ret = "";
        OkHttpUtils.get().url(mContext.getString(R.string.sycn_baidu_data_url)).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                cdc.errgetweb();
            }

            @Override
            public void onResponse(String response, int id) {
                String[] retArr = null;
                HTMLUtils hu;
                try {
                    hu = new HTMLUtils();
                    retArr=hu.getNewNumnber(response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                cdc.wingetweb(retArr);
            }
        });
    }
    public void TaskCheng(){
        BaseUtils BU =new BaseUtils(mContext);
        String nextday=BU.getShareP("nextday",mContext);
        this.getByData(new GetDataCallBack() {
            @Override
            void wingetweb(List<NumberModle> list) {
                if(null!=list&&list.size()>0){
                    NumberModle _m =list.get(0);
                    int _money = chengeByBalls(_m.getOpenNumbers(), _m.getOpenDay() + "");
                    if (_money == 0) {
                        sendNotiFicaTion("双色球开奖了！", _m.getOpenDay() + "期开奖:" + _m.getOpenNumbers(), _m.getOpenNumbers(), mContext, MainActivity.class);
                    } else if (_money == 100000) {
                        sendNotiFicaTion("双色球开奖了！", "恭喜您！您中得二等奖！", _m.getOpenNumbers(), mContext, MainActivity.class);
                    } else if (_money == 1000000) {
                        sendNotiFicaTion("双色球开奖了！", "恭喜您！您中得一等奖！", _m.getOpenNumbers(), mContext, MainActivity.class);
                    } else {
                        sendNotiFicaTion("双色球开奖了！", "您的中奖金额为：" + _money, _m.getOpenNumbers(), mContext, MainActivity.class);
                    }
                    Log.v("Toast", "兑奖完毕，下期见！");
                }else
                {
                    Log.v("Toast", "未兑奖，下期见！");
                }
            }

            @Override
            void errgetweb() {

            }
        },nextday,nextday);
    }
}
