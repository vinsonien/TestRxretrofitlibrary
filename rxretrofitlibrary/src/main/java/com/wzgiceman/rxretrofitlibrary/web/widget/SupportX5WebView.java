package com.wzgiceman.rxretrofitlibrary.web.widget;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;

import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebView;
import com.wzgiceman.rxretrofitlibrary.R;
import com.wzgiceman.rxretrofitlibrary.web.client.SupportWebChromeClient;
import com.wzgiceman.rxretrofitlibrary.web.client.SupportWebViewClient;
import com.wzgiceman.rxretrofitlibrary.web.listener.FileListener;
import com.wzgiceman.rxretrofitlibrary.web.listener.JsListener;
import com.wzgiceman.rxretrofitlibrary.web.listener.ProgressListener;
import com.wzgiceman.rxretrofitlibrary.web.utils.FileManager;

import org.greenrobot.greendao.annotation.NotNull;

/**
 * @author: S
 * @date: 2018/11/23 10:25
 * @description:
 */
public class SupportX5WebView extends X5WebView {

    SupportWebChromeClient webChromeClient;
    SupportWebViewClient webViewClient;

    SupportProgressListener supportProgressListener;//进度监听
    SupportNetStatusListener supportNetStatusListener;//网络错误监听
    SupportJavascriptListener supportJavascriptListener;//JS交互监听
    SupportSuperJavascriptListener supportSuperJavascriptListener;//JS交互高效率监听

    private Activity hostActivity;
    private Uri fileUri;
    public static final int TYPE_REQUEST_PERMISSION = 3;
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_GALLERY = 2;


    public SupportX5WebView(@NotNull Context arg0) {
        super(arg0);
        hostActivity = (Activity) arg0;
        initCallBack();
    }

    @SuppressLint("SetJavaScriptEnabled")
    public SupportX5WebView(@NotNull Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
        hostActivity = (Activity) arg0;
        initCallBack();
    }

    private void initCallBack() {
        webChromeClient = new SupportWebChromeClient(hostActivity);
        webChromeClient.SetFileListener(fileListener);
        webChromeClient.SetProgressListener(progressListener);
        webChromeClient.SetJsListener(jsListener);
        webViewClient = new SupportWebViewClient(hostActivity, new SupportWebViewClient.SupportWebClientCallback() {
            @Override
            public void onErrorClient() {
                if (supportNetStatusListener != null){
                    supportNetStatusListener.onErrorClient();
                }
            }
        });

        setWebChromeClient(webChromeClient);
        setWebViewClient(webViewClient);
    }

    /**
     * 监听选择文件
     */
    FileListener fileListener = new FileListener() {
        @Override
        public void onChooseFile() {
            openFileChoose();
        }
    };

