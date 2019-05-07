package com.wzgiceman.rxretrofitlibrary.web.client;

import android.content.Context;
import android.net.Uri;

import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.wzgiceman.rxretrofitlibrary.web.listener.FileListener;


/**
 * @author: S
 * @date: 2018/11/26 9:46
 * @description: 管理文件交互
 */
public class FileWebChromeClient extends JSWebChromeClient {

    private Context context;
    private FileListener fileListener;
    public ValueCallback<Uri> uploadFile;
    public ValueCallback<Uri[]> uploadFileArray;

    public FileWebChromeClient(Context context) {
        super(context);
    }

    public void SetFileListener(FileListener fileListener){
        this.fileListener = fileListener;
    }



    // android 5.0
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                     WebChromeClient.FileChooserParams fileChooserParams) {
//        LogUtil.e( "onShowFileChooser---5.0");
        uploadFileArray = filePathCallback;
        if(fileListener !=null){
            fileListener.onChooseFile();
        }
        return true;
    }

    //For Android 4.1
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
//        LogUtil.e("openFileChooser---4.1");
//        LogUtil.e("openFileChooser---4.1--acceptType"+acceptType);
//        LogUtil.e("openFileChooser---4.1--capture"+capture);
        uploadFile = uploadMsg;
        if(fileListener !=null){
            fileListener.onChooseFile();
        }
    }

    // For Android < 3.0
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
//        LogUtil.e("openFileChooser---3.0");
        uploadFile = uploadMsg;
        if(fileListener !=null){
            fileListener.onChooseFile();
        }
    }

    // For Android 3.0+
    public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
//        LogUtil.e("openFileChooser---3.0+");
        uploadFile = uploadMsg;
        if(fileListener !=null){
            fileListener.onChooseFile();
        }
    }



    /**
     * 置空ValueCallback
     */
    public void resetValueCallback(){
        if (uploadFile != null) {
            uploadFile.onReceiveValue(null);
            uploadFile = null;
        }
        if (uploadFileArray != null) {
            uploadFileArray.onReceiveValue(null);
            uploadFileArray = null;
        }
    }

    /**
     * 回调ValueCallback
     * @param uri
     * @return
     */
    public boolean returnValueCallback(Uri uri){

        if (uploadFileArray != null) {
            Uri[] uris = new Uri[]{uri};
            uploadFileArray.onReceiveValue(uris);
            uploadFileArray = null;
        } else if (uploadFile != null) {
            uploadFile.onReceiveValue(uri);
            uploadFile = null;
        } else {
//            Toast.makeText(context, "无法获取数据", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
