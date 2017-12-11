package com.example.tatsuki.itacrch2017;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import android.util.Log;

import java.util.Locale;

/**
 * Created by tatsuki on 2017/11/29.
 */

public class RemoteService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    private CalculatorService.Stub mStub =
            new CalculatorService.Stub() { //【1】
                @Override
                public int calservice(String s) throws RemoteException { //【2】
                    return s.length();
                }
            };

    @Override
    public IBinder onBind(Intent intent) {
        // Return the interface
        return mStub;
    }

}

