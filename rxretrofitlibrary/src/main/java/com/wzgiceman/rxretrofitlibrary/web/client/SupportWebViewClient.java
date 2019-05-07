package com.wzgiceman.rxretrofitlibrary.web.client;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @author: S
 * @date: 2018/11/20 14:30
 * @description:
 */
public class SupportWebViewClient extends WebViewClient {

    private Context context;
    private SupportWebClientCallback supportWebClientCallback;

    public SupportWebViewClient(Context context, SupportWebClientCallback supportWebClientCallback) {
        this.context = context;
        this.supportWebClientCallback = supportWebClientCallback;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//        LogUtil.e("shouldOverrideUrlLoading--"+request.getUrl().toString());
        view.loadUrl(request.getUrl().toString());
        return true;

    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//        LogUtil.e("shouldOverrideUrlLoading--"+url);
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        //super.onReceivedSslError(view, handler, error);
        handler.proceed();  // 接受所有网站的证书
    }

    @Override
    public void onReceivedError(WebView webView, int i, String s, String s1) {
        super.onReceivedError(webView, i, s, s1);
//        LogUtil.e("onReceivedError--1--"+i+"--"+s);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return;
        }
        if (supportWebClientCallback!=null){
            supportWebClientCallback.onErrorClient();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
        super.onReceivedError(webView, webResourceRequest, webResourceError);
//        LogUtil.e("onReceivedError--2--"+webResourceError.getErrorCode()+"--"+webResourceError.getDescription());
        if(webResourceRequest.isForMainFrame()){
            if (supportWebClientCallback!=null){
                supportWebClientCallback.onErrorClient();
            }
        }
    }

    public interface SupportWebClientCallback{
        void onErrorClient();
    }

}
