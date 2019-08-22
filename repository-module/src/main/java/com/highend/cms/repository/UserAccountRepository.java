package com.highend.cms.repository;

import com.highend.cms.repository.model.UserAccount;

import java.util.List;

public interface UserAccountRepository extends GenericRepository<Long, UserAccount>{

    UserAccount getByUsername(String username);
}
