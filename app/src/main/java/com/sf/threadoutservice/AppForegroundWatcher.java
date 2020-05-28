package com.sf.threadoutservice;

/**
 * @author by liyunlei
 * <p>
 * write on 2020/5/27
 * <p>
 * Class desc:
 */
public interface AppForegroundWatcher {

    void onForeground();

    void onBackground();

}
