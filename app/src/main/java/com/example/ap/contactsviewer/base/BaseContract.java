package com.example.ap.contactsviewer.base;

import android.support.annotation.StringRes;

public interface BaseContract {
    interface View {
        void onError(@StringRes int stringId);
    }

    interface BasePresenter<V extends View> {
        void attach(V v);

        void detach();
    }
}
