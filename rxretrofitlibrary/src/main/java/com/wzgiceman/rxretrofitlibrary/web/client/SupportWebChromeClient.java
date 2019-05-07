package com.wzgiceman.rxretrofitlibrary.web.client;

import android.content.Context;

/**
 * @author: S
 * @date: 2018/11/20 10:59
 * @description:
 */
public class SupportWebChromeClient extends FileWebChromeClient {

    private Context context;
//    private SupportWebChromeCallback supportWebChromeCallback;
//    public ValueCallback<Uri> uploadFile;
//    public ValueCallback<Uri[]> uploadFileArray;

    public SupportWebChromeClient(Context context) {
        super(context);
    }

//    public SupportWebChromeClient(Context context, SupportWebChromeCallback supportWebChromeCallback){
//        this.context = context;
//        this.setSupportWebChromeClient(supportWebChromeCallback);
//    }
//    public void setSupportWebChromeClient(SupportWebChromeCallback supportWebChromeCallback){
//        this.supportWebChromeCallback = supportWebChromeCallback;
//    }
//
//    public interface SupportWebChromeCallback{
//        void onProgressChanged(WebView view, int newProgress);//进度变化
//        void onChooseFile();//选择文件
//    }
//
//    @Override
//    public void onProgressChanged(WebView view, int newProgress) {
//        super.onProgressChanged(view, newProgress);
//        if(supportWebChromeCallback!=null){
//            supportWebChromeCallback.onProgressChanged(view, newProgress);
//        }
//    }
//
//
//    // android 5.0
//    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
//                                     FileChooserParams fileChooserParams) {
////        LogUtil.e( "onShowFileChooser---5.0");
//        uploadFileArray = filePathCallback;
//        if(supportWebChromeCallback!=null){
//            supportWebChromeCallback.onChooseFile();
//        }
//        return true;
//    }
//
//    //For Android 4.1
//    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
////        LogUtil.e("openFileChooser---4.1");
////        LogUtil.e("openFileChooser---4.1--acceptType"+acceptType);
////        LogUtil.e("openFileChooser---4.1--capture"+capture);
//        uploadFile = uploadMsg;
//        if(supportWebChromeCallback!=null){
//            supportWebChromeCallback.onChooseFile();
//        }
//    }
//
//    // For Android < 3.0
//    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
////        LogUtil.e("openFileChooser---3.0");
//        uploadFile = uploadMsg;
//        if(supportWebChromeCallback!=null){
//            supportWebChromeCallback.onChooseFile();
//        }
//    }
//
//    // For Android 3.0+
//    public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
////        LogUtil.e("openFileChooser---3.0+");
//        uploadFile = uploadMsg;
//        if(supportWebChromeCallback!=null){
//            supportWebChromeCallback.onChooseFile();
//        }
//    }
//
//
//
//    /**
//     * 置空ValueCallback
//     */
//    public void resetValueCallback(){
//        if (uploadFile != null) {
//            uploadFile.onReceiveValue(null);
//            uploadFile = null;
//        }
//        if (uploadFileArray != null) {
//            uploadFileArray.onReceiveValue(null);
//            uploadFileArray = null;
//        }
//    }
//
//    /**
//     * 回调ValueCallback
//     * @param uri
//     * @return
//     */
//    public boolean returnValueCallback(Uri uri){
//
//        if (uploadFileArray != null) {
//            Uri[] uris = new Uri[]{uri};
//            uploadFileArray.onReceiveValue(uris);
//            uploadFileArray = null;
//        } else if (uploadFile != null) {
//            uploadFile.onReceiveValue(uri);
//            uploadFile = null;
//        } else {
////            Toast.makeText(context, "无法获取数据", Toast.LENGTH_LONG).show();
//            return false;
//        }
//        return true;
//    }
}
