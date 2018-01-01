package com.udacity.gradle.builditbigger;

import android.support.test.espresso.IdlingResource;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by NIKHIL on 27-12-2017.
 */

public class SimpleIdlingResource implements IdlingResource {

    public static AtomicBoolean isIdleNow = new AtomicBoolean(true);
    public IdlingResource.ResourceCallback resourceCallback;

    @Override
    public String getName() {
        return getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        resourceCallback = callback;
    }

    public void setIdleState(boolean isIdle) {
        isIdleNow.set(isIdle);
        if (isIdle && resourceCallback != null) {
            resourceCallback.onTransitionToIdle();
        }
    }
}
