package com.nhnacademy.springsecurityhomework.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.nhnacademy.springsecurityhomework.exception.NotFoundRoleException;


public enum Role {
    ADMIN("ADMIN"), MEMBER("MEMBER"), GOOGLE("GOOGLE");

    private String AUTH_ROLE;


    Role(String authrole) {
        this.AUTH_ROLE = authrole;
    }

    public String getAuthRole() {
        return AUTH_ROLE;
    }


    @JsonCreator
    public static Role fromValue(String role) {
        role = role.toLowerCase();
        for (Role roles : Role.values()) {
            if (roles.toString().equals(role)) {
                return roles;
            }
        }
        throw new NotFoundRoleException(role + "은 없는 롤 입니다.");
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }


}
