package com.grew.sw.cashlawn.network;

import androidx.annotation.NonNull;

import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NetUtil{
    private static Scheduler scheduler;

    @NonNull
    public static  <T> ObservableTransformer<T, T> applySchedulers() {
        if(scheduler == null){
            scheduler =  Schedulers.from(Executors.newFixedThreadPool(10));
        }
        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream.subscribeOn(scheduler)
                        .unsubscribeOn(scheduler)
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
