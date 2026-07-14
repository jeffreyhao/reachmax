package com.xcyh.reachmax.app.meta.utils.defenceLib;

import com.baidu.baselibrary.log.ALog;

import com.base.net.webhook.DingDingSender;

public class UncaughtExceptionLogger {

    public static UncaughtExceptionLogger newInstance(){
        return new UncaughtExceptionLogger();
    }

    private Thread.UncaughtExceptionHandler mOriginHandler;

    public UncaughtExceptionLogger() {
    }

    public void init(){
        mOriginHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                ALog.crash("ExceptionHandler", "onCaughtException", e);
                DingDingSender.sendCrash(t, e, "UncaughtExceptionLogger.uncaughtException()");

                mOriginHandler.uncaughtException(t, e);
            }
        });
    }


}
