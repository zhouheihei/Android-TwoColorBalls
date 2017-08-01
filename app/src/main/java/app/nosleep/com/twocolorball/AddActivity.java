package app.nosleep.com.twocolorball;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import java.util.List;

public class AddActivity extends BaseActivity implements AddAdapter.MyOnClickItems, View.OnClickListener {

    ListView mListView = null;
    Toolbar toolbar = null;
    FloatingActionButton fab = null;
    RelativeLayout mAddRl = null;
    TextView mTxtOldNumber = null;
    TextView mTxtNext = null;
    EditText mEdNumber = null;
    TextView mTxtTitleTip = null;
    TextView mTxtTitle = null;
    TextView mTxtErrTip = null;
    TextView mTxtMoney = null;
    RelativeLayout mRLalert=null;
    TextView mTxtOk=null;
    TextView mTxtEsc=null;


    private boolean mIsChenge = false;
    private Context mContext = null;
    private AddAdapter mAdapter = null;
    private Intent mItent = null;
    private List<NoteModle> mList = null;
    private String mNumberId;
    private BaseApp mApp = null;
    private BaseSQLiteOpenHelper mHelp = null;
    private String StartType;
    private String mOpenDay;

    //删除数据
    private String mDelId;
    private String mDelLine;

    //    private int mNumberListIndex=1;

    //新建号码参数
    private List<NoteModle> mListNote = null;
    private String[] mAddNumbers;
    private int mAddIndex = 1;

