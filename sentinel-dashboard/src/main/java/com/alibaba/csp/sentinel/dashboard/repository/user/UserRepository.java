package com.alibaba.csp.sentinel.dashboard.repository.user;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.user.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by @author:zhengxgs on 2021/4/14.
 */
@Transactional
@Repository("userRepository")
public class UserRepository extends UserRepositoryAdapter<User> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(User user) {
        em.persist(user);
    }

    @Override
    public void saveAll(Iterable<User> t) {
        t.forEach(this::save);
    }

    @Override
    public User select(Integer id) {
        StringBuilder hql = new StringBuilder();
        hql.append("select id,userName,pwd,nickName FROM User WHERE id=:id");
        Query query = em.createQuery(hql.toString());
        query.setParameter("id", id);
        Object singleResult = query.getSingleResult();
        if (Objects.nonNull(singleResult)) {
            Object[] entity = (Object[]) singleResult;
            User user = new User();
            user.setId(Integer.parseInt(entity[0].toString()));
            user.setUserName(String.valueOf(entity[1]));
            user.setPwd(String.valueOf(entity[2]));
            user.setNickName(String.valueOf(entity[3]));
            return user;
        }
        return null;
    }

    @Override
    public List<User> selectList(User user) {
        StringBuilder hql = new StringBuilder();
        hql.append("select id,userName,pwd,nickName FROM User");
        Query query = em.createQuery(hql.toString());

        List<User> results = new ArrayList<>();
        List<Object[]> list = query.getResultList();
        if (list != null) {
            for (Object[] entity : list) {
                User u = new User();
                u.setId(Integer.parseInt(entity[0].toString()));
                u.setUserName(String.valueOf(entity[1]));
                u.setPwd(String.valueOf(entity[2]));
                u.setNickName(String.valueOf(entity[3]));
                results.add(u);
            }
        }
        return results;
    }

    @Override
    public void update(User user) {
        em.merge(user);

    }

    @Override
    public int delete(Integer id) {
        StringBuilder hql = new StringBuilder();
        hql.append("DELETE FROM User WHERE id=:id");
        Query query = em.createQuery(hql.toString());
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    @Override
    public User selectByUserName(String userName) {
        StringBuilder hql = new StringBuilder();
        hql.append("select id,userName,pwd,nickName FROM User WHERE userName=:userName");
        Query query = em.createQuery(hql.toString());
        query.setParameter("userName", userName);
        Object singleResult = query.getSingleResult();
        if (Objects.nonNull(singleResult)) {
            Object[] entity = (Object[]) singleResult;
            User user = new User();
            user.setId(Integer.parseInt(entity[0].toString()));
            user.setUserName(String.valueOf(entity[1]));
            user.setPwd(String.valueOf(entity[2]));
            user.setNickName(String.valueOf(entity[3]));
            return user;
        }
        return null;
    }
}