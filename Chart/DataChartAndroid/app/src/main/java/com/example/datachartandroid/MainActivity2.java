package com.example.datachartandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个以ListView为基础的下拉刷新
 */
public class MainActivity2 extends AppCompatActivity {
    private ListView mListView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<String> stringList=new ArrayList<>();
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        for (int x=0 ; x<16;x++){
            stringList.add("新浪微博：搏击爱好者Z"+"---"+x);
        }
        initView();
    }

    private void initView() {
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.srfl);
        //设置进度圈的大小;(这里面只有两个值SwipeRefreshLayout.LARGE和DEFAULT，后者是默认效果)
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        //设置进度圈的背景色。这里随便给他设置了一个颜色：浅绿色
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.CYAN);
        //设置进度动画的颜色。这里面最多可以指定四个颜色，我这也是随机设置的，大家知道怎么用就可以了
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_dark
                ,android.R.color.holo_blue_dark
                ,android.R.color.holo_red_dark
                ,android.R.color.widget_edittext_dark);
        mListView= (ListView) findViewById(R.id.listview);
        adapter = new ArrayAdapter<>(MainActivity2.this, android.R.layout.simple_list_item_1, stringList);
        mListView.setAdapter(adapter);

        //设置手势滑动监听器
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                //发送一个延时1秒的handler信息
                handler.sendEmptyMessageDelayed(199,1000);
            }
        });

        //给listview设置一个滑动的监听
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            //当滑动状态发生改变的时候执行
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState){
                    //当不滚动的时候
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:

                        //判断是否是最底部
                        if(view.getLastVisiblePosition()==(view.getCount())-1){
                            for(int x=0;x<5;x++){
                                stringList.add(stringList.size(),"魔兽世界"+x);
                            }
                            adapter.notifyDataSetChanged();
                        }
                        break;
                }
            }
            //正在滑动的时候执行
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private final Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==199){
                stringList.add(0,"英雄联盟");
                adapter.notifyDataSetChanged();
                //设置组件的刷洗状态；false代表关闭
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    };
}