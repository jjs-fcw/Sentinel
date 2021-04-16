package com.alibaba.csp.sentinel.dashboard.repository.user;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.user.UserPermissions;
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
@Repository("userPermissionsRepository")
public class UserPermissionsRepository extends UserPermissionsRepositoryAdapter<UserPermissions> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(UserPermissions userPermissions) {
        em.persist(userPermissions);
    }

    @Override
    public void saveAll(Iterable<UserPermissions> t) {
        t.forEach(this::save);
    }

    @Override
    public UserPermissions select(Integer id) {
        StringBuilder hql = new StringBuilder();
        hql.append("select id,userName,workerId,app FROM UserPermissions WHERE id=:id");
        Query query = em.createQuery(hql.toString());
        query.setParameter("id", id);
        Object singleResult = query.getSingleResult();
        if (Objects.nonNull(singleResult)) {
            Object[] entity = (Object[]) singleResult;
            UserPermissions permissions = new UserPermissions();
            permissions.setId(Integer.parseInt(entity[0].toString()));
            permissions.setUserName(String.valueOf(entity[1]));
            permissions.setWorkerId(String.valueOf(entity[2]));
            permissions.setApp(String.valueOf(entity[3]));
            return permissions;
        }
        return null;
    }

    @Override
    public List<UserPermissions> selectList(UserPermissions permissions) {
        StringBuilder hql = new StringBuilder();
        hql.append(" select id,userName,workerId,app FROM UserPermissions ");
        hql.append(" WHERE 1=1");
        if (permissions.getUserName() != null) {
            hql.append(" and userName=:userName");
        }
        Query query = em.createQuery(hql.toString());
        if (permissions.getUserName() != null) {
            query.setParameter("userName", permissions.getUserName());
        }

        List<UserPermissions> results = new ArrayList<>();
        List<Object[]> list = query.getResultList();
        if (list != null) {
            for (Object[] objects : list) {
                UserPermissions userPermissions = new UserPermissions();
                userPermissions.setId(Integer.parseInt(objects[0].toString()));
                userPermissions.setUserName(String.valueOf(objects[1]));
                userPermissions.setWorkerId(String.valueOf(objects[2]));
                userPermissions.setApp(String.valueOf(objects[3]));
                results.add(userPermissions);
            }
        }
        return results;
    }

    @Override
    public void update(UserPermissions permissions) {
        em.merge(permissions);
    }

    @Override
    public int deleteByUserName(String userName) {
        StringBuilder hql = new StringBuilder();
        hql.append("DELETE FROM UserPermissions WHERE userName=:userName");
        Query query = em.createQuery(hql.toString());
        query.setParameter("userName", userName);
        return query.executeUpdate();
    }
}