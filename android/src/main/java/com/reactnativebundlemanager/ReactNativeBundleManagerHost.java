package com.reactnativebundlemanager;

import android.app.Application;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;

import java.io.File;


public abstract class ReactNativeBundleManagerHost extends ReactNativeHost {
    private static final String TAG = "BundleManager";
    protected ReactNativeBundleManagerHost(Application application) {
        super(application);
    }

    protected ReactInstanceManager createReactInstanceManager() {
        ReactInstanceManager rim = super.createReactInstanceManager();
        File file = new File(rim.getDevSupportManager().getDownloadedJSBundleFile());
        if(file.exists()){
            file.delete();
        }
        return rim;
    }

}