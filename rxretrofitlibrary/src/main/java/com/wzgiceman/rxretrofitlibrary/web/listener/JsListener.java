package com.wzgiceman.rxretrofitlibrary.web.listener;

import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebView;

/**
 * @author: S
 * @date: 2018/11/26 9:50
 * @description:
 */
public interface JsListener {

    boolean onJsAlert(WebView webView, String url, String message, JsResult jsResult);
    boolean onJsConfirm(WebView webView, String url, String message, JsResult jsResult);
    boolean onJsPrompt(WebView webView, String url, String message, String defaultValue, JsPromptResult jsPromptResult);
    boolean onJsBeforeUnload(WebView webView, String url, String message, JsResult jsResult);
    boolean onJsTimeout();
}
