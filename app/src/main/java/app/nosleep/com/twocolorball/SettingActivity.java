package app.nosleep.com.twocolorball;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.umeng.analytics.MobclickAgent;


/**
 * Created by nosleep on 16-12-16.
 * 日期：16-12-16上午10:40
 * author:zzh
 * override:
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    TextView mBtnPullBug=null;
    TextView mBtnAbout=null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void init() {
        this.mBtnAbout= (TextView) findViewById(R.id.setting_about);
        this.mBtnPullBug= (TextView) findViewById(R.id.setting_pullbug);
    }

    @Override
    protected void setView() {

    }

    @Override
    protected void setListenner() {
        this.mBtnAbout.setOnClickListener(this);
        this.mBtnPullBug.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        if(view.getId()==R.id.setting_about){
            intent.setClass(SettingActivity.this,AboutActivity.class);
            SettingActivity.this.startActivity(intent);
            SettingActivity.this.finish();
        }else if(view.getId()==R.id.setting_pullbug){
            intent.setClass(SettingActivity.this,PullBugActivity.class);
            SettingActivity.this.startActivity(intent);
            SettingActivity.this.finish();
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
