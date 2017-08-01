package app.nosleep.com.twocolorball;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    //控件
    private Toolbar toolbar =null;
    private FloatingActionButton fab=null;
    private ListView mListView= null;
    private BaseApp mApp=null;
    private Context mContext=null;
    private RelativeLayout mMainTopRL=null;
    private TextView mTxtOpenDay=null;
    private TextView mTxtOpenDate=null;
    private TextView mTxtOpenWeek=null;
    private SwipeRefreshLayout mSRL=null;
    private BaseSQLiteOpenHelper mHelp=null;
    private BaseUtils BU=null;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSRL.setRefreshing(true);
        onRefresh();
        mHelp.updateByNumber("2016154","2017001");
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        mApp= (BaseApp) getApplication();
        mContext=getApplicationContext();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mListView= (ListView) findViewById(R.id.main_list);
        mMainTopRL= (RelativeLayout) findViewById(R.id.maintop_rl);
        mTxtOpenDay= (TextView) findViewById(R.id.list_maintop_number);
        mTxtOpenDate= (TextView) findViewById(R.id.list_maintop_date);
        mTxtOpenWeek= (TextView) findViewById(R.id.list_maintop_week);
        mSRL= (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);
        BU=new BaseUtils(mContext);
        mHelp=new BaseSQLiteOpenHelper(mContext);
    }

    @Override
    public void setView() {
        setSupportActionBar(toolbar);
        mApp.LIST_NUMBERS =new ArrayList<>();
//        Log.v("load","开始加载数据"+mApp.LIST_NUMBERS.size());
        if(null!=mApp.LIST_NUMBERS){
            mApp.mFirstNumber=new NumberModle();
            adapter = new ListAdapter(getApplicationContext(), mApp.LIST_NUMBERS, new ListAdapter.MyOnClickItems() {
                @Override
                public void OnClick(View v) {
                    if(null!=v&&null!=v.getTag()){
                        ListAdapter.ViewHolder datas = (ListAdapter.ViewHolder) v.getTag();
                        String _noteid= datas.txt_money.getTag()+"";
                        Intent intent = new Intent(MainActivity.this,AddActivity.class);
                        Log.v("Item Click","Item Click"+_noteid);
                        intent.putExtra("id", _noteid);
                        intent.putExtra("openday", datas.txt_number.getText()+"");
                        intent.putExtra("type", "notop");
                        MainActivity.this.startActivity(intent);
                    }
                }

                @Override
                public void OnLongClick(View v) {

                }
            });
            mListView.setAdapter(adapter);
        }
    }

    private void getFirstAndUpdata(){
       List<NumberModle> _list= mHelp.getNumberAll();
        if(null!=_list&&_list.size()>1){
            mTxtOpenDay.setText(_list.get(0).getOpenDay());
            mTxtOpenDate.setText(_list.get(0).getOpenDate());
            mTxtOpenWeek.setText(_list.get(0).getOpenWeek());
        }
    }
    @Override
    public void setListenner() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainTopRL.callOnClick();
            }
        });
        mMainTopRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,AddActivity.class);
                intent.putExtra("openday", mTxtOpenDay.getText());
                intent.putExtra("id",mApp.mFirstNumber.getId()+"");
                intent.putExtra("type", "top");
                MainActivity.this.startActivity(intent);
            }
        });
        mSRL.setOnRefreshListener(this);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            showLoginBar("确定要退出应用?","退出",1);
            return false;
        }
        return super.onKeyDown(keyCode,event);
    }
    /**
     * 弹出对话框，
     * @param title    提示语
     * @param btntext   按钮语句
     * @param type      类型，0=登陆，1=退出
     */
    private void showLoginBar(String title, final String btntext, final int type){
        Snackbar.make(mMainTopRL, title, Snackbar.LENGTH_LONG)
                .setAction(btntext, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            MainActivity.this.finish();
                            mApp.del();
                    }
                }).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.toolar_save) {
            Intent intent = new Intent(MainActivity.this,SettingActivity.class);
            MainActivity.this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void onRefresh() {

        BU.getNewNumberHtml(new GetNewDataCallBack() {
            @Override
            void wingetweb(String[] datas) {
                String endday="";
//                for(int i = 0 ;i<datas.length;i++){
//                    if(datas[i].indexOf("期开奖结果")>0){
//                        endday=datas[i].substring(datas[i].indexOf("第"),datas[i].indexOf("期"));
//                        break;
//                    }
//                }
//                Log.v("Stringss",endday);
//                int _intopenday=Integer.getInteger(endday);
//                endday=(_intopenday+1)+"";
                //mApp.NEXTOPENDAY=endday;
                //BU.setShareP("nextday",endday,mContext);
                BU.getByData(new GetDataCallBack() {
                    @Override
                    void wingetweb(List<NumberModle> list) {
                        mApp.LIST_NUMBERS =list;
                        String _dangDay=list.get(0).getOpenDay();
                        int _opnextday=Integer.parseInt(_dangDay);
                        _opnextday=_opnextday+1;
                        mApp.NEXTOPENDAY=_opnextday+"";
                        BU.setShareP("nextday",mApp.NEXTOPENDAY,mContext);
                        Log.v("Stirngss",list.get(0).getOpenDay());
                        Log.v("Stringss",list.get(list.size()-1).getOpenDay());
                        if(null!=mApp.LIST_NUMBERS&&mApp.LIST_NUMBERS.size()>1) {
                            adapter.ChangedData(mApp.LIST_NUMBERS);
                            mTxtOpenDay.setText(mApp.NEXTOPENDAY);
                            String[] _nextdate=BU.getDate(mApp.LIST_NUMBERS.get(0).getOpenDate());
                            mTxtOpenDate.setText(_nextdate[0]);
                            mTxtOpenWeek.setText(_nextdate[1]);
                            mSRL.setRefreshing(false);
                            Intent intent = new Intent(MainActivity.this,GetWebService.class);
                            //_nextdate[0]+" 21:50:00"
                            intent.putExtra("date",_nextdate[0]+" 21:50:00");
                            startService(intent);
                        }
                    }

                    @Override
                    void errgetweb() {

                    }
                },"2017000","2017100");


            }

            @Override
            void errgetweb() {

            }
        });

    }
}
