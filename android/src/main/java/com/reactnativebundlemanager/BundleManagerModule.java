package com.reactnativebundlemanager;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import com.facebook.react.devsupport.DevInternalSettings;

public class BundleManagerModule extends ReactContextBaseJavaModule {
  private static final String TAG = "BundleManager";
  ReactInstanceManager instanceManager;
  ReactApplication reactApplication;
  ReactApplicationContext reactContext;
  DevInternalSettings mDevSetting;

  BundleManagerModule(ReactApplicationContext context) {
    super(context);
    reactContext = context;
    reactApplication = (ReactApplication) context.getApplicationContext();
    instanceManager = reactApplication.getReactNativeHost().getReactInstanceManager();
    mDevSetting = new DevInternalSettings((Context) reactApplication, new DevInternalSettings.Listener() {
      @Override
      public void onInternalSettingsChanged() {
        instanceManager.recreateReactContextInBackground();
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
  public void load(String urlAddress) {
      Log.i(TAG, "Load bundle from server: " + urlAddress);

      reactContext.getCurrentActivity().runOnUiThread(new Runnable() {
        @Override
        public void run() {
          instanceManager.getDevSupportManager().reloadJSFromServer(urlAddress);
          Log.i(TAG, "rel: " + instanceManager.getDevSupportManager().getDownloadedJSBundleFile());
          instanceManager.recreateReactContextInBackground();
        }
      });

  }
  @ReactMethod
  public void setPackagerHost(String hostAddress) {
    try {
      URL url = new URL(hostAddress);
      if(!validIP(url.getHost())) {
        return;
      }
      String host = url.getHost() + ':' + url.getPort();
      Log.i(TAG, "Setting host: " + host);
      reactContext.getCurrentActivity().runOnUiThread(new Runnable() {
        @Override
        public void run() {
          mDevSetting.getPackagerConnectionSettings().setDebugServerHost(host);
          instanceManager.recreateReactContextInBackground();
        }
      });
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }

  @NonNull
  @Override
  public String getName() {
    return "BundleManagerModule";
  }
}

