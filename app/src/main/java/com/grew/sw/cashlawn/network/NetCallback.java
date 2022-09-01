package com.grew.sw.cashlawn.network;

import com.grew.sw.cashlawn.App;
import com.grew.sw.cashlawn.R;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class NetCallback < T extends Object> implements Observer<T> {
    public abstract void businessFail(NetErrorModel netErrorModel);
    public abstract void businessSuccess(T data);

    @Override
    public void onNext(T t) {
        if (t == null){
            businessFail(new NetErrorModel(-1, App.get().getString(R.string.network_error)));
        }else {
            businessSuccess(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        businessFail(new NetErrorModel(-1, e.toString()));
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable d) {

    }
}
