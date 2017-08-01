package app.nosleep.com.twocolorball;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by nosleep on 16-10-25.
 * 日期：16-10-25上午10:21
 * author:zzh
 * override:
 */

public class WelcomApapter extends BaseAdapter {

    private List<Integer> mData =null;
    private LayoutInflater mInflater;
    private Context mContext=null;

    public WelcomApapter(Context pcontext, List<Integer> list){
        this.mContext=pcontext;
        this.mInflater = LayoutInflater.from(mContext);
        this.mData=list;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView = mInflater.inflate(R.layout.apater_welcom_one, null);
            holder.img= (ImageView) convertView.findViewById(R.id.apater_img);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.img.setImageResource(mData.get(position));
        return convertView;
    }
    public final class ViewHolder{
        public ImageView img;
    }
}
