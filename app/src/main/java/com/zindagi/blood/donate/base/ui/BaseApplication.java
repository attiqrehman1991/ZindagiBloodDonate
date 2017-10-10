/*
 * MIT License
 * Copyright (c) 2017. Attiq ur Rehman
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished
 * to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software. THE SOFTWARE IS PROVIDED
 * "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM,OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.zindagi.blood.donate.base.ui;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.zindagi.blood.donate.base.dagger.AppComponent;
import com.zindagi.blood.donate.base.dagger.AppModule;
import com.zindagi.blood.donate.base.dagger.DaggerAppComponent;

import io.realm.Realm;

/**
 * Created by Attiq ur Rehman on 08 Oct 2017.
 * Senior Software Engineer at Systems Ltd
 * attiq.ur.rehman1991@gmail.com
 */

public class BaseApplication extends Application {
    private static final long SYNC_FREQUENCY = 60 * 60;  // 1 hour (in seconds)
    public static boolean isConnected;
    private static BaseApplication mInstance;
    private AppComponent appComponent;
    //    public static HashMap<String, BusAdapterModel> busDataNumberPlateMap = new HashMap<>();
//    public static HashMap<String, BusAdapterModel> busDataSLMap = new HashMap<>();
    private BroadcastReceiver networkStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
            isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if (isConnected) {
                Intent intent2 = new Intent();
                intent2.setAction("com.naqabaaveros.SERVICE_FREE");
                context.sendBroadcast(intent2);
            }
        }
    };

    public static synchronized BaseApplication getInstance() {
        return mInstance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    protected AppComponent initDagger(BaseApplication baseApplication) {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(baseApplication))
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = initDagger(this);
        Realm.init(this);
        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        mInstance = this;
    }
}
