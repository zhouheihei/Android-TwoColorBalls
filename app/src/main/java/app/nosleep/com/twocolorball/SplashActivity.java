package app.nosleep.com.twocolorball;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.umeng.analytics.MobclickAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

public class SplashActivity extends Activity {

    private BaseSQLiteOpenHelper helper;
    private BaseUtils BU = null;
    private String first;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        helper = new BaseSQLiteOpenHelper(getApplicationContext());
        BU=new BaseUtils(getApplicationContext());//工具
        first = BU.getShareP("first", getApplicationContext());
        if (null == first || first.equals("")) {
            Log.v("if","是第一次运行！");
            String url = getResources().getString(R.string.sycn_data_url);
            OkHttpUtils.get().url(url).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {

                }

                @Override
                public void onResponse(String response, int id) {
                    Log.v("if","准备初始化数据");
                    try {
                        HTMLUtils hu = new HTMLUtils();
                        List<String[]> list = hu.getAll(response);
                        for (int i = list.size()-1; i >=0 ; i--) {
//                            Log.v("LIST", "期号" + list.get(i)[0]);
//                            Log.v("LIST", "号码" + list.get(i)[1]);
//                            Log.v("LIST", "星期" + list.get(i)[2]);
//                            Log.v("LIST", "日期" + list.get(i)[3]);
                            helper.insertToNumbers(list.get(i)[0], list.get(i)[3], "星期"+list.get(i)[2], "0", list.get(i)[1]);
                        }
                        BU.ChengeData(new ChengDataCallBack() {
                            @Override
                            void wingetweb() {
                            }

                            @Override
                            void errgetweb() {

                            }
                        });
                        Log.v("number",list.get(list.size()-1)[1]+"");
                        BU.setShareP("first", "1", getApplicationContext());
                        BU.setShareP("firstnumber", list.get(list.size()-1)[0], getApplicationContext());
                        intent=new Intent(SplashActivity.this,WelcomActivity.class);
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                SplashActivity.this.finish();
                            }
                        }, 500);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }else
        {

//
            Log.v("if","不是第一次运行！");
            intent=new Intent(SplashActivity.this,MainActivity.class);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    SplashActivity.this.finish();
                }
            }, 1000);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        MobclickAgent.onResume(this);
    }
}
