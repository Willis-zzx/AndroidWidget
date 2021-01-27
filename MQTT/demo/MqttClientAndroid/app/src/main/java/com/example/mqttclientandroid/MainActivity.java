package com.example.mqttclientandroid;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mqttclientandroid.mqtt.MqttManager;
import com.lichfaker.log.Logger;

import org.eclipse.paho.client.mqttv3.MqttException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class MainActivity extends AppCompatActivity {

    // public static final String URL = "tcp://183.230.40.39:6002";
    public static final String URL = "tcp://broker.hivemq.com:1883";
    private String userName = "123";
    private String password = "123";
    private String clientId = "1234567890cjf";

    String cmdon = "1";
    String cmdoff = "0";
    TextView txtWenDu;
    TextView txtShiDu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        txtWenDu = (TextView) findViewById(R.id.wendu);
        txtShiDu = (TextView) findViewById(R.id.shidu);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean b = MqttManager.getInstance().creatConnect(URL, userName, password, clientId);
                        Logger.d("isConnected: " + b);
                    }
                }).start();
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MqttManager.getInstance().publish("feng", 0, cmdon.getBytes());
                    }
                }).start();
            }
        });

        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MqttManager.getInstance().publish("feng", 0, cmdoff.getBytes());
                    }
                }).start();
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MqttManager.getInstance().subscribe("w", 2);
                        MqttManager.getInstance().subscribe("s", 2);
                    }
                }).start();
            }
        });

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            MqttManager.getInstance().disConnect();
                        } catch (MqttException e) {

                        }
                    }
                }).start();

            }
        });


    }

    /**
     * 订阅接收到的消息
     * 这里的Event类型可以根据需要自定义, 这里只做基础的演示
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showdata(String event) {
        String[] msg = event.split(",");
        if (msg[0].equals("w")) {
            txtWenDu.setText(msg[1]);
        }
        if (msg[0].equals("s")) {
            txtShiDu.setText(msg[1]);
        }
        Toast.makeText(this, msg[0] + msg[1], Toast.LENGTH_SHORT).show();
    }

    ;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}

