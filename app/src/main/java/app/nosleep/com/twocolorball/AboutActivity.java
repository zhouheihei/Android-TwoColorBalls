package app.nosleep.com.twocolorball;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by nosleep on 16-12-16.
 * 日期：16-12-16上午10:50
 * author:zzh
 * override:
 */

public class AboutActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void setView() {

    }

    @Override
    protected void setListenner() {

    }
    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
//        MobclickAgent.onResume(this);
    }
}
