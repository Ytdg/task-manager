package com.api.manager.auth;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserDetailImpl extends User {

    @Getter
    private final Long id;

    public UserDetailImpl(String username, Long ID, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = ID;
    }
}
