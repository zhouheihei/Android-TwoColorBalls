package app.nosleep.com.twocolorball;

import android.app.Activity;
import android.app.Application;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nosleep on 16-12-6.
 * 日期：16-12-6上午11:02
 * author:zzh
 * override:
 */

public class BaseApp extends Application {

    public List<NumberModle> LIST_NUMBERS;
    public NumberModle mFirstNumber = null;
    private BaseUtils BU = null;
    private List<Activity> LIST_ACTIVITY=null;
    public String NEXTOPENDAY;//下一开奖期号
//    private String NEXTOPENDATE;//下载开奖日期
//    private String NEXTOPENWEEK;//下棋开奖周几
//    private String NEXTID;//下一期ID
//    private String first;


    @Override
    public void onCreate() {
        super.onCreate();
        LIST_NUMBERS = new ArrayList<>();
        LIST_ACTIVITY=new ArrayList<>();
        //PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
//        mPushAgent.register(new IUmengRegisterCallback() {
//
//            @Override
//            public void onSuccess(String deviceToken) {
//                //注册成功会返回device token
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//
//            }
//        });
    }

    public void del(){
        LIST_ACTIVITY.clear();
    }

}
