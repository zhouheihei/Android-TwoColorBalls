package app.nosleep.com.twocolorball;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by nosleep on 16-12-2.
 * 日期：16-12-2下午3:40
 * author:zzh
 * override:
 */

public class ListAdapter extends BaseAdapter implements View.OnClickListener{

    private Context mContext;
    private List<NumberModle> mList;
    private MyOnClickItems onClickItems;
    private int[] mColors=new int[]{R.color.colorMainListItemthree,R.color.colorMainListItemfour,R.color.colorMainListItemfive,R.color.colorMainListItemsix};
    private int mColorIndex=0;
    private BaseUtils BU;

    public ListAdapter(Context pContext,List<NumberModle> pList,MyOnClickItems pOnClickItems){
        this.mContext=pContext;
        this.mList=pList;
        this.onClickItems=pOnClickItems;
        this.BU=new BaseUtils(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(null==view||view.getTag()==null){
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item_main_layout,null);
            ViewHolder holder = new ViewHolder();
            holder.txt_number= (TextView) view.findViewById(R.id.list_item_number);
            holder.txt_data= (TextView) view.findViewById(R.id.list_item_date);
            holder.txt_week= (TextView) view.findViewById(R.id.list_item_week);
            holder.txt_num_one= (TextView) view.findViewById(R.id.number_one);
            holder.txt_num_two= (TextView) view.findViewById(R.id.number_two);
            holder.txt_num_three= (TextView) view.findViewById(R.id.number_three);
            holder.txt_num_four= (TextView) view.findViewById(R.id.number_four);
            holder.txt_num_five= (TextView) view.findViewById(R.id.number_five);
            holder.txt_num_six= (TextView) view.findViewById(R.id.number_six);
            holder.txt_num_seven= (TextView) view.findViewById(R.id.number_seven);
            holder.txt_money= (TextView) view.findViewById(R.id.list_item_money);
            holder.rl_mainlist= (RelativeLayout) view.findViewById(R.id.main_list_rl);
            view.setTag(holder);

        }
        NumberModle modle = mList.get(i);
        ViewHolder holder = (ViewHolder) view.getTag();
        String[] _openNumbers =modle.getOpenNumbers().split(",");
        holder.txt_number.setText(modle.getOpenDay());
        holder.txt_data.setText(modle.getOpenDate());
        holder.txt_week.setText("星期"+modle.getOpenWeek());
        holder.txt_num_one.setText(_openNumbers[0]);
        holder.txt_num_two.setText(_openNumbers[1]);
        holder.txt_num_three.setText(_openNumbers[2]);
        holder.txt_num_four.setText(_openNumbers[3]);
        holder.txt_num_five.setText(_openNumbers[4]);
        holder.txt_num_six.setText(_openNumbers[5]);
        holder.txt_num_seven.setText(_openNumbers[6]);
        modle.setBingoMoney(BU.chengeByBalls(modle.getOpenNumbers(),modle.getOpenDay()));
        if(modle.getBingoMoney()==100000){
            holder.txt_money.setText("二等奖");
        }else if(modle.getBingoMoney()==1000000){
            holder.txt_money.setText("一等奖");
        }else{
            holder.txt_money.setText(modle.getBingoMoney()+"");
        }
        holder.txt_money.setTag(modle.getId());
//        holder.rl_mainlist.setBackgroundResource(mColors[mColorIndex]);
        holder.rl_mainlist.setBackgroundResource(R.color.colorMainListItemone);
        if(mColorIndex+1<mColors.length){
            mColorIndex=mColorIndex+1;
        }else{
            mColorIndex=0;
        }
        view.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        onClickItems.OnClick(view);
    }

    public class ViewHolder{
        public RelativeLayout rl_mainlist;
        public TextView txt_number;
        public TextView txt_data;
        public TextView txt_week;
        public TextView txt_num_one;
        public TextView txt_num_two;
        public TextView txt_num_three;
        public TextView txt_num_four;
        public TextView txt_num_five;
        public TextView txt_num_six;
        public TextView txt_num_seven;
        public TextView txt_money;
    }
    public interface MyOnClickItems{
        void OnClick(View v);
        void OnLongClick(View v);
    }

    public void ChangedData(List<NumberModle> list){
        mList=list;
        this.notifyDataSetChanged();
    }

}