    /**
     * 监听渲染进度
     *
     * @param view
     * @param newProgress
     */
    ProgressListener progressListener = new ProgressListener() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (supportProgressListener !=null){
                supportProgressListener.onProgressChanged(view,newProgress);
            }
        }
    };

    JsListener jsListener = new JsListener() {
        @Override
        public boolean onJsAlert(WebView webView, String s, String s1, JsResult jsResult) {
            if (supportJavascriptListener!=null){
                return supportJavascriptListener.onJsAlert(webView, s, s1, jsResult);
            }
            return false;
        }

        @Override
        public boolean onJsConfirm(WebView webView, String s, String s1, JsResult jsResult) {
            if (supportJavascriptListener!=null){
                return supportJavascriptListener.onJsConfirm(webView, s, s1, jsResult);
            }
            return false;
        }

        @Override
        public boolean onJsPrompt(WebView webView, String s, String s1, String s2, JsPromptResult jsPromptResult) {
            if (supportJavascriptListener!=null){
                return supportJavascriptListener.onJsPrompt(webView, s, s1, s2, jsPromptResult);
            }
            return false;
        }

        @Override
        public boolean onJsBeforeUnload(WebView webView, String s, String s1, JsResult jsResult) {
            if (supportJavascriptListener!=null){
                return supportJavascriptListener.onJsBeforeUnload(webView, s, s1, jsResult);
            }
            return false;
        }

        @Override
        public boolean onJsTimeout() {
            if (supportJavascriptListener!=null){
                return supportJavascriptListener.onJsTimeout();
            }
            return false;
        }
    };


    private void openFileChoose() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(hostActivity);
        alertDialog.setOnCancelListener(new ReOnCancelListener());
        alertDialog.setTitle("选择");
        alertDialog.setItems(R.array.options,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            //相机
                            if (ContextCompat.checkSelfPermission(hostActivity,
                                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                // 申请WRITE_EXTERNAL_STORAGE权限
                                ActivityCompat
                                        .requestPermissions(
                                                hostActivity,
                                                new String[]{Manifest.permission.CAMERA},
                                                TYPE_REQUEST_PERMISSION);
                            } else {
                                toCamera();
                            }
                        } else {
                            //相册
                            Intent i = new Intent(
                                    Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// 调用android的图库
                            hostActivity.startActivityForResult(i,
                                    TYPE_GALLERY);
                        }
                    }
                });
        alertDialog.show();
    }

    // 请求拍照
    public void toCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 调用android的相机
        // 创建一个文件保存图片
        fileUri = Uri.fromFile(FileManager.getImgFile(hostActivity.getApplicationContext()));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        hostActivity.startActivityForResult(intent, TYPE_CAMERA);
    }

    private class ReOnCancelListener implements
            DialogInterface.OnCancelListener {

        @Override
        public void onCancel(DialogInterface dialogInterface) {
            if (webChromeClient != null) {
                webChromeClient.resetValueCallback();
            }
        }
    }

    /**
     * 回调到网页
     *
     * @param uri
     */
    public boolean onActivityCallBack(int requestCode, Uri uri) {
        if (webChromeClient!=null){
            if (requestCode== SupportX5WebView.TYPE_CAMERA){
                uri = fileUri;
            }
            return webChromeClient.returnValueCallback(uri);
        }
        return false;
    }


    /**
     * 访问 url
     * @param url
     */
    public void LoadUrl(String  url){
        if (url==null || url.equals("")){
            Log.e(""," SupportX5WebView url is null ");
            return;
        }
        loadUrl(url);
    }

    public void evaluate(String method){
        if (method==null || method.equals("")){
            Log.e(""," SupportX5WebView method is null ");
            return;
        }
        evaluateJavascript(method, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                Log.e(""," ************** ");
                if (supportSuperJavascriptListener != null){
                    supportSuperJavascriptListener.onReceiveValue(s);
                }
            }
        });
    }

    public interface SupportNetStatusListener {
        void onErrorClient();
    }

    public interface SupportProgressListener {
        void onProgressChanged(WebView view, int newProgress);
    }

    public interface SupportJavascriptListener{

        //android 4.4（含）前返回
        boolean onJsAlert(WebView webView, String url, String message, JsResult jsResult);
        boolean onJsConfirm(WebView webView, String url, String message, JsResult jsResult);
        boolean onJsPrompt(WebView webView, String url, String message, String defaultValue, JsPromptResult jsPromptResult);
        boolean onJsBeforeUnload(WebView webView, String url, String message, JsResult jsResult);
        boolean onJsTimeout();

    }

    public interface SupportSuperJavascriptListener{
        //android 4.4后返回
        void onReceiveValue(String s);
    }

    public void setSupportNetStatusListener(SupportNetStatusListener supportNetStatusListener){
        this.supportNetStatusListener = supportNetStatusListener;
    }
    public void setSupportProgressListener(SupportProgressListener supportProgressListener){
        this.supportProgressListener = supportProgressListener;
    }

    public void setSupportJavascriptListener(SupportJavascriptListener supportJavascriptListener){
        this.supportJavascriptListener = supportJavascriptListener;
    }

    public void setSupportSuperJavascriptListener(SupportSuperJavascriptListener supportSuperJavascriptListener){
        this.supportSuperJavascriptListener = supportSuperJavascriptListener;
    }
}
