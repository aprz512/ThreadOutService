package com.sf.threadoutservice;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by liyunlei
 * <p>
 * write on 2020/5/27
 * <p>
 * Class desc:
 */
public class ActivityLifecycleCallbacksImpl implements Application.ActivityLifecycleCallbacks {

    private static int visibleActivityCount;
    private List<AppForegroundWatcher> watcherList = new ArrayList<>();

    public void registerWatcher(AppForegroundWatcher watcher) {
        if (watcherList.contains(watcher)) {
            return;
        }
        watcherList.add(watcher);
    }

    public void unRegisterWatcher(AppForegroundWatcher watcher) {
        watcherList.remove(watcher);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        ++visibleActivityCount;
        if (visibleActivityCount == 1) {
            for (AppForegroundWatcher watcher : watcherList) {
                watcher.onForeground();
            }
        }
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        --visibleActivityCount;
        if (visibleActivityCount == 0) {
            for (AppForegroundWatcher watcher : watcherList) {
                watcher.onBackground();
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }

    public static boolean isAppForeground() {
        return visibleActivityCount > 0;
    }

}
