package com.example.rise;

import android.app.Activity;
import android.app.Application;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
	public static final int I_WODESHEBEI = 1;            //我的设备
	public static final int I_SHEBEIZHUANGTAI = 2;       //设备状态
	public static final int I_YUANCHENG = 3;             //远程
	public static final int I_SHEBEIQUXIAN = 4;          //设备曲线
	public static final int I_YUNXINGCANSHU = 0;         //运行参数
	public static String nameString = "";

    private List<Activity> activities = new ArrayList<>();
    private static MyApplication instance;

    @Override
    public void onCreate(){
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);
        instance = this;
    }

    public static MyApplication getInstance(){
        return instance;
    }

    public void addActivity(Activity activity){
        activities.add(activity);
    }

    public void removeActivity(Activity activity){
        if(activity != null){
            this.activities.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    public void exit(){
        for(Activity activity : activities){
            if(activity != null){
                activity.finish();
            }
        }
        System.exit(0);          //结束当前正在运行当中的虚拟机System.exit(status)
                                 // 不管status为何值都会退出程序。
                                // 和return 相比有以下不同点：   return是回到上一层，而System.exit(status)是回到最上层
    }
}
