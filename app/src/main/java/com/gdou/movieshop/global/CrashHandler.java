package com.gdou.movieshop.global;

import android.content.Context;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private Context context;
    private static CrashHandler crashHandler;

    private CrashHandler() {

    }

    public void init(Context context) {
        this.context = context;
    }

    public static synchronized CrashHandler getInstance() {
        if (crashHandler == null) {
            crashHandler = new CrashHandler();
        }
        return crashHandler;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

    }
}
