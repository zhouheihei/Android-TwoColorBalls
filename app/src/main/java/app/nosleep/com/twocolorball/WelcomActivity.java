package app.nosleep.com.twocolorball;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class WelcomActivity extends Activity {

    private Context mContext = null;
    private BaseUtils mBU = null;
    private WelcomApapter apapter;
    private List<View> guides = new ArrayList<>();
    private ViewPager pager;
    private ImageView curDot;
    // 位移量
    private int offset;
    // 记录当前的位置
    private int curPos = 0;
    // 首次使用程序的显示的欢迎图片
//    private int[] ids = { R.mipmap.welcomeimg,
//            R.mipmap.welcomeimg, R.mipmap.welcomeimg,
//            };
    // 首次使用程序的显示的欢迎布局
    private int[] layouts = { R.layout.apater_welcom_one,
            R.layout.apater_welcom_two, R.layout.apater_welcom_three,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
        init();
        setview();
        setlistenner();
    }

    private void init() {
        this.mContext=getApplicationContext();
        this.mBU=new BaseUtils(mContext);
        mBU.setShareP("firsrun","false",this);
        for (int i = 0; i < layouts.length; i++) {
            LayoutInflater flater = LayoutInflater.from(this);
            View view = flater.inflate(layouts[i],null);
            guides.add(view);
        }
        curDot = (ImageView) findViewById(R.id.cur_dot);
        curDot.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    public boolean onPreDraw() {
                        offset = curDot.getWidth();
                        return true;
                    }
                });
        WecommPagerAdapter adapter = new WecommPagerAdapter(guides);
        pager = (ViewPager) findViewById(R.id.welcome_vp);
        pager.setAdapter(adapter);

    }

    private void setview() {
    }

    private void setlistenner() {
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int arg0) {
                moveCursorTo(arg0);
                if (arg0 == layouts.length - 1) {// 到最后一张了
                    skipActivity(2);
                }
                curPos = arg0;
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }

        });
    }
    /**
     * 移动指针到相邻的位置
     *
     * @param position
     *            指针的索引值
     * */
    private void moveCursorTo(int position) {
        TranslateAnimation anim = new TranslateAnimation(offset * curPos,
                offset * position, 0, 0);
        anim.setDuration(300);
        anim.setFillAfter(true);
        curDot.startAnimation(anim);
    }

    /**
     * 延迟多少秒进入主界面
     * @param min 秒
     */
    private void skipActivity(int min) {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(WelcomActivity.this,
                        MainActivity.class);
                startActivity(intent);
                WelcomActivity.this.finish();
            }
        }, 1000*min);
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
