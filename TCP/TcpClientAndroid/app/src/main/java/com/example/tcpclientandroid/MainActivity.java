package com.example.tcpclientandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.tcpclientandroid.funcs.FuncTcpClient;
import com.example.tcpclientandroid.funcs.FuncTcpServer;

public class MainActivity extends AppCompatActivity {

    private RadioButton radioBtnServer,radioBtnClient;
    private Button btnFuncEnsure;
    private TextView txtShowFunc;
    private MyRadioButtonCheck myRadioButtonCheck = new MyRadioButtonCheck();
    private MyButtonClick myButtonClick = new MyButtonClick();

    private class MyRadioButtonCheck implements RadioButton.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            switch (compoundButton.getId()){
                case R.id.radio_Server:
                    if (b){
                        txtShowFunc.setText("你选则的功能是：服务器");
                    }
                    break;
                case R.id.radio_Client:
                    if (b){
                        txtShowFunc.setText("你选则的功能是：客户端");
                    }
                    break;
            }
        }
    }

    private class MyButtonClick implements Button.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_FunctionEnsure:
                    Intent intent = new Intent();
                    if (radioBtnServer.isChecked()){
                        intent.setClass(MainActivity.this, FuncTcpServer.class);
                        startActivity(intent);
                    }
                    if (radioBtnClient.isChecked()){
                        intent.setClass(MainActivity.this, FuncTcpClient.class);
                        startActivity(intent);
                    }
                    break;
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindID();
        bindListener();
    }

    private void bindID() {
        radioBtnServer = (RadioButton) findViewById(R.id.radio_Server);
        radioBtnClient = (RadioButton) findViewById(R.id.radio_Client);
        btnFuncEnsure = (Button) findViewById(R.id.btn_FunctionEnsure);
        txtShowFunc = (TextView) findViewById(R.id.txt_ShowFunction);
    }

    private void bindListener(){
        radioBtnClient.setOnCheckedChangeListener(myRadioButtonCheck);
        radioBtnServer.setOnCheckedChangeListener(myRadioButtonCheck);
        btnFuncEnsure.setOnClickListener(myButtonClick);
    }
}