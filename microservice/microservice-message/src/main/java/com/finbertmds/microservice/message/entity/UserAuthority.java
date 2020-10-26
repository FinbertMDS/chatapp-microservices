package com.finbertmds.microservice.message.entity;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.finbertmds.microservice.message.security.authority.AuthoritiesConstants;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;
import org.springframework.security.core.GrantedAuthority;

@UserDefinedType(value = Schema.USER_AUTHORITY_UDT)
@JsonTypeInfo(use= JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="@class")
public class UserAuthority implements GrantedAuthority {
    /**
     *
     */
    private static final long serialVersionUID = 2473386612575971470L;
    @Column
    private String authority = AuthoritiesConstants.USER;

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
