package com.test.sdk.testrxretrofitlibrary;

import android.app.Application;


/**
 * @author: S
 * @date: 2019/5/7 14:19
 * @description:
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        InitRxRetrofit(true);
    }


    /**
     * RxRetrofit 修改域名
     */
    private void InitRxRetrofit(boolean isDebug){
    }
}
