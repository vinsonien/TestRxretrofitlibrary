package com.wzgiceman.rxretrofitlibrary.web.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wzgiceman.rxretrofitlibrary.R;


/**
 * @author: S
 * @date: 2018/11/20 10:02
 * @description:
 */
public class WebErrorLayout extends LinearLayout {

    Button btn_again;
    TextView error_tips;
    private Context mContext;
    private WebErrorCallBack mWebErrorCallBack;

    public WebErrorLayout(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public WebErrorLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }


    private void init(){
        View v = LayoutInflater.from(mContext).inflate(R.layout.web_error,
                this, true);
//        ButterKnife.bind(this, v);
        error_tips = v.findViewById(R.id.error_tips);
        btn_again = v.findViewById(R.id.btn_again);
        btn_again.setOnClickListener(onClickListener);
    }

    public void setErrorTips(String msg){
        if(msg==null || msg.equals("")){
            error_tips.setText(mContext.getString(R.string.web_load_error));
        }else{
            error_tips.setText(msg);
        }
    }

    public void setError(Throwable e){
        if(e == null){
            setErrorTips(null);
        }else {
            setErrorTips("exception");
        }
    }

    public void setWebErrorCallBack(WebErrorCallBack webErrorCallBack){
        mWebErrorCallBack = webErrorCallBack;
    }

//    @OnClick({R2.id.btn_again})
//    public void viewClick(View v){
//
//        LogUtil.e("== 01 =="+ v.getId());
//        LogUtil.e("== 02 =="+ R2.id.btn_again);
//        switch (v.getId()){
//            case R2.id.btn_again:
//                if(mWebErrorCallBack!=null){
//                    mWebErrorCallBack.clickRefresh();
//                }
//                break;
//        }
//    }

    OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mWebErrorCallBack!=null){
                mWebErrorCallBack.clickRefresh();
            }
        }
    };

    public interface  WebErrorCallBack{
        void clickRefresh();
    }

}
