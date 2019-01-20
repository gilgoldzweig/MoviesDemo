package com.example.gilgoldzweig.moviedemo.base;

import android.arch.lifecycle.Lifecycle;
import android.support.annotation.UiThread;

@UiThread
public interface BasePresenterCallBack {
    /**
     * Allows me to verify that my "View" has a lifecycle that the presenter can subscribe
     * @return the "view's" Lifecycle
     */
    Lifecycle getLifecycle();
}
