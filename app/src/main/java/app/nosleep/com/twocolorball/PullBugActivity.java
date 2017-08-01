package app.nosleep.com.twocolorball;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class PullBugActivity extends BaseActivity {

    private EditText mEdEmail=null;
    private EditText mEdContent=null;
    private TextView mBtnOk=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pullbug;
    }

    @Override
    protected void init() {
        this.mEdEmail= (EditText) findViewById(R.id.pullbug_ed_email);
        this.mEdContent= (EditText) findViewById(R.id.pullbug_ed_remark);
        this.mBtnOk= (TextView) findViewById(R.id.pullbug_btn_ok);
    }

    @Override
    protected void setView() {

    }

    @Override
    protected void setListenner() {
        this.mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpUtils.post()
                        .url(PullBugActivity.this.getString(R.string.SERVERURL))
                        .addParams("content",mEdContent.getText().toString())
                        .addParams("username",mEdEmail.getText().toString())
                        .addParams("userid","TwoColorBall")
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mBtnOk.setText("非常感谢您的建议！");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        mBtnOk.setText("非常感谢您的建议！");
                    }
                });

            }
        });
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
