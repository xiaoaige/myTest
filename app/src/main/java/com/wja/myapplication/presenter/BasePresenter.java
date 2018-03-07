package com.wja.myapplication.presenter;

import com.wja.myapplication.api.ApiRetrofit;
import com.wja.myapplication.api.ApiService;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by hanke on 2018/2/9.
 */

public abstract class BasePresenter<T> {
    protected ApiService mApiService=ApiRetrofit.newIntance().getApiService();
    T listener;

    public BasePresenter(T listener) {
          attachListener(listener);
    }
    private void attachListener(T listener){
        this.listener=listener;
    }

    public void detachListener(){
        listener=null;
        onUnSubscriber();
    }




    private ApiService apiService=ApiRetrofit.newIntance().getApiService();
    private CompositeSubscription subscription;




    public void addSubscription(Observable observable, Subscriber subscriber){
        if (subscription==null){
            subscription=new CompositeSubscription();
        }
        subscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }

    public void onUnSubscriber(){
        if (subscription!=null && subscription.hasSubscriptions()){
            subscription.unsubscribe();
        }

    }
}
