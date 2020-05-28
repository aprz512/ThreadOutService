package com.sf.threadoutservice.task;

import android.util.Log;

import com.orhanobut.logger.Logger;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author by liyunlei
 * <p>
 * write on 2020/5/28
 * <p>
 * Class desc:
 */
public class SomethingTask implements Task {
    private static final String TAG = "SomethingTask";
    @Override
    public long taskTimeInterval() {
        return 60 * 1000L;
    }

    @Override
    public String id() {
        return "SomethingTask";
    }

    @Override
    public void run() {
        DateFormat dateTimeInstance = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.CHINA);
        Logger.d("SomethingTask is running at %s", dateTimeInstance.format(new Date()));
        Log.e(TAG, "SomethingTask");
    }
}
