package com.wzgiceman.rxretrofitlibrary.web.client;

import android.content.Context;

import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebView;
import com.wzgiceman.rxretrofitlibrary.web.listener.JsListener;


/**
 * @author: S
 * @date: 2018/11/26 9:43
 * @description: 管理JS交互
 */
public class JSWebChromeClient extends ProgressWebChromeClient {

    private Context context;
    private JsListener jsListener;

    public JSWebChromeClient(Context context) {
        super(context);
    }

    public void SetJsListener(JsListener jsListener){
        this.jsListener = jsListener;
    }


    @Override
    public boolean onJsAlert(WebView webView, String url, String message, JsResult jsResult) {
        if (jsListener!=null){
            return jsListener.onJsAlert(webView, url, message, jsResult);
        }
        return super.onJsAlert(webView, url, message, jsResult);
    }

    @Override
    public boolean onJsConfirm(WebView webView, String url, String message, JsResult jsResult) {
        if (jsListener!=null){
            return jsListener.onJsConfirm(webView, url, message, jsResult);
        }
        return super.onJsConfirm(webView, url, message, jsResult);
    }

    @Override
    public boolean onJsPrompt(WebView webView, String url, String message, String defaultValue, JsPromptResult jsPromptResult) {
        if (jsListener!=null){
            return jsListener.onJsPrompt(webView, url, message, defaultValue, jsPromptResult);
        }
        return super.onJsPrompt(webView, url, message, defaultValue, jsPromptResult);
    }

    @Override
    public boolean onJsBeforeUnload(WebView webView, String url, String message, JsResult jsResult) {
        if (jsListener!=null){
            return jsListener.onJsBeforeUnload(webView, url, message, jsResult);
        }
        return super.onJsBeforeUnload(webView, url, message, jsResult);
    }

    @Override
    public boolean onJsTimeout() {
        if (jsListener!=null){
            return jsListener.onJsTimeout();
        }
        return super.onJsTimeout();
    }
}
