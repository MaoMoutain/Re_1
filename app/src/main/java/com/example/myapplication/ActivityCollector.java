package com.example.myapplication;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;
//活动管理类
public class ActivityCollector {
    public static List<Activity>activities=new ArrayList<>();

    public static  void addActivity(Activity activity){
        //添加一个活动
        activities.add(activity);
    }

    public static  void removeActivity(Activity activity){
        //删除一个活动
        activities.remove(activity);
    }
    public static void finishAll(){
        //退出程序
        for (Activity activity:activities){
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
