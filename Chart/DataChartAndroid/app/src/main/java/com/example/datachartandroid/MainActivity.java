package com.example.datachartandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 一个以ListView为基础的时间轴
 */
public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private List<String> mListBean;
    private MyAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.list_time);
        mListBean = new ArrayList();
        for (int i = 0; i < 100; i++) {
            mListBean.add("数据第" + i + "条");
        }
        mAdapter = new MyAdapter(this, mListBean);
        mListView.setAdapter(mAdapter);
    }


}

class MyAdapter extends BaseAdapter {
    private List<String> mList;
    private Context mContext;

    public MyAdapter(Context context, List<String> mListBean) {
        this.mList = mListBean;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item, null);
            holder.time = (TextView) convertView.findViewById(R.id.time_tv);
            holder.msg = (TextView) convertView.findViewById(R.id.msg_tv);
            holder.up = (View) convertView.findViewById(R.id.line_up);
            holder.down = (View) convertView.findViewById(R.id.line_dowm);
            holder.quan = (ImageView) convertView.findViewById(R.id.quan);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == 0) {//第一条隐藏上边的线
            holder.up.setVisibility(View.INVISIBLE);
        } else if (position == mList.size() - 1) {//最后一条影藏下边的线
            holder.down.setVisibility(View.INVISIBLE);
        } else {
            holder.up.setVisibility(View.VISIBLE);
            holder.down.setVisibility(View.VISIBLE);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd hh:mm:ss");
        String time = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        String msg = mList.get(position);
        holder.time.setText(time);

        holder.msg.setText(msg);

        return convertView;
    }


    private static class ViewHolder {
        private TextView time;
        private TextView msg;
        private View up;
        private View down;
        private ImageView quan;
    }
}