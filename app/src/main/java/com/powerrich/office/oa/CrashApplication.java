package com.powerrich.office.oa;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;


public class CrashApplication extends Application {    
	private List<Activity> activitylist=new LinkedList<Activity>();
    @Override    
    public void onCreate() {    
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();    
        crashHandler.init(getApplicationContext());    
    }    
    public void addActivity(Activity activity){ 
        activitylist.add(activity); 
    } 
 
    public void exit(){ 
        for(Activity ac:activitylist){ 
            ac.finish(); 
        } 
        System.exit(0); 
    } 
} 
