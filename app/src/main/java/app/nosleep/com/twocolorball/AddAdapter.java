package app.nosleep.com.twocolorball;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by nosleep on 16-12-2.
 * 日期：16-12-2下午3:40
 * author:zzh
 * override:
 */

public class AddAdapter extends BaseAdapter implements View.OnClickListener,View.OnLongClickListener {

    private Context mContext;
    private List<NoteModle> mList;
    private MyOnClickItems onClickItems;

    public AddAdapter(Context pContext, List<NoteModle> pList, MyOnClickItems pOnClickItems) {
        this.mContext = pContext;
        this.mList = pList;
        this.onClickItems = pOnClickItems;
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

    public void addItem(NoteModle pNote){
        this.mList.add(pNote);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (null == view || view.getTag() == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.list_item_add_layout, null);
            ViewHolder holder = new ViewHolder();
            holder.ed_num_one = (TextView) view.findViewById(R.id.item_add_number_1);
            holder.ed_num_two = (TextView) view.findViewById(R.id.item_add_number_2);
            holder.ed_num_three = (TextView) view.findViewById(R.id.item_add_number_3);
            holder.ed_num_four = (TextView) view.findViewById(R.id.item_add_number_4);
            holder.ed_num_five = (TextView) view.findViewById(R.id.item_add_number_5);
            holder.ed_num_six = (TextView) view.findViewById(R.id.item_add_number_6);
            holder.ed_num_seven = (TextView) view.findViewById(R.id.item_add_number_7);
            holder.ed_multiple = (TextView) view.findViewById(R.id.item_add_number_multiple);
            holder.ed_num_one.setOnClickListener(this);
            holder.ed_num_two.setOnClickListener(this);
            holder.ed_num_three.setOnClickListener(this);
            holder.ed_num_four.setOnClickListener(this);
            holder.ed_num_five.setOnClickListener(this);
            holder.ed_num_six.setOnClickListener(this);
            holder.ed_num_seven.setOnClickListener(this);
            holder.ed_multiple.setOnClickListener(this);
            holder.ed_num_one.setOnLongClickListener(this);
            holder.ed_num_two.setOnLongClickListener(this);
            holder.ed_num_three.setOnLongClickListener(this);
            holder.ed_num_four.setOnLongClickListener(this);
            holder.ed_num_five.setOnLongClickListener(this);
            holder.ed_num_six.setOnLongClickListener(this);
            holder.ed_num_seven.setOnLongClickListener(this);
            holder.ed_multiple.setOnLongClickListener(this);
            view.setTag(holder);
        }
        NoteModle modle = mList.get(i);
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.ed_multiple.setText(modle.getMultiple() + "");
        holder.ed_multiple.setTag(modle.getMultiple() + ","+modle.getId()+","+i);
        holder.ed_num_one.setText(modle.getNumone());
        holder.ed_num_one.setTag(modle.getNumone()+ ","+modle.getId()+","+i);
        holder.ed_num_two.setText(modle.getNumtwo());
        holder.ed_num_two.setTag(modle.getNumtwo()+ ","+modle.getId()+","+i);
        holder.ed_num_three.setText(modle.getNumthree());
        holder.ed_num_three.setTag(modle.getNumthree()+ ","+modle.getId()+","+i);
        holder.ed_num_four.setText(modle.getNumfour());
        holder.ed_num_four.setTag(modle.getNumfour()+ ","+modle.getId()+","+i);
        holder.ed_num_five.setText(modle.getNumfive());
        holder.ed_num_five.setTag(modle.getNumfive()+ ","+modle.getId()+","+i);
        holder.ed_num_six.setText(modle.getNumsix());
        holder.ed_num_six.setTag(modle.getNumsix()+ ","+modle.getId()+","+i);
        holder.ed_num_seven.setText(modle.getNumseven());
        holder.ed_num_seven.setTag(modle.getNumseven()+ ","+modle.getId()+","+i);
        return view;
    }

    @Override
    public void onClick(View view) {
        Log.v("打你","单价");
        onClickItems.OnClick(view);
    }

    @Override
    public boolean onLongClick(View view) {
        onClickItems.OnLongClick(view);
        return true;
    }

    public class ViewHolder {
        private TextView ed_num_one;
        private TextView ed_num_two;
        private TextView ed_num_three;
        private TextView ed_num_four;
        private TextView ed_num_five;
        private TextView ed_num_six;
        private TextView ed_num_seven;
        private TextView ed_multiple;
    }

    public interface MyOnClickItems {
        void OnClick(View v);
        void OnLongClick(View v);
    }

}
