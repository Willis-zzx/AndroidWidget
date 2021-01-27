package com.example.datachartandroid;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.datachartandroid.MPChart.DynamicLineChartManager;
import com.example.datachartandroid.mqtt.MqttManager;
import com.github.mikephil.charting.charts.LineChart;
import com.lichfaker.log.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 实时曲线：接收MQTT发送来的实时数据
 */
public class MainActivity7 extends AppCompatActivity {


    private DynamicLineChartManager dynamicLineChartManager2;
    private List<Integer> list = new ArrayList<>(); //数据集合
    private List<String> names = new ArrayList<>(); //折线名字集合
    private List<Integer> colour = new ArrayList<>();//折线颜色集合


    //mqtt服务器地址
    public static final String URL = "";
    //mqtt服务器用户名
    private String username = "";
    //mqtt服务器密码
    private String password = "";
    //mqtt服务器clientId
    private String clientId = "";
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean b = MqttManager.getInstance().creatConnect(URL, username, password, clientId);
                        Logger.d("isConnected: " + b);
                        if (b == true) {
                            MqttManager.getInstance().subscribe("318", 2);
                        }
                    }
                }).start();
            }
        });

        LineChart mChart2 = (LineChart) findViewById(R.id.dynamic_chart2);
        //折线名字
        names.add("温度");
        names.add("压强");
        names.add("其他");
        //折线颜色
        colour.add(Color.CYAN);
        colour.add(Color.GREEN);
        colour.add(Color.BLUE);


        dynamicLineChartManager2 = new DynamicLineChartManager(mChart2, names, colour);

        dynamicLineChartManager2.setYAxis(100, 0, 10);

    }

    /**
     * 订阅接收到的消息
     * 这里的Event类型可以根据需要自定义, 这里只做基础的演示
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ShowData(String event) {
        String[] msg = event.split(",");
        if (msg[0].equals("318")) {
            list.add(Integer.parseInt(msg[1]));
            list.add(Integer.parseInt(msg[2]));
            list.add(Integer.parseInt(msg[3]));
            dynamicLineChartManager2.addEntry(list);
            list.clear();
        }
    }


    /**
     * 报错：
     * No subscribers registered for event class java.lang.String
     * No subscribers registered for event class org.greenrobot.eventbus.NoSubscriberEvent
     * 修改：
     * onStart()和onDestroy()增加一个判断
     */

    @Override
    public void onStart() {
        super.onStart();
        //加上判断
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        //当结束程序时关掉Timer
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

}
