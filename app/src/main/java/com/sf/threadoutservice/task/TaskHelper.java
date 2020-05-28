package com.sf.threadoutservice.task;

import android.util.ArrayMap;
import android.util.Log;

import com.sf.threadoutservice.executor.AppExecutor;

/**
 * @author by liyunlei
 * <p>
 * write on 2020/5/28
 * <p>
 * Class desc:
 */
public class TaskHelper {
    private static final String TAG = "TaskHelper";

    private static ArrayMap<String, Long> record = new ArrayMap<>();

    public static void submitTask(Task task) {
        synchronized (TaskHelper.class) {
            Long lastSubmitTime = record.get(task.id());
            if (lastSubmitTime == null
                    || System.currentTimeMillis() - lastSubmitTime >= task.taskTimeInterval()) {
                AppExecutor.getInstance().submit(task);
                record.put(task.id(), System.currentTimeMillis());
            } else {
//                Logger.w("提交任务%s间隔大于设定间隔，忽略该次提交", task.id());
                Log.e(TAG, "提交任务" + task.id() + "间隔小于设定间隔，忽略该次提交");
            }
        }
    }

}
