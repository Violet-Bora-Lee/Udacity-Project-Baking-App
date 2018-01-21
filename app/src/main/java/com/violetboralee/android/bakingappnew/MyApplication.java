package com.violetboralee.android.bakingappnew;

import android.app.Application;

import com.violetboralee.android.bakingappnew.util.ConnectivityReceiver;

/**
 * Created by bora on 15/01/2018.
 */

public class MyApplication extends Application {

    private static MyApplication mInstance;

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }


}
