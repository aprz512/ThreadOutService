package com.sf.threadoutservice.task;

/**
 * @author by liyunlei
 * <p>
 * write on 2020/5/28
 * <p>
 * Class desc:
 */
public interface Task extends Runnable {

    public long taskTimeInterval();

    public String id();

}
