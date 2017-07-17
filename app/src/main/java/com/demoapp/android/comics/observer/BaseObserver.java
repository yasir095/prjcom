package com.demoapp.android.comics.observer;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by yasirmahmood on 17/07/2017.
 */

public abstract class BaseObserver<T,E> implements Observer<E>
{
    protected Disposable disposable;
    protected T data;
    private ResponseDelegate delegate;

    public BaseObserver(ResponseDelegate delegate)
    {
        this.delegate = delegate;
    }

    public Disposable getDisposable()
    {
        return this.disposable;
    }

    protected void setDisposable(Disposable disposable)
    {
        this.disposable = disposable;
    }

    @Override
    public void onSubscribe(Disposable d)
    {
        setDisposable(d);
    }

    @Override
    public void onNext(E value)
    {
        setData(value);
    }

    public T getData()
    {
        return this.data;
    }

    @Override
    public void onComplete()
    {
        this.delegate.onSuccess(getData());
    }

    @Override
    public void onError(Throwable e)
    {
        this.delegate.onError(e);
    }

    protected abstract void setData(E data);
}
