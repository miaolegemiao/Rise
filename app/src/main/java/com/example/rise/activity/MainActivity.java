package com.example.rise.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.example.rise.MyApplication;
import com.example.rise.R;
import com.example.rise.adapter.MainViewAdapter;
import com.example.rise.fragment.TabFragment1;
import com.example.rise.fragment.TabFragment11;
import com.example.rise.fragment.TabFragment2;
import com.example.rise.fragment.TabFragment3;
import com.example.rise.fragment.TabFragment4;
import com.example.rise.listener.OnTabSelectedListener;
import com.example.rise.widget.Tab;
import com.example.rise.widget.TabContainerView;

import org.xutils.x;

public class MainActivity extends AppCompatActivity {
    public static MainActivity instance2 = null;
    private Intent intent;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_container);
        x.view().inject(this);
        MyApplication.getInstance().addActivity(this);
        instance2 = this;
        intent = new Intent();
        /*
         * 控件的初始化，然后加上适配器，控件的点击事件
         */
        TabContainerView tabContainerView = (TabContainerView) findViewById(R.id.tab_container);
        MainViewAdapter mainViewAdapter = new MainViewAdapter(getSupportFragmentManager(),
                new Fragment[] {new TabFragment1(), new TabFragment2(), new TabFragment3(),
                        new TabFragment4()});
        mainViewAdapter.setHasMsgIndex(0);
        tabContainerView.setAdapter(mainViewAdapter);
        tabContainerView.setOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {
                Log.e("Fuck the code!", tab.getIndex() + "");
                if(tab.getIndex() == 0){
                    //TabFragment1.speechInput.setText("");
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                intent.setAction(Intent.ACTION_MAIN);               // "android.intent.action.MAIN"
                intent.addCategory(Intent.CATEGORY_HOME);           //"android.intent.category.HOME"
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onKeyUp(keyCode, event);
    }
}
