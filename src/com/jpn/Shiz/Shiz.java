package com.jpn.Shiz;

import android.app.Application;
import android.content.Context;

/**
 * Created by saeed on 1/16/16.
 */
public class Shiz extends Application {

    private static Context context;

    /**
     * Get application context
     *
     * @return context of application
     */
    public static Context getContext() {
        return context;
    }


    public void onCreate() {
        super.onCreate();
        Shiz.context = getApplicationContext();
    }
}
