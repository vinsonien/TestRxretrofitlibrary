package com.wzgiceman.rxretrofitlibrary.web.listener;

import com.tencent.smtt.sdk.WebView;

/**
 * @author: S
 * @date: 2018/11/26 9:48
 * @description: 管理进度
 */
public interface ProgressListener {
    void onProgressChanged(WebView view, int newProgress);//进度变化
}
