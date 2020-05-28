package com.sf.threadoutservice;

import android.app.Application;
import android.content.Intent;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.Logger;
import com.sf.threadoutservice.service.KeepAliveService;
import com.sf.threadoutservice.task.SilentMusicPlayer;

/**
 * @author by liyunlei
 * <p>
 * write on 2020/5/27
 * <p>
 * Class desc:
 */
public class App extends Application {

    private static App app;

    public static App getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        initLog();

        ActivityLifecycleCallbacksImpl callbacks = new ActivityLifecycleCallbacksImpl();
        registerActivityLifecycleCallbacks(callbacks);
        SilentMusicPlayer thread = new SilentMusicPlayer();
        callbacks.registerWatcher(thread);


    }

    private void initLog() {
        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.addLogAdapter(new DiskLogAdapter());
    }
}
