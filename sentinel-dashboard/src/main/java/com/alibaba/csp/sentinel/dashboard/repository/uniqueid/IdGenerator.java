package com.alibaba.csp.sentinel.dashboard.repository.uniqueid;

/**
 * Created by @author:zhengxgs on 2021/1/23.
 */
public interface IdGenerator<T> {

    /**
     * get next global unique id
     *
     * @return unique id
     */
    T nextId();

}