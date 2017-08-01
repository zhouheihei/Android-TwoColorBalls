package app.nosleep.com.twocolorball;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by nosleep on 16-12-2.
 * 日期：16-12-2下午2:51
 * author:zzh
 * override:
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
//        PushAgent.getInstance(this).onAppStart();
        this.init();
        this.setView();
        this.setListenner();

    }


    /**
     * get layout id
     * @return int id
     */
    protected abstract int getLayoutId();
    /**
     * 获取控件以及变量初始化
     */
    protected abstract void init();

    /**
     * 设置控件初始化
     */
    protected abstract void setView();

    /**
     * 注册控件监听
     */
    protected abstract void setListenner();
}
