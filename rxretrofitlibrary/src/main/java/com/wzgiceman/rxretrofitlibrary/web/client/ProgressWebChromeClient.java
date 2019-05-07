package com.wzgiceman.rxretrofitlibrary.web.client;

import android.content.Context;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.wzgiceman.rxretrofitlibrary.web.listener.ProgressListener;


/**
 * @author: S
 * @date: 2018/11/26 9:45
 * @description:
 */
public class ProgressWebChromeClient extends WebChromeClient {

    private Context context;
    private ProgressListener progressListener;

    public ProgressWebChromeClient(Context context) {
        this.context = context;
    }

    public void SetProgressListener(ProgressListener progressListener){
        this.progressListener = progressListener;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if(progressListener !=null){
            progressListener.onProgressChanged(view, newProgress);
        }
    }
}
