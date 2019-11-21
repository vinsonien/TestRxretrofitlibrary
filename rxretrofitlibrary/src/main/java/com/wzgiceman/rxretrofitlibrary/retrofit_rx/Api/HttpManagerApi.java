package com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxFragment;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.HttpManager;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextSubListener;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * 请求数据统一封装类
 * Created by WZG on 2016/7/16.
 */
public class HttpManagerApi extends BaseApi {
    private HttpManager manager;

    public HttpManagerApi(HttpOnNextListener onNextListener, RxAppCompatActivity appCompatActivity) {
        manager = new HttpManager(onNextListener, appCompatActivity);
    }

    public HttpManagerApi(HttpOnNextSubListener onNextSubListener, RxAppCompatActivity appCompatActivity) {
        manager = new HttpManager(onNextSubListener, appCompatActivity);
    }

    public HttpManagerApi(HttpOnNextListener onNextListener, RxFragment rxFragment) {
        manager = new HttpManager(onNextListener, rxFragment);
    }

    public HttpManagerApi(HttpOnNextSubListener onNextSubListener, RxFragment rxFragment) {
        manager = new HttpManager(onNextSubListener, rxFragment);
    }


    protected Retrofit getRetrofit() {
        return manager.getReTrofit(this);
    }


    protected void doHttpDeal(Retrofit retrofit) {
        manager.httpDeal(retrofit, this);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        return null;
    }
}
