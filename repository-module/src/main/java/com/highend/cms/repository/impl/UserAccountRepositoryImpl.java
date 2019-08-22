package com.highend.cms.repository.impl;

import com.highend.cms.repository.UserAccountRepository;
import com.highend.cms.repository.model.UserAccount;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserAccountRepositoryImpl extends GenericRepositoryImpl<Long, UserAccount> implements UserAccountRepository {

    @Override
    public UserAccount getByUsername(String username) {
        String hql = "from UserAccount as U where U.username=:username";
        Query query = entityManager.createQuery(hql);
        query.setParameter("username", username);
        try {
            return (UserAccount) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserAccount> getPage(int page) {
        String hql = "FROM UserAccount as O ORDER BY changedAt DESC";
        Query q = entityManager.createQuery(hql)
                .setFirstResult(getOffset(page))
                .setMaxResults(BATCH_SIZE);
        return q.getResultList();
    }
}

