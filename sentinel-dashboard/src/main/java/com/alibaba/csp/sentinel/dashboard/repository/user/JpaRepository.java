package com.alibaba.csp.sentinel.dashboard.repository.user;

import java.util.List;

/**
 * JPA repository
 * Created by @author:zhengxgs on 2021/4/14.
 */
public interface JpaRepository<T,ID> {

    void save(T t);

    void saveAll(Iterable<T> t);

    T select(ID id);

    List<T> selectList(T query);

    void update(T t);

}
