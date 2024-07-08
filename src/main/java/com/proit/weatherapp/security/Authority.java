package com.proit.weatherapp.security;

import com.proit.weatherapp.entity.Role;

public final class Authority {

    private Authority() {
    }

    public static final String USER = "ROLE_USER";
    public static final String ADMIN = "ROLE_ADMIN";

    public static String getAuthority(Role role) {
        return switch (role) {
            case Role.USER -> Authority.USER;
            case Role.ADMIN -> Authority.ADMIN;
        };
    }
}
