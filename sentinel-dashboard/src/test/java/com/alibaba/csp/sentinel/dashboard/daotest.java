package com.alibaba.csp.sentinel.dashboard;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.user.User;
import com.gitee.baa.IPage;
import com.gitee.baa.IService;
import com.gitee.baa.dao.DaoFactory;
import com.gitee.baa.dao.IDao;
import com.gitee.baa.dao.IRowMapper;
import java.util.List;
import java.util.Map;
import org.junit.Test;

/**
 * Created by @author:zhengxgs on 2021/4/17.
 */
public class daotest {

    Service service = new ServiceImpl();

    @Test
    public void test1() {
        List<User> list = service.list(new User());

        IPage<User> page = service.list(new User(), "id", false, 1, 1);
        System.out.println(page.getList().get(0));
        System.out.println(list);
    }


    IRowMapper<User> mapper = (rs, row) -> {
        User user = new User();
        user.setUserName(rs.getString("user_name"));
        return user;
    };

    @Test
    public void test2() {
        IDao dao = DaoFactory.getIDao(true);
        int cdp = dao.queryInt("select count(1) from sentinel_user", "cdp", 0);
        Map<String, Object> map = dao
            .queryMap("select user_name from sentinel_user where id=?", "test", (msp, rs) -> msp.put("jiji", rs.getString("user_name")), 1);
        dao.queryList("select * from user", "test", mapper);
        System.out.println("啊啊啊啊啊啊我有多少啊---->" + cdp);
    }
}

interface Service extends IService<User> {

}

class ServiceImpl implements Service {

    @Override
    public Class<User> getEntity() {
        return User.class;
    }

    @Override
    public String getBizName() {
        return "test";
    }

    @Override
    public boolean zk() {
        return false;
    }
}