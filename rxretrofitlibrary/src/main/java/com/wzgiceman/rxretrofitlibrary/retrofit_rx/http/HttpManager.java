package com.wzgiceman.rxretrofitlibrary.retrofit_rx.http;

import android.util.Log;

import com.google.gson.Gson;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.android.FragmentEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxFragment;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseApi;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.RxRetrofitApp;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.RetryWhenNetworkException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.func.ExceptionFunc;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.func.ResulteFunc;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextSubListener;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.subscribers.ProgressSubscriber;

import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * http交互处理类
 * Created by WZG on 2016/7/16.basePar
 */
public class HttpManager {
    /*软引用對象*/
    private SoftReference<HttpOnNextListener> onNextListener;
    private SoftReference<HttpOnNextSubListener> onNextSubListener;
    private SoftReference<RxAppCompatActivity> appCompatActivity;
    private SoftReference<RxFragment> appFragment;


    private ExceptionFunc exceptionFunc;
    private ResulteFunc resulteFunc;



    public HttpManager(HttpOnNextListener onNextListener, RxAppCompatActivity appCompatActivity) {
        this.onNextListener = new SoftReference(onNextListener);
        this.appCompatActivity = new SoftReference(appCompatActivity);
    }

    public HttpManager(HttpOnNextSubListener onNextSubListener, RxAppCompatActivity appCompatActivity) {
        this.onNextSubListener = new SoftReference(onNextSubListener);
        this.appCompatActivity = new SoftReference(appCompatActivity);
    }

    public HttpManager(RxAppCompatActivity appCompatActivity) {
        this.appCompatActivity = new SoftReference(appCompatActivity);
    }

    public HttpManager(HttpOnNextListener onNextListener, RxFragment appFragment) {
        this.onNextListener = new SoftReference(onNextListener);
        this.appFragment = new SoftReference(appFragment);
    }

    public HttpManager(HttpOnNextSubListener onNextSubListener, RxFragment appFragment) {
        this.onNextSubListener = new SoftReference(onNextSubListener);
        this.appFragment = new SoftReference(appFragment);
    }

    public HttpManager(RxFragment appFragment) {
        this.appFragment = new SoftReference(appFragment);
    }


    public HttpManager() {

    }


    /**
     * 处理http请求
     *
     * @param basePar 封装的请求数据
     */
    public Observable<String> doHttpDeal(final BaseApi basePar) {
        return httpDeal(getReTrofit(basePar), basePar);
    }


    /**
     * 获取Retrofit对象
     *
     * @param basePar
     * @return
     */
    public Retrofit getReTrofit(final BaseApi basePar) {
        //手动创建一个OkHttpClient并设置超时时间缓存等设置
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(basePar.getConnectionTime(), TimeUnit.SECONDS);
        if (RxRetrofitApp.isDebug()) {
            builder.addInterceptor(getHttpLoggingInterceptor());
        }

        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(new Gson());
        ScalarsConverterFactory scalarsConverterFactory = ScalarsConverterFactory.create();

        /*创建retrofit对象*/
        final Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(scalarsConverterFactory)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(basePar.getBaseUrl())
                .build();
        return retrofit;
    }

    /**
     * RxRetrofit处理
     *
     * @param basePar
     */
    public Observable httpDeal(Retrofit retrofit, BaseApi basePar) {
        /*失败后的retry配置*/
        Observable observable = basePar.getObservable(retrofit)
                /*失败后retry处理控制*/
                .retryWhen(new RetryWhenNetworkException(basePar.getRetryCount(),
                        basePar.getRetryDelay(), basePar.getRetryIncreaseDelay()))
                /*异常处理*/
                .onErrorResumeNext(getExceptionFunc()==null?new ExceptionFunc(): getExceptionFunc())
                /*tokean过期统一处理*/
//                .flatMap(new TokeanFunc(basePar, retrofit))
                /*返回数据统一判断*/
                .map(getResulteFunc()==null?new ResulteFunc():getResulteFunc())
                /*http请求线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread());

        /*生命周期管理*/
        if (appCompatActivity != null) {
            observable.compose(appCompatActivity.get().bindUntilEvent(ActivityEvent.DESTROY));
        }else if(appFragment != null){
            observable.compose(appFragment.get().bindUntilEvent(FragmentEvent.DESTROY));
        }

        /*是否不需要处理sub对象*/
        if (basePar.isNoSub()) {
            return observable;
        }

        /*ober回调，链接式返回*/
        if (onNextSubListener != null && null != onNextSubListener.get()) {
            onNextSubListener.get().onNext(observable, basePar.getMethod());
        }

        /*数据String回调*/
        if (onNextListener != null && null != onNextListener.get()) {
            ProgressSubscriber subscriber;
            if (appCompatActivity != null) {
                subscriber = new ProgressSubscriber(basePar, onNextListener, appCompatActivity);
                observable.subscribe(subscriber);
            }else if(appFragment != null){
                subscriber = new ProgressSubscriber(basePar, onNextListener, appFragment);
                observable.subscribe(subscriber);
            }
        }

        return observable;
    }

    public ExceptionFunc getExceptionFunc() {
        return exceptionFunc;
    }

    public void setExceptionFunc(ExceptionFunc exceptionFunc) {
        this.exceptionFunc = exceptionFunc;
    }

    public ResulteFunc getResulteFunc() {
        return resulteFunc;
    }

    public void setResulteFunc(ResulteFunc resulteFunc) {
        this.resulteFunc = resulteFunc;
    }

    /**
     * 日志输出
     * 自行判定是否添加
     *
     * @return
     */
    private HttpLoggingInterceptor getHttpLoggingInterceptor() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("RxRetrofit", "Retrofit====Message:" + message);
            }
        });
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }

}
