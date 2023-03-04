package com.reactnativebundlemanager;

import android.app.Application;
import android.util.Log;

import androidx.annotation.Nullable;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactInstanceManagerBuilder;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JSBundleLoader;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.ReactMarkerConstants;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.devsupport.interfaces.DevSupportManager;

import java.io.File;


public abstract class ReactNativeBundleManagerHost extends ReactNativeHost {
    private static final String TAG = "BundleManager";
    private DevSupportManager devManager;
    public ReactNativeBundleManagerHost(Application application) {
        super(application);
    }

   @Override
   protected String getJSMainModuleName() {
       return "index";
   }


    @Override
    protected ReactInstanceManager createReactInstanceManager() {
        ReactInstanceManager rim = super.createReactInstanceManager();
        devManager = rim.getDevSupportManager();

         File file = new File(devManager.getDownloadedJSBundleFile());
         if(file.exists()){
             Log.i(TAG, "remove bundle file: "  + file.getAbsolutePath());
             file.delete();
         } else {
             Log.i(TAG, "No file under: " + file.getAbsolutePath());
         }
        return rim;
    }

    @Nullable
    @Override
    protected String getBundleAssetName() {
        String assetName = super.getBundleAssetName();
        Log.i(TAG, "assetName " + assetName);
        return  assetName;
    }

    @Nullable
    @Override
    protected String getJSBundleFile() {
        return super.getJSBundleFile();
    }


}
