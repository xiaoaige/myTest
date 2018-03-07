package com.wja.myapplication.callback;

/**
 * Created by hanke on 2018/2/10.
 */

public interface ApiCallBack<T> {

   void onCompleted();


    void onError(Throwable e);


    void onNext(T t);
}
