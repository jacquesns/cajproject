package com.example.knowledge.programming.activities;

import android.app.Application;
import android.content.Context;

/**
 * Created by jacquesdossantos on 2017-02-27.
 */

public class ApplicationContext extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }

}
