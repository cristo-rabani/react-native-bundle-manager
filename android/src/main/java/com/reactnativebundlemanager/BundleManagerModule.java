package com.reactnativebundlemanager;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import com.facebook.react.devsupport.DevInternalSettings;

public class BundleManagerModule extends ReactContextBaseJavaModule {
  private static final String TAG = "BundleManager";
  ReactApplication reactApplication;
  ReactApplicationContext reactContext;
  DevInternalSettings mDevSetting;
  ReactNativeBundleManagerHost nativeHost;

  BundleManagerModule(ReactApplicationContext context) {
    super(context);
    reactContext = context;
    reactApplication = (ReactApplication) context.getApplicationContext();
    nativeHost = (ReactNativeBundleManagerHost) reactApplication.getReactNativeHost();
    mDevSetting = new DevInternalSettings((Context) reactApplication, new DevInternalSettings.Listener() {
      @Override
      public void onInternalSettingsChanged() {
        nativeHost.getReactInstanceManager().recreateReactContextInBackground();
      }
    });
  }


  public static boolean validIP (String ip) {
    try {
      if ( ip == null || ip.isEmpty() ) {
        return false;
      }

      String[] parts = ip.split( "\\." );
      if ( parts.length != 4 ) {
        return false;
      }

      for ( String s : parts ) {
        int i = Integer.parseInt( s );
        if ( (i < 0) || (i > 255) ) {
          return false;
        }
      }
      if ( ip.endsWith(".") ) {
        return false;
      }

      return true;
    } catch (NumberFormatException nfe) {
      return false;
    }
  }

  @ReactMethod
  public void load(String urlAddress, Promise promise) {
      Log.i(TAG, "Load bundle from server: " + urlAddress);

      reactContext.getCurrentActivity().runOnUiThread(new Runnable() {
        @Override
        public void run() {
          ReactInstanceManager instanceManager = nativeHost.getReactInstanceManager();
          promise.resolve(urlAddress);
          instanceManager.getDevSupportManager().reloadJSFromServer(urlAddress);
          Log.i(TAG, "rel: " + instanceManager.getDevSupportManager().getDownloadedJSBundleFile());
        }
      });

  }
  @ReactMethod
  public void setPackagerHost(String hostAddress, Promise promise) {
    try {
      if (!hostAddress.startsWith("http")) {
        hostAddress = "http://" + hostAddress;
      }
      URL url = new URL(hostAddress);
      if(!validIP(url.getHost())) {
        Log.i(TAG, "STOP: No valid url: " + hostAddress);
        return;
      }
      String host = url.getHost() + ':' + url.getPort();
      Log.i(TAG, "Setting host: " + host);
      reactContext.getCurrentActivity().runOnUiThread(new Runnable() {
        @Override
        public void run() {
          mDevSetting.getPackagerConnectionSettings().setDebugServerHost(host);
          promise.resolve(host);
          nativeHost.getReactInstanceManager().recreateReactContextInBackground();
        }
      });
    } catch (MalformedURLException e) {
      e.printStackTrace();
      promise.reject("Bad host address");
    }
  }

  @NonNull
  @Override
  public String getName() {
    return "BundleManagerModule";
  }
}

