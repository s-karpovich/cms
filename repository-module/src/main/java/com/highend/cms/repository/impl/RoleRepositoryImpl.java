package com.highend.cms.repository.impl;

import com.highend.cms.repository.RoleRepository;
import com.highend.cms.repository.model.Role;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepositoryImpl extends GenericRepositoryImpl<Long, Role> implements RoleRepository {
}
