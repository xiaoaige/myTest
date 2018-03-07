package com.wja.myapplication.callback;

import rx.Subscriber;

/**
 * Created by hanke on 2018/2/10.
 */

public class SubscriberCallBack<T> extends Subscriber<T>{
    private ApiCallBack<T> apiCallBack;
    public SubscriberCallBack(ApiCallBack<T> apiCallBack) {
        this.apiCallBack=apiCallBack;
    }

    @Override
    public void onCompleted() {
        apiCallBack.onCompleted();

    }

    @Override
    public void onError(Throwable e) {
        apiCallBack.onError(e);

    }

    @Override
    public void onNext(T t) {
        apiCallBack.onNext(t);

    }
}
