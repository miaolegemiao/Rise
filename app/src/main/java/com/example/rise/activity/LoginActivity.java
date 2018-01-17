package com.example.rise.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rise.MyApplication;
import com.example.rise.R;
import com.example.rise.util.Loadding;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class LoginActivity extends AppCompatActivity {

    private String add, account, password;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    Loadding loadding;
    private Boolean isRemember = false;
    private Intent intent;

    @ViewInject(R.id.username)
    EditText userName;
    @ViewInject(R.id.password)
    EditText passWord;
    @ViewInject(R.id.checkbox1)
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        x.view().inject(this);
        MyApplication.getInstance().addActivity(this);

        init();
    }
    //初始化
    private void init(){
        loadding = new Loadding(this);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);//创建一个数据库
        editor = prefs.edit();
        add = prefs.getString("add", "");
        //从数据库中取出remember_password参数的值
        isRemember = prefs.getBoolean("remember_password", false);
        checkBox.setChecked(isRemember);
        //从数据库中取出account参数的值
        account = prefs.getString("account", "");
        //从数据库中取出password参数的值
        password = prefs.getString("password", "");
        intent = new Intent();
        if(isRemember){
            userName.setText(account);
            passWord.setText(password);

        }else{
            userName.setText(account);
            passWord.setText("");
        }
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()){
                    isRemember = true;
                }else{
                    isRemember = false;
                }
            }
        });
    }

    @Event( value = { R.id.setting, R.id.login_main_interface },
            type = View.OnClickListener.class )
    private void btnClick(View v){
        switch(v.getId()){
            case R.id.setting:
                intent = new Intent(LoginActivity.this, GatewayAddressActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.login_main_interface:
                loadding.show("正在加载中。。。");
                if(add.equals("")){
                    Toast.makeText(LoginActivity.this, "请先设置网关地址",
                            Toast.LENGTH_SHORT).show();
                    intent.setClass(LoginActivity.this, GatewayAddressActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    getData(add + "WebWorkMeritSheet_Sanley.asmx/GetUserInfo");
                }
                break;
            default:
                break;
        }
    }

    private void getData(String address) {

        String usernameValue = userName.getText().toString();
        String passwordValue = passWord.getText().toString();
        RequestParams params = new RequestParams(address);
        params.addBodyParameter("Number", usernameValue);
        params.addBodyParameter("PassWord", passwordValue);

        x.http().post(params, new org.xutils.common.Callback.CommonCallback<String>() {

            @Override
            public void onCancelled(CancelledException arg0) {
            }

            @Override
            public void onError(Throwable ex, boolean arg1) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onSuccess(String arg0) {
                try {
                    JSONObject object = new JSONObject(arg0);
                    String message = object.getString("Message");
                    //用户名和密码错误的情况
                    if(message.equals("获取用户信息失败")){
                        Toast.makeText(LoginActivity.this, "用户名和密码错误",
                                Toast.LENGTH_SHORT).show();
                    }else if(message.equals("获取用户信息成功")){
                    //用户名和密码成功的情况
                        String data = object.getString("Data");
                        JSONArray object1 = new JSONArray(data);
                        JSONObject object2 = new JSONObject(object1.get(0).toString());
                        final String number = object2.getString("Number");
                        final String un = object2.getString("UserName");

                        if(checkBox.isChecked()){//检查复选框是否被选中
                            //给数据库附加三个key-value对，然后提交
                            editor.putBoolean("remember_password", true);
                            editor.putString("account", userName.getText().toString().trim());
                            editor.putString("password", passWord.getText().toString().trim());
                        }else{
                            //如果复选框没有选中的话也给数据库附加三个key-value对，然后提交
                            editor.putBoolean("remember_password", false);
                            editor.putString("account", "");
                            editor.putString("password", "");
                        }
                        editor.putString("Number", number);
                        editor.putString("UN", un);
                        editor.putString("ADD", add);
                        editor.apply();
                        //这是用户名密码成功登陆的情况，登陆进去主界面了，并且将三条数据通过intent传入主界面当中，然后
                        //finish()掉它们，并且给用户一个提示：用户登录成功
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        loadding.close();
                        Toast.makeText(LoginActivity.this, "用户登录成功",
                                Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
