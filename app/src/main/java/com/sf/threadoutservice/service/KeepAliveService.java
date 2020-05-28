package com.sf.threadoutservice.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.sf.threadoutservice.R;
import com.sf.threadoutservice.task.Task;

import java.util.List;
import java.util.concurrent.Executors;

/**
 * @author by liyunlei
 * <p>
 * write on 2020/5/27
 * <p>
 * Class desc:
 */
public class KeepAliveService extends Service {

    private static final String TAG = "KeepAliveService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(Integer.MAX_VALUE >> 2, buildNotification());
//        Executors.newSingleThreadExecutor().submit(new Runnable() {
//            @Override
//            public void run() {
//                while(true) {
//
//                    SystemClock.sleep(2000);
//
//                    ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//
//                    List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
//                    if (runningAppProcesses == null) {
//                        return;
//                    }
//
//                    for (ActivityManager.RunningAppProcessInfo runningAppProcess : runningAppProcesses) {
//                        if (runningAppProcess.pid == Process.myPid()) {
//                            Log.e(TAG, "Thread in onStartCommand pname is " + runningAppProcess.processName);
//                        }
//                    }
//
//                }
//            }
//        });
        Log.e(TAG, "onStartCommand");
        return START_REDELIVER_INTENT;
    }

    private Notification buildNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, "channel_id_for_foreground_service")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("ThreadOutService")
                        .setContentText("running...");

        return builder.build();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