    //修改号码参数
    private String mUpdateColName;
    private String mUpdateId;
    private String[] mUpdateTag;
    private String mUpdateLin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add;
    }

    @Override
    protected void init() {
        this.mContext = getApplicationContext();
        this.mApp = (BaseApp) getApplication();
        this.mItent = getIntent();
        this.mHelp = new BaseSQLiteOpenHelper(mContext);
        this.mNumberId = mItent.getStringExtra("id");
        this.StartType = mItent.getStringExtra("type");
        this.mOpenDay = mItent.getStringExtra("openday");
        this.mList = mHelp.getNoteAll(mOpenDay);
        this.mListView = (ListView) findViewById(R.id.add_list_number);
        this.mAdapter = new AddAdapter(mContext, mList, this);
        this.mListView.setAdapter(mAdapter);
        this.mTxtNext = (TextView) findViewById(R.id.add_add_next);
        this.mTxtOldNumber = (TextView) findViewById(R.id.add_add_old);
        this.mEdNumber = (EditText) findViewById(R.id.add_add_ed_number);
        this.mTxtTitleTip = (TextView) findViewById(R.id.add_add_tip);
        this.mTxtMoney = (TextView) findViewById(R.id.add_txt_money);
        this.mTxtErrTip = (TextView) findViewById(R.id.add_add_errtip);
        this.mTxtTitle = (TextView) findViewById(R.id.add_txt_title);
        this.mTxtOk= (TextView) findViewById(R.id.add_add_alertok);
        this.mTxtEsc= (TextView) findViewById(R.id.add_add_alertesc);
        this.mRLalert= (RelativeLayout) findViewById(R.id.add_add_alert);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.addfab);
        mAddRl = (RelativeLayout) findViewById(R.id.add_content_rl);

    }

    @Override
    protected void setView() {
        setSupportActionBar(toolbar);
        this.mTxtTitle.setText(this.mOpenDay + "期");
        if (this.StartType.equals("top")) {
            this.mAddRl.setOnClickListener(this);
            this.mTxtNext.setOnClickListener(this);
            this.mTxtOldNumber.setOnClickListener(this);
            this.mEdNumber.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (mEdNumber.getText().toString().length() == 2
                            && !mIsChenge
                            && mAddIndex != 7
                            && !mTxtNext.getText().equals("修改")) {
                        onClick(mTxtNext);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
        }
        getMoney();
    }

    @Override
    protected void setListenner() {
        if (this.StartType.equals("top")) {
            fab.setOnClickListener(this);
        }

        mTxtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("del","id:"+mDelId+"line:"+mDelLine);
                mHelp.deleteByNoteID(mDelId);
                mList.remove(Integer.parseInt(mDelLine));
                mAdapter.notifyDataSetChanged();
                getMoney();
                mRLalert.setVisibility(View.INVISIBLE);
            }
        });

        mTxtEsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRLalert.setVisibility(View.INVISIBLE);
            }
        });

    }

    //Item Onclick
    @Override
    public void OnClick(View v) {
        if (StartType.equals("top")) {
            String tag = (String) v.getTag();
            String[] tags = tag.split(",");
            mTxtOldNumber.setVisibility(View.INVISIBLE);
            mTxtTitleTip.setText("修改红色号码");
            mTxtNext.setText("修改");
            mTxtErrTip.setText("");
            mEdNumber.setText(tags[0]);
            mUpdateLin = tags[2];
            String updatekey = "";
            if (v.getId() == R.id.item_add_number_1) {
                updatekey = "no_num1";
            }
            if (v.getId() == R.id.item_add_number_2) {
                updatekey = "no_num2";
            }
            if (v.getId() == R.id.item_add_number_3) {
                updatekey = "no_num3";
            }
            if (v.getId() == R.id.item_add_number_4) {
                updatekey = "no_num4";
            }
            if (v.getId() == R.id.item_add_number_5) {
                updatekey = "no_num5";
            }
            if (v.getId() == R.id.item_add_number_6) {
                updatekey = "no_num6";
            }
            if (v.getId() == R.id.item_add_number_7) {
                updatekey = "no_num7";
                mTxtTitleTip.setText("修改蓝色号码");
            }
            if (v.getId() == R.id.item_add_number_multiple) {
                updatekey = "no_multiple";
                mTxtTitleTip.setText("修改倍数");
            }
            mUpdateId = tags[1];
            mUpdateColName = updatekey;
            mUpdateTag = tags;
            if (mAddRl.getVisibility() == View.INVISIBLE) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
            mAddRl.setVisibility(View.VISIBLE);
            mEdNumber.setFocusable(true);
            mEdNumber.setFocusableInTouchMode(true);
            mEdNumber.requestFocus();
        }
    }

    @Override
    public void OnLongClick(View v) {
        Log.v("longclick","onclick......");
        if(null!= v.getTag()){
            String tag = (String) v.getTag();
            String[] tags = tag.split(",");
            mDelId=tags[1];
            mDelLine=tags[2];
            if(mRLalert.getVisibility()==View.INVISIBLE){
                mRLalert.setVisibility(View.VISIBLE);
            }
        }

    }

    //activity onclick
    @Override
    public void onClick(View view) {
        Log.v("click", view.getId() + "");
        if (view.getId() == R.id.add_add_next) {
            if (mTxtNext.getText().equals("修改")) {
                oncliToSave();
            } else {
                onclickToNext();
            }
        } else if (view.getId() == R.id.add_add_old) {
            onclickToOld();
        } else if (view.getId() == R.id.add_content_rl) {
            //mAddRl.setVisibility(View.INVISIBLE);
        } else if (view.getId() == R.id.addfab) {
            onclickToAddFod();
        }
    }

    //单击下一个/保存按钮
    private void onclickToNext() {
        if (!isChengeUpdate(mEdNumber.getText() + "", mTxtTitleTip.getText() + "")) {
            return;
        }
        if(null==mAddNumbers){
            return;
        }
        mAddNumbers[mAddIndex] = mEdNumber.getText() + "";
        if (mAddIndex + 1 <= 7 && !mAddNumbers[mAddIndex].equals("")) {
            mIsChenge = true;
            mTxtOldNumber.setText(mAddNumbers[mAddIndex]);
            mAddIndex = mAddIndex + 1;
            mEdNumber.setText(mAddNumbers[mAddIndex]);
            mIsChenge = false;
        }
        if (mTxtNext.getText().equals("保存")) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (mAddNumbers[i].equals(mAddNumbers[j]) && j != i) {
                        mTxtErrTip.setText("号码" + mAddNumbers[i] + "重复!");
                        return;
                    }
                }
            }
            NoteModle _note = new NoteModle();
            _note.setMultiple(Integer.parseInt(mAddNumbers[mAddNumbers.length - 1]));
            _note = StringArrayToObject(_note, mAddNumbers);
            mHelp.insertToNote(mAddNumbers[mAddNumbers.length - 1], mAddNumbers, mOpenDay);
            mAdapter.addItem(_note);
            mAddRl.setVisibility(View.INVISIBLE);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            getMoney();
        }
        mTxtErrTip.setText("");
        if (mAddIndex == 7)
            mTxtNext.setText("保存");
        if (mAddIndex == 7)
            mTxtTitleTip.setText("本注购买倍数");
        else if (mAddIndex == 6)
            mTxtTitleTip.setText("蓝色球");
        else {
            mTxtTitleTip.setText((mAddIndex + 1) + "号红色球");
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (mAddRl.getVisibility() == View.VISIBLE) {
                mAddRl.setVisibility(View.INVISIBLE);
                return false;
            }
        }
        return super.onKeyDown(keyCode,event);
    }
    //单击修改
    private void oncliToSave() {
        if (!isChengeUpdate(mEdNumber.getText() + "", mTxtTitleTip.getText() + "")) {
            return;
        }
        NoteModle _note = mList.get(Integer.parseInt(mUpdateLin));
        String edstr = mEdNumber.getText() + "";
        int _edint=Integer.parseInt(edstr);
        if(_edint<10){
            edstr="0"+_edint;
        }
        Log.v("if","开始"+edstr);
        if (mTxtTitleTip.getText().equals("修改红色号码")) {
            Log.v("if","开始判断"+_note.getNumone()+_note.getNumtwo()+_note.getNumthree()+_note.getNumfour()+_note.getNumfive()+edstr);
            if (_note.getNumone().equals(edstr) ||
                    _note.getNumtwo().equals(edstr) ||
                    _note.getNumthree().equals(edstr) ||
                    _note.getNumfour().equals(edstr) ||
                    _note.getNumfive().equals(edstr)) {
                    mTxtErrTip.setText(edstr+"号重复！");
                return;
            }
        }
        mHelp.updateByNote(mEdNumber.getText() + "", mUpdateId, mUpdateColName);
        mAddRl.setVisibility(View.INVISIBLE);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        if (mUpdateColName.equals("no_num1")) {
            mList.get(Integer.parseInt(mUpdateLin)).setNumone(mEdNumber.getText() + "");
        }
        if (mUpdateColName.equals("no_num2")) {
            mList.get(Integer.parseInt(mUpdateLin)).setNumtwo(mEdNumber.getText() + "");
        }
        if (mUpdateColName.equals("no_num3")) {
            mList.get(Integer.parseInt(mUpdateLin)).setNumthree(mEdNumber.getText() + "");
        }
        if (mUpdateColName.equals("no_num4")) {
            mList.get(Integer.parseInt(mUpdateLin)).setNumfour(mEdNumber.getText() + "");
        }
        if (mUpdateColName.equals("no_num5")) {
            mList.get(Integer.parseInt(mUpdateLin)).setNumfive(mEdNumber.getText() + "");
        }
        if (mUpdateColName.equals("no_num6")) {
            mList.get(Integer.parseInt(mUpdateLin)).setNumsix(mEdNumber.getText() + "");
        }
        if (mUpdateColName.equals("no_num7")) {
            mList.get(Integer.parseInt(mUpdateLin)).setNumseven(mEdNumber.getText() + "");
        }
        if (mUpdateColName.equals("no_multiple")) {
            mList.get(Integer.parseInt(mUpdateLin)).setMultiple(Integer.parseInt(mEdNumber.getText() + ""));
        }
        mAdapter.notifyDataSetChanged();
        getMoney();
    }

    private NoteModle StringArrayToObject(NoteModle note, String[] arry) {
        note.setNumone(arry[0]);
        note.setNumtwo(arry[1]);
        note.setNumthree(arry[2]);
        note.setNumfour(arry[3]);
        note.setNumfive(arry[4]);
        note.setNumsix(arry[5]);
        note.setNumseven(arry[6]);
        return note;
    }

    //单击上一个
    private void onclickToOld() {
        mIsChenge = true;
        mAddNumbers[mAddIndex] = mEdNumber.getText() + "";
        if ((mAddIndex - 1) >= 0) {
            mAddIndex = mAddIndex - 1;
            mEdNumber.setText(mAddNumbers[mAddIndex]);
            if ((mAddIndex - 1) >= 0) {
                mTxtOldNumber.setText(mAddNumbers[mAddIndex - 1]);
            }
            mTxtTitleTip.setText("第" + (mAddIndex + 1) + "个号码");
        } else {
            mTxtOldNumber.setText("");
        }
        if (mAddIndex < 7)
            mTxtNext.setText("下一个");
        if (mAddIndex == 7)
            mTxtTitleTip.setText("本注购买倍数");
        else if (mAddIndex == 6)
            mTxtTitleTip.setText("蓝色球");
        else {
            mTxtTitleTip.setText((mAddIndex + 1) + "号红色球");
        }
        mIsChenge = false;
    }

    //单击添加按钮
    private void onclickToAddFod() {
        mAddNumbers = new String[]{"", "", "", "", "", "", "", ""};
        mAddIndex = 0;
        mTxtOldNumber.setVisibility(View.VISIBLE);
        mTxtTitleTip.setText((mAddIndex + 1) + "号红色球");
        mTxtNext.setText("下一个");
        mEdNumber.setHint("00");
        mEdNumber.setText("");
        mTxtOldNumber.setText("");
        mTxtErrTip.setText("");
        mAddRl.setVisibility(View.VISIBLE);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        mEdNumber.setFocusable(true);
        mEdNumber.setFocusableInTouchMode(true);
        mEdNumber.requestFocus();
    }

    //判断输入合法性
    private boolean isChengeUpdate(String value, String tip) {
        if (value.equals("")) {
            mTxtErrTip.setText("号码不能空!");
            return false;
        }
        int _int = Integer.parseInt(mEdNumber.getText() + "");
        if (_int <= 0) {
            mTxtErrTip.setText("号码不能为零!");
            return false;
        }
        Log.v("chenge", "号码" + value + "tip" + tip);
        if ((_int > 33 && tip.equals("修改红色号码")) ||
                (_int > 16 && tip.equals("修改蓝色号码")) ||
                (_int > 99 && tip.equals("修改倍数"))
                ) {
            mTxtErrTip.setText("号码输入有误!");
            return false;
        }
        if (
                (_int > 33 && tip.equals("1号红色球")) ||
                        (_int > 33 && tip.equals("2号红色球")) ||
                        (_int > 33 && tip.equals("3号红色球")) ||
                        (_int > 33 && tip.equals("4号红色球")) ||
                        (_int > 33 && tip.equals("5号红色球")) ||
                        (_int > 33 && tip.equals("6号红色球")) ||
                        (_int > 16 && tip.equals("蓝色球")) ||
                        (_int > 99 && tip.equals("本注购买倍数"))
                ) {
            mTxtErrTip.setText("号码输入有误!");
            return false;
        }
        return true;
    }

    private void getMoney() {
        int mulipt=0;
        for(int i =0;i<mList.size();i++){
            mulipt=mulipt+mList.get(i).getMultiple();
        }
        this.mTxtMoney.setText("购买金额:" + mulipt * 2 + "元");
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
