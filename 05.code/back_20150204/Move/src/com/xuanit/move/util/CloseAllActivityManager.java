package com.xuanit.move.util;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class CloseAllActivityManager extends Application {

	
	 /**
     * Activity列表
     */
    private static List<Activity> activityList = new LinkedList<Activity>();
    
    /**
     * 全局唯一实例
     */
    private static CloseAllActivityManager instance;
    

    /**
     * 该类采用单例模式，不能实例化
     */
    private CloseAllActivityManager()
    {
    }

    /**
     * 获取类实例对象
     * @return    CloseAllActivityManager
     */
    public static CloseAllActivityManager getInstance() {
        if (null == instance) {
            instance = new CloseAllActivityManager();  
        }
        return instance;
    }


    /**
     * 保存Activity到现有列表中
     * @param activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }


    /**
     * 关闭保存的Activity
     */
    public void exit() {
        if(activityList!=null)
        {
            Activity activity;
            
            for (int i=0; i<activityList.size(); i++) {            
                activity = activityList.get(i);
                
                if(activity!=null)
                {
                    if(!activity.isFinishing())
                    {
                        activity.finish();
                    }
                    
                    activity = null;
                }
                
                activityList.remove(i);                
                i--;
            }
        }
    }
	
}
