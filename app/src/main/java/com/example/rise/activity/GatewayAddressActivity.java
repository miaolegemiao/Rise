package com.example.rise.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rise.MyApplication;
import com.example.rise.R;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class GatewayAddressActivity extends AppCompatActivity {

    @ViewInject(R.id.gatewayaddress)
    EditText gatewayAddress;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gateway_address);
        x.view().inject(this);
        MyApplication.getInstance().addActivity(this);

        init();
    }

    private void init(){
        prefs = PreferenceManager.getDefaultSharedPreferences(this);//创建一个数据库
        editor = prefs.edit();
        intent = new Intent();
    }

    @Event(value = { R.id.back_button, R.id.restoredefaults, R.id.determine },
            type = View.OnClickListener.class)
    private void btnClick(View v){
        switch (v.getId()){
            //返回按钮
            case R.id.back_button:
                intent.setClass(GatewayAddressActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            //回复默认按钮
            case R.id.restoredefaults:
                gatewayAddress.setText("http://222.173.103.228:10066/");
                break;
            //确认按钮
            case R.id.determine:
                String address = gatewayAddress.getText().toString();
                editor.putString("add", address);
                editor.apply();
                intent.setClass(GatewayAddressActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(GatewayAddressActivity.this, "设置成功，请重新登录",
                        Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
