package com.highend.cms.service.impl;

import com.highend.cms.service.UserAccountService;
import com.highend.cms.service.model.AppUserAccountPrincipal;
import com.highend.cms.service.model.UserAccountDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final UserAccountService userAccountService;

    public AppUserDetailsService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserAccountDTO userAccount = userAccountService.getByUsername(username);
        if (userAccount == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new AppUserAccountPrincipal(userAccount);
    }
}
