package com.highend.cms.service.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AppUserAccountPrincipal implements UserDetails {

    private UserAccountDTO userAccountDTO;
    private Set<GrantedAuthority> grantedAuthorities;

    public AppUserAccountPrincipal(UserAccountDTO userAccountDTO) {
        this.userAccountDTO = userAccountDTO;
        this.grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(userAccountDTO.getRoleDTO().getName()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return userAccountDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return userAccountDTO.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
