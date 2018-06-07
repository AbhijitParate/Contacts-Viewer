package com.example.ap.contactsviewer.base;

import com.example.ap.contactsviewer.data.schedular.SchedulerProvider;
import com.example.ap.contactsviewer.data.source.ContactService;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;

public class BasePresenter<V extends BaseContract.View> {

    private V view;
    private ContactService contactService;
    private SchedulerProvider schedulerProvider;
    private CompositeDisposable disposable;

    public BasePresenter(V view, ContactService contactService, SchedulerProvider schedulerProvider) {
        this.view = view;
        this.contactService = contactService;
        this.schedulerProvider = schedulerProvider;
        this.disposable = new CompositeDisposable();
    }

    protected void subscribe() {

    }

    protected void unsubscribe() {
        disposable.clear();
    }

    protected <T> void addToDisposable(Maybe<T> maybe, DisposableMaybeObserver<T> disposableMaybeObserver) {
        disposable.add(maybe
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(disposableMaybeObserver));
    }

    protected <T> void addToDisposable(Completable completable, DisposableCompletableObserver disposableMaybeObserver) {
        disposable.add(completable
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(disposableMaybeObserver));
    }

    protected ContactService getContactService() {
        return contactService;
    }

    public V getView() {
        return view;
    }
}